import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String ... args){
        LogWork lw = new LogWork();
        lw.logWrite("*** Start program ***");

        Thread prog = new Thread(new Runnable() {
            public void run() {

                ConfWork cf = new ConfWork();

                cf.readConfig();

            }
        });

        prog.start();



        lw.logWrite("*** End program ***\n\n");
    }



}
