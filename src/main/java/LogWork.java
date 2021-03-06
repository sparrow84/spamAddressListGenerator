import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWork {

    private static SimpleDateFormat formatForCurDate = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss\"SSS");
    private static SimpleDateFormat formatForLogName = new SimpleDateFormat("yyyy.MM.dd");
    private static String nameLogFile = "salg";
//    private static File logFile = new File("../" + nameLogFile + "_" + formatForLogName.format(new Date()) + ".log");
    private static File logFile = new File(nameLogFile + "_" + formatForLogName.format(new Date()) + ".log");

//    public LogWork() {
//        this.checkLogExist(logFile);
//    }

    /**
     * It checks if the file exists and if it does not exist, it creates.
     * @param logFile
     * @return false if the file can not be created
     */
    private static boolean checkLogExist(File logFile) {
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                System.out.println("vvaMessage - Can't create log file.");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * Write message in log file
     * @param logMsg
     */
    public static void logWrite (String logMsg) {

        if (checkLogExist(logFile)) {
            try (FileWriter fileWriter = new FileWriter(logFile,true)) {
                fileWriter.write(formatForCurDate.format(new Date()) + "    ");
                fileWriter.write(logMsg + "\n");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(logMsg);
        } else {
            System.out.println("Что то пошло не так с лог файлом LogWork -> logWrite");
        }
    }

    public static void myPrintStackTrace (Exception e) {
        LogWork.logWrite("Atention  --  " + e.toString());
        for (StackTraceElement s: e.getStackTrace()) {
            LogWork.logWrite("      " + s);
        }
        e.printStackTrace();
    }
}
