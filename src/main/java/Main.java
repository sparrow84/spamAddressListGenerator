public class Main {
    public static void main(String ... args){

        LogWork.logWrite("\n\n   *** Start program ***");

        Thread prog = new Thread(new Runnable() {
            public void run() {

                ConfWork.readConfig();

                LogWork.logWrite(ConfWork.printParam());


                try {
                    DBWork.connect(ConfWork.getBasePath());
                } catch (Exception e) {
                    LogWork.logWrite("Atention  --  " + e.toString());
                    e.printStackTrace();
                }

                MailLogHandler mailLogHandler = new MailLogHandler(ConfWork.getMailLogPath());

                long lactString = mailLogHandler.scanFile();
                ConfWork.chageConfig("lineNumberToStart", String.valueOf(lactString));


//                DBWork.deleteAllDataTable();

                DBWork.showDataTable();

                DBWork.disconnect();
            }
        });

        prog.start();



//        LogWork.logWrite("*** End program ***\n\n");
    }



}
