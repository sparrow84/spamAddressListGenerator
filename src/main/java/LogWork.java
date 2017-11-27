import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
* написать методы работы с логфайлом
* написать конструктор для (синглтон)
*
* При запуске програмы записать в лог сообщение о старте. Если файл лога создать не удалось
* сообщить в консоль:
* - не удалось создать файл
* - файл пытаюсь создать тут %PATH%
* - работаю по пользователем %USER%
*
* */

public class LogWork {

    private SimpleDateFormat formatForCurDate = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss\"SSS");
    private SimpleDateFormat formatForLogName = new SimpleDateFormat("yyyy.MM.dd");
    private String nameLogFile = "salg";
    private File logFile = new File("../" + nameLogFile + "_" + formatForLogName.format(new Date()) + ".log");

    public LogWork() {
        this.checkLogExist(logFile);
    }

    /**
     * It checks if the file exists and if it does not exist, it creates.
     * @param logFile
     * @return false if the file can not be created
     */
    private boolean checkLogExist(File logFile) {
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
    public void logWrite (String logMsg) {

        if (this.checkLogExist(logFile)) {
            try (FileWriter fileWriter = new FileWriter(logFile,true)) {
                fileWriter.write(formatForCurDate.format(new Date()) + "    ");
                fileWriter.write(logMsg);
                fileWriter.write("\n");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(logMsg);
        }
    }
}
