import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Calendar;

class MailLogHandler {
    private File sourceFile;

    MailLogHandler(String mailLogPath) {
        sourceFile = new File(mailLogPath);
    }

    public void scanFile() {
        try (
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))
        ) {

            String workLine;
            String tmpLine;

            for (long i = 1; (workLine = bufferedReader.readLine()) != null; i++) {
                if (i >= ConfWork.getLineNumberToStart()) {
                    if (searchKeyString(ConfWork.getKeyStringsForSearch(),workLine)) {
                        System.out.println(i + "    -- -- --    " + workLine);


                        System.out.println();
                    }
                }
            }
        } catch (IOException ioe) {
            LogWork.logWrite(ioe.toString());
            ioe.printStackTrace();
        }
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
            case "oct": month = 9; break;
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
}
