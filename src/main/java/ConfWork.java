import java.io.*;

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
        try {
            FileInputStream fstream = new FileInputStream(pConfig);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            for (long i = 1; (strLine = br.readLine()) != null; i++) {
                System.out.println(strLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
