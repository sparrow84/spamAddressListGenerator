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

                // Fill parametrs by default
                try (FileWriter fileWriter = new FileWriter(pConfig,true)) {
                    fileWriter.write("mail_log_path = ~/mail\n");
                    fileWriter.write("Parametr02 = qwe\n");
                    fileWriter.write("Parametr03 = jhg3\n");
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

            for (long i = 1; (strLine = br.readLine()) != null; i++) {

                // Ignore comments (string starts with ";")
                if (!strLine.startsWith(";")) {
                    System.out.println(strLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
