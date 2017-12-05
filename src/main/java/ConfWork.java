import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfWork {

    private static String nameConfFile = "salg.conf";
    private static File pConfig = new File("../" + nameConfFile);

    private static String mailLogPath;
    private static String basePath;
    private static String resultFilePath;
    private static int allowableFrequency;
    private static int allowableAddressRepeatTime;
    private static int amnestyPeriod;
    private static int timeWaitRepeat;
    private static long lineNumberToStart;
    private static List<String> keyStringsForSearch = new ArrayList<>();
    private static String trash;

//    ConfWork() {
//        readConfig();
//    }

    public static void readConfig() {

        if (!pConfig.exists()) {
            try {
                pConfig.createNewFile();

                // Fill config parametrs by default
                try (FileWriter fileWriter = new FileWriter(pConfig,true)) {
                    fileWriter.write("; Path to mail server log file");
                    fileWriter.write("\n; path must be specified without quotes (spaces in the path are allowed)");
                    fileWriter.write("\nmailLogPath = ~/mail");
                    fileWriter.write("\n");
                    //
                    fileWriter.write("\n; Path to data base for storage found adresses");
                    fileWriter.write("\n; path must be specified without quotes (spaces in the path are allowed)");
                    fileWriter.write("\nbasePath = ../salgBase.db");
                    fileWriter.write("\n");
                    //
                    fileWriter.write("\n; Path for result file with forbidden addresses");
                    fileWriter.write("\n; path must be specified without quotes (spaces in the path are allowed)");
                    fileWriter.write("\nresultFilePath = ../forbidden_addr.txt");
                    //
                    fileWriter.write("\n; The allowed number of addresses in the file");
                    fileWriter.write("\nallowableFrequency = 10");
                    fileWriter.write("\n");
                    //
                    fileWriter.write("\n; Allowable address repeat time in the file in minutes");
                    fileWriter.write("\nallowableAddressRepeatTime = 10");
                    fileWriter.write("\n");
                    //
                    fileWriter.write("\n; Amnesty period in days");
                    fileWriter.write("\n; The period after which the address is excluded from the black");
                    fileWriter.write("\n; list is specified if it was not mentioned in this period.");
                    fileWriter.write("\namnestyPeriod = 30");
                    fileWriter.write("\n");
                    fileWriter.write("\n; Time to wait for repeat in minutes");
                    fileWriter.write("\ntimeWaitRepeat = 5");
                    fileWriter.write("\n");
                    fileWriter.write("\n; The line number from which you want to start reading the file");
                    fileWriter.write("\nlineNumberToStart = 1");
                    fileWriter.write("\n");
                    fileWriter.write("\n; List of strings to look for in the file to identify bad addresses");
                    fileWriter.write("\n; Print one line separated by commas");
                    fileWriter.write("\nkeyStringsForSearch = " +
                            "SASL LOGIN authentication failed," +
                            "does not resolve to address," +
                            "Host not found," +
                            "need fully-qualified hostname");
                    fileWriter.write("\n");

                    mailLogPath = "";
                    basePath = "";
                    resultFilePath = "";
                    allowableFrequency = 0;
                    allowableAddressRepeatTime = 0;
                    amnestyPeriod = 0;
                    timeWaitRepeat = 0;
                    trash = "";

                }
                catch (IOException e) {
                    LogWork.logWrite("Atention  --  " + e.toString());
                    for (StackTraceElement s: e.getStackTrace()) {
                        LogWork.logWrite("      " + s);
                    }
                    e.printStackTrace();
                }

            } catch (IOException e) {
                LogWork.logWrite("Atention  --  " + e.toString());
                for (StackTraceElement s: e.getStackTrace()) {
                    LogWork.logWrite("      " + s);
                }
                e.printStackTrace();
            }
        }

        // Read parametrs
        try {
            FileInputStream fstream = new FileInputStream(pConfig);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            String[] arrayStrLine;
            for (long i = 1; (strLine = br.readLine()) != null; i++) {
                // Ignore comments (string starts with ";")
                if (!strLine.startsWith(";")) {
                    arrayStrLine = strLine.split("=");
                    switch (arrayStrLine[0].trim()){
                        case "mailLogPath":
                            mailLogPath = arrayStrLine[1].trim();
                            break;
                        case "basePath":
                            basePath = arrayStrLine[1].trim();
                            break;
                        case "resultFilePath":
                            resultFilePath = arrayStrLine[1].trim();
                            break;
                        case "allowableFrequency":
                            allowableFrequency = Integer.parseInt(arrayStrLine[1].trim());
                            break;
                        case "allowableAddressRepeatTime":
                            allowableAddressRepeatTime = Integer.parseInt(arrayStrLine[1].trim());
                            break;
                        case "amnestyPeriod":
                            amnestyPeriod = Integer.parseInt(arrayStrLine[1].trim());
                            break;
                        case "timeWaitRepeat":
                            timeWaitRepeat = Integer.parseInt(arrayStrLine[1].trim());
                            break;
                        case "lineNumberToStart":
                            lineNumberToStart = Long.parseLong(arrayStrLine[1].trim());
                            break;
                        case "keyStringsForSearch":
                            for (String s: arrayStrLine[1].trim().split(",")) {
                                keyStringsForSearch.add(s.trim().toLowerCase());
                            }
                            break;
                        default:
                            for (String s: arrayStrLine) {
                                trash = trash + " | " + s;
                            }
                    }
                }
            }
        } catch (IOException e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            for (StackTraceElement s: e.getStackTrace()) {
                LogWork.logWrite("      " + s);
            }
            e.printStackTrace();
        }
    }

    public static String printParam() {
        String params = "\n- mailLogPath = " + mailLogPath +
        "\n- basePath = " + basePath +
        "\n- resultFilePath = " + resultFilePath +
        "\n- allowableFrequency = " + allowableFrequency +
        "\n- allowableAddressRepeatTime = " + allowableAddressRepeatTime +
        "\n- amnestyPeriod = " + amnestyPeriod +
        "\n- timeWaitRepeat = " + timeWaitRepeat +
        "\n- lineNumberToStart = " + lineNumberToStart +
        "\n- trash = " + trash +
        "\n- getKeyStringsForSearch:";

        if (ConfWork.getKeyStringsForSearch() != null) {
            for (String s: ConfWork.getKeyStringsForSearch()) {
                params = params + "\n      " + s;
            }
        } else {
            params = params + "\n     EMPTY";
        }
        return params;
    }

    public static void chageConfig (String parametr, String value) {
        File tmpConf = new File("../tmpConf.tmp");
        try {
            tmpConf.createNewFile();
        } catch (IOException e) {
            LogWork.logWrite("Atention  --  " + e.toString());
            for (StackTraceElement s: e.getStackTrace()) {
                LogWork.logWrite("      " + s);
            }
            e.printStackTrace();
        }

        try (
                FileInputStream fileInputStream = new FileInputStream(pConfig);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                FileWriter fileWriter = new FileWriter(tmpConf,true)
        ) {

            String workLine;
            String tmpLine;

            for (long i = 1; (workLine = bufferedReader.readLine()) != null; i++) {
                if (workLine.toLowerCase().contains(parametr.toLowerCase())) {
                    fileWriter.write(workLine.split("=")[0].trim() + " = " + value + "\n");
                } else {
                    fileWriter.write(workLine + "\n");
                }
            }
        } catch (IOException ioe) {
            LogWork.myPrintStackTrace(ioe);
            LogWork.logWrite("Atention  --  " + ioe.toString());
            for (StackTraceElement s: ioe.getStackTrace()) {
                LogWork.logWrite("      " + s);
            }
            ioe.printStackTrace();
        }

        pConfig.delete();
        tmpConf.renameTo(pConfig);
    }

    public static String getNameConfFile() {
        return nameConfFile;
    }

    public static void setNameConfFile(String nameConfFile_) {
        nameConfFile = nameConfFile_;
    }

    public static File getpConfig() {
        return pConfig;
    }

    public static void setpConfig(File pConfig_) {
        pConfig = pConfig_;
    }

    public static String getMailLogPath() {
        return mailLogPath;
    }

    public static void setMailLogPath(String mailLogPath_) {
        mailLogPath = mailLogPath_;
    }

    public static String getBasePath() {
        return basePath;
    }

    public static void setBasePath(String basePath_) {
        basePath = basePath_;
    }

    public static String getResultFilePath() {
        return resultFilePath;
    }

    public static void setResultFilePath(String resultFilePath_) {
        resultFilePath = resultFilePath_;
    }

    public static int getAllowableFrequency() {
        return allowableFrequency;
    }

    public static void setAllowableFrequency(int allowableFrequency_) {
        allowableFrequency = allowableFrequency_;
    }

    public static long getAllowableAddressRepeatTime() {
        long result = (long) allowableAddressRepeatTime * (long) 60 * (long) 1000;
        return result;
    }

    public static void setAllowableAddressRepeatTime(int allowableAddressRepeatTime_) {
        allowableAddressRepeatTime = allowableAddressRepeatTime_;
    }

    public static int getAmnestyPeriod() {
        return amnestyPeriod;
    }

    public static void setAmnestyPeriod(int amnestyPeriod_) {
        amnestyPeriod = amnestyPeriod_;
    }

    public static int getTimeWaitRepeat() {
        return timeWaitRepeat;
    }

    public static void setTimeWaitRepeat(int timeWaitRepeat_) {
        timeWaitRepeat = timeWaitRepeat_;
    }

    public static long getLineNumberToStart() {
        return lineNumberToStart;
    }

    public static void setLineNumberToStart(long lineNumberToStart_) {
        lineNumberToStart = lineNumberToStart_;
    }

    public static String getTrash() {
        return trash;
    }

    public static void setTrash(String trash_) {
        trash = trash_;
    }

    public static List<String> getKeyStringsForSearch() {
        return keyStringsForSearch;
    }
}
