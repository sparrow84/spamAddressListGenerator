import java.io.File;
import java.io.IOException;

public class ConfWork {

    private String nameConfFile = "salg";
    private File pConfig = new File("../" + nameConfFile + ".conf");

    public ConfWork() {
        readConfig();

    }

    public void readConfig() {

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
}
