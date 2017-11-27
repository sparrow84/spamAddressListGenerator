import java.io.File;
import java.io.IOException;

public class confWork {

    private String nameConfFile = "salg";
    private File pConfig = new File("../" + nameConfFile + ".conf");

    public confWork() {
        if (!pConfig.exists()) {
            try {
                pConfig.createNewFile();

                // Заполнить параметрами по умолчанию

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Считать параметры

    }

    public void readConfig() {

        if (!pConfig.exists()) {
            try {
                pConfig.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
