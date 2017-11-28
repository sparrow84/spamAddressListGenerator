
public class Main {
    public static void main(String ... args){

        LogWork.logWrite("\n\n   *** Start program ***");

        Thread prog = new Thread(new Runnable() {
            public void run() {

                ConfWork.readConfig();

                LogWork.logWrite("");
                LogWork.logWrite("- mailLogPath = " + ConfWork.getMailLogPath());
                LogWork.logWrite("- allowableFrequency = " + ConfWork.getAllowableFrequency());
                LogWork.logWrite("- amnestyPeriod = " + ConfWork.getAmnestyPeriod());
                LogWork.logWrite("- timeWaitRepeat = " + ConfWork.getTimeWaitRepeat());
                LogWork.logWrite("- lineNumberToStart = " + ConfWork.getLineNumberToStart());
                LogWork.logWrite("- trash = " + ConfWork.getTrash());

                MailLogHandler mailLogHandler = new MailLogHandler(ConfWork.getMailLogPath());
                mailLogHandler.scanFile();

            }
        });

        prog.start();



//        LogWork.logWrite("*** End program ***\n\n");
    }



}
