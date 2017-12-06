import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Calendar;

class MailLogHandler {
    private File sourceFile;

    MailLogHandler(String mailLogPath) {
        sourceFile = new File(mailLogPath);
    }

    public long scanFile() {

        long lastI = 1;

        try (
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))
        ) {

            String workLine;
            String tmpAddr;
            long tmpTime;
            long[] dataFromBase;

            // Беру очердную строку из лога сервера
            for (long i = 1; (workLine = bufferedReader.readLine()) != null; i++) {
                lastI = i;
                // Ищу строку на которой остановлся в прощлый раз
                if (i >= ConfWork.getLineNumberToStart()) {
                    // Ищу в строке ключевые фразы
                    if (searchKeyString(ConfWork.getKeyStringsForSearch(),workLine)) {
                        // Забираю из строки адрес и время
                        tmpAddr = getAddressFromString(workLine);
                        tmpTime = getAddressTime(workLine);

                        // Ищу в базе запись с такимже адресом как в текущей строке из лога сервера
                        dataFromBase = DBWork.searchAddr(tmpAddr);


                        if (dataFromBase[0] < 1) {
                            DBWork.insert(tmpAddr,tmpTime,1);
//                            System.out.println("VVA debug  ---  Insert new addr " + tmpAddr);
                        } else {
//                            System.out.println("VVA debug  ---  before Update exist addr " + tmpAddr);
//                            System.out.println("VVA debug  ---  " + "\n    tmpTime " + tmpTime+ "\n    dataFromBase[1] " + dataFromBase[1] + "\n    ConfWork.getAllowableAddressRepeatTime() " + ConfWork.getAllowableAddressRepeatTime());
                            if ((tmpTime - dataFromBase[1]) < ConfWork.getAllowableAddressRepeatTime()) {
                                DBWork.update(tmpAddr,tmpTime);
//                                System.out.println("VVA debug  ---  Update exist addr " + tmpAddr);
                            }
                        }

                    }
                }
            }
        } catch (IOException ioe) {
            LogWork.logWrite("Atention  --  " + ioe.toString());
            for (StackTraceElement s: ioe.getStackTrace()) {
                LogWork.logWrite("      " + s);
            }
            ioe.printStackTrace();
        }

        return lastI;
    }

    private static boolean searchKeyString (List<String> keyStrings, String targetString) {
        for (String s: keyStrings)
            if (targetString.toLowerCase().contains(s))
                return true;
        return false;
    }






    private static String getAddressFromString(String str, String addresType) {
        Pattern pattern;

        if (addresType.equals("ipv6")) {
            pattern = Pattern.compile("([0-9A-Fa-f]{4}):([0-9A-Fa-f]{4})");
        } else if (addresType.equals("ipv4")) {
            pattern = Pattern.compile("([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})");
        } else if (addresType.equals("text") ) {
//            pattern = Pattern.compile("((name=)[^\\s]+)");
            pattern = Pattern.compile("((?<=name=)[^\\s]+)");
        } else {
            // IPv4 default
            pattern = Pattern.compile("([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})");
        }

        Matcher matcher =  pattern.matcher(str);

        if (matcher.find()) {
            return str.substring(matcher.start(), matcher.end());
        }
        return "0";
    }

    private static String getAddressFromString(String str) {
        String[] addressTypes = {"text","ipv4","ipv6"};
        String returnValue;
        for (String s: addressTypes)
            if (!(returnValue = getAddressFromString(str,s)).equals("0"))
                return returnValue;
        return "0";
    }

    private static Long getAddressTime(String str) {

        int month = 0;
        int day;
        int hour;
        int min;
        int sec;

        Calendar myCalendar = (Calendar) Calendar.getInstance().clone();
        Calendar cfgcy = Calendar.getInstance(); // calendar For Get Currrent Year

        switch (str.substring(0,3).toLowerCase()) {
            case "jan": month = 0;  break;
            case "feb": month = 1;  break;
            case "mar": month = 2;  break;
            case "apr": month = 3;  break;
            case "may": month = 4;  break;
            case "jun": month = 5;  break;
            case "jul": month = 6;  break;
            case "aug": month = 7;  break;
            case "sep": month = 8;  break;
            case "oct": month = 9;  break;
            case "nov": month = 10; break;
            case "dec": month = 11; break;
            default:
                System.out.println("In string -> (" + str.substring(0,3).toLowerCase() + ") month not found!");
                return null;
        }

        if (str.substring(4,5).equals(" ")) {
            day = Integer.parseInt(str.substring(5,6));
        } else {
            day = Integer.parseInt(str.substring(4,6));
        }

        hour = Integer.parseInt(str.substring(7,9));
        min = Integer.parseInt(str.substring(10,12));
        sec = Integer.parseInt(str.substring(13,15));

        myCalendar.set(cfgcy.get(Calendar.YEAR),month,day,hour,min,sec);

//        long countMilis = myCalendar.getTimeInMillis();


        return myCalendar.getTimeInMillis();

        /* Пересчёт милисекунд обратно в читаемую дату
            String tmpLine;
            if (!(tmpLine = getAddressFromString(workLine)).equals("0")) {
                System.out.print(tmpLine + "   ");
                long someLongInt = getAddressTime(workLine);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(someLongInt);
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss\"SSS");
                System.out.println(format.format(cal.getTime()));
                //System.out.println(getAddressTime(workLine));
            }
        */
    }

    public void makeResultFile (String path, List<String> list) {
        File resultF = new File(path);

        if (resultF.exists()) resultF.delete();
        try {
            resultF.createNewFile();
        } catch (IOException e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            for (StackTraceElement s: e.getStackTrace()) {
                LogWork.logWrite("      " + s);
            }
            e.printStackTrace();
        }

        try (FileWriter fileWriter = new FileWriter(resultF,true)) {

            for (String s: list) {
                fileWriter.write(s + "\n");
            }

        }
        catch (IOException e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            for (StackTraceElement s: e.getStackTrace()) {
                LogWork.logWrite("      " + s);
            }
            e.printStackTrace();
        }


    }

}


