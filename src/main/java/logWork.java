import java.io.File;
import java.io.IOException;

/*
* написать методы работы с логфайлом
* написать конструктор для (синглтон)
* qwe
* */

public class logWork {

    private File logFile = new File("../salg.log");



    private boolean checkLog(File logFile) {
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

    public void logWrite (String logMsg) {
        if (this.checkLog(logFile)) {

        }
    }
}
