import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String ... args){
        System.out.println("*** Start program ***");

        Thread prog = new Thread(new Runnable() {
            public void run() {
                LogWork lw = new LogWork();
                lw.logWrite("*** Start program ***");
            }
        });

        prog.start();

        readConfig();





        System.out.println("*** End program ***");
    }

    public static void readConfig() {
        File pConfig = new File("../salg.conf");

        System.out.println(pConfig.getAbsolutePath());
        System.out.println(pConfig.exists());

        if (!pConfig.exists()) {
            try {
                pConfig.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
