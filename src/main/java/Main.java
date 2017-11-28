
public class Main {
    public static void main(String ... args){

        LogWork.logWrite("*** Start program ***");

        Thread prog = new Thread(new Runnable() {
            public void run() {

                ConfWork confWork = new ConfWork();

                MailLogHandler mlh = new MailLogHandler("");
                LogWork.logWrite("");
                LogWork.logWrite("- mailLogPath = " + confWork.getMailLogPath());
                LogWork.logWrite("- allowableFrequency = " + confWork.getAllowableFrequency());
                LogWork.logWrite("- amnestyPeriod = " + confWork.getAmnestyPeriod());
                LogWork.logWrite("- timeWaitRepeat = " + confWork.getTimeWaitRepeat());
                LogWork.logWrite("- lineNumberToStart = " + confWork.getLineNumberToStart());
                LogWork.logWrite("- trash = " + confWork.getTrash());


            }
        });

        prog.start();



        LogWork.logWrite("*** End program ***\n\n");
    }



}
