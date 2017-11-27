import java.io.*;

public class ConfWork {

    private String nameConfFile = "salg";
    private File pConfig = new File("../" + nameConfFile + ".conf");

    private String mailLogPath;
    private int allowableFrequency;
    private int amnestyPeriod;
    private int timeWaitRepeat;
    private long lineNumberToStart;
    private String trash;

    ConfWork() {
        readConfig();
    }

    public void readConfig() {

        if (!pConfig.exists()) {
            try {
                pConfig.createNewFile();

                // Fill config parametrs by default
                try (FileWriter fileWriter = new FileWriter(pConfig,true)) {
                    fileWriter.write("\n; Path to mail server log file");
                    fileWriter.write("\n; path must be specified without quotes");
                    fileWriter.write("\nmailLogPath = ~/mail");
                    fileWriter.write("\n");
                    fileWriter.write("\n; Allowable frequency in minutes");
                    fileWriter.write("\nallowableFrequency = 10");
                    fileWriter.write("\n");
                    fileWriter.write("\n; Amnesty period in days");
                    fileWriter.write("\namnestyPeriod = 30");
                    fileWriter.write("\n");
                    fileWriter.write("\n; Time to wait for repeat in minutes");
                    fileWriter.write("\ntimeWaitRepeat = 5");
                    fileWriter.write("\n");
                    fileWriter.write("\n; The line number from which you want to start reading the file");
                    fileWriter.write("\n; The adjusted value is valid for the next cycle");
                    fileWriter.write("\n; After one cycle the value becomes -1");
                    fileWriter.write("\nlineNumberToStart = -1");
                    fileWriter.write("\n");

                    this.mailLogPath = "";
                    this.allowableFrequency = 0;
                    this.amnestyPeriod = 0;
                    this.timeWaitRepeat = 0;
                    this.trash = "";

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
                            this.mailLogPath = arrayStrLine[1].trim();
                            break;
                        case "allowableFrequency":
                            this.allowableFrequency = Integer.parseInt(arrayStrLine[1].trim());
                            break;
                        case "amnestyPeriod":
                            this.amnestyPeriod = Integer.parseInt(arrayStrLine[1].trim());
                            break;
                        case "timeWaitRepeat":
                            this.timeWaitRepeat = Integer.parseInt(arrayStrLine[1].trim());
                            break;
                        case "lineNumberToStart":
                            this.lineNumberToStart = Long.parseLong(arrayStrLine[1].trim());
                            break;
                        default:
                            for (String s: arrayStrLine) {
                                this.trash = this.trash + " | " + s;
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

    public String getNameConfFile() {
        return nameConfFile;
    }

    public void setNameConfFile(String nameConfFile) {
        this.nameConfFile = nameConfFile;
    }

    public File getpConfig() {
        return pConfig;
    }

    public void setpConfig(File pConfig) {
        this.pConfig = pConfig;
    }

    public String getMailLogPath() {
        return mailLogPath;
    }

    public void setMailLogPath(String mailLogPath) {
        this.mailLogPath = mailLogPath;
    }

    public int getAllowableFrequency() {
        return allowableFrequency;
    }

    public void setAllowableFrequency(int allowableFrequency) {
        this.allowableFrequency = allowableFrequency;
    }

    public int getAmnestyPeriod() {
        return amnestyPeriod;
    }

    public void setAmnestyPeriod(int amnestyPeriod) {
        this.amnestyPeriod = amnestyPeriod;
    }

    public int getTimeWaitRepeat() {
        return timeWaitRepeat;
    }

    public void setTimeWaitRepeat(int timeWaitRepeat) {
        this.timeWaitRepeat = timeWaitRepeat;
    }

    public long getLineNumberToStart() {

        long tmp = lineNumberToStart;
        lineNumberToStart = -1;
        return tmp;
    }

    public void setLineNumberToStart(long lineNumberToStart) {
        this.lineNumberToStart = lineNumberToStart;
    }

    public String getTrash() {
        return trash;
    }

    public void setTrash(String trash) {
        this.trash = trash;
    }
}
