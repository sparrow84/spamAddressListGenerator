import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfWork {

    private static String nameConfFile = "salg.conf";
    private static File pConfig = new File("../" + nameConfFile);

    private static String mailLogPath;
    private static int allowableAddressRepeatTime;
    private static int allowableFrequency;
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
                    fileWriter.write("\n; path must be specified without quotes");
                    fileWriter.write("\nmailLogPath = ~/mail");
                    fileWriter.write("\n");
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
                    allowableFrequency = 0;
                    amnestyPeriod = 0;
                    timeWaitRepeat = 0;
                    trash = "";

                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
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
                    System.out.println(strLine);

                    arrayStrLine = strLine.split("=");

                    switch (arrayStrLine[0].trim()){
                        case "mailLogPath":
                            mailLogPath = arrayStrLine[1].trim();
                            break;
                        case "allowableFrequency":
                            allowableFrequency = Integer.parseInt(arrayStrLine[1].trim());
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
            e.printStackTrace();
        }
    }

    public void printParam() {
        System.out.println();
        System.out.println("- mailLogPath = " + mailLogPath);
        System.out.println("- allowableFrequency = " + allowableFrequency);
        System.out.println("- amnestyPeriod = " + amnestyPeriod);
        System.out.println("- timeWaitRepeat = " + timeWaitRepeat);
        System.out.println("- lineNumberToStart = " + lineNumberToStart);
        System.out.println("- trash = " + trash);
    }

    public static void chageConfig (String parametr, String value) {
        File tmpConf = new File("../tmpConf.tmp");
        try {
            tmpConf.createNewFile();
        } catch (IOException e) {
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
            LogWork.logWrite(ioe.toString());
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

    public static int getAllowableFrequency() {
        return allowableFrequency;
    }

    public static void setAllowableFrequency(int allowableFrequency_) {
        allowableFrequency = allowableFrequency_;
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
