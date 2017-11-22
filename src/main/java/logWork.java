import java.io.File;
import java.io.IOException;

public class logWork {

    File logFile = new File("../salg.log");

    public static void checkLog(File logFile) {
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                System.out.println("vvaMessage - Can't create log file.");
                e.printStackTrace();
            }
        }
    }
}
