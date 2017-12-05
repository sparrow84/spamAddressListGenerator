public class Main {
    public static void main(String ... args){

        LogWork.logWrite("\n\n   *** Start program ***********************************************************");

        Thread prog = new Thread(new Runnable() {
            public void run() {

                ConfWork.readConfig();

                LogWork.logWrite(ConfWork.printParam());


                try {
                    DBWork.connect(ConfWork.getBasePath());
                } catch (Exception e) {
                    LogWork.logWrite("Atention  --  " + e.toString());
                    for (StackTraceElement s: e.getStackTrace()) {
                        LogWork.logWrite("      " + s);
                    }
                    e.printStackTrace();
                }

                MailLogHandler mailLogHandler = new MailLogHandler(ConfWork.getMailLogPath());

                long lactString = mailLogHandler.scanFile();
                ConfWork.chageConfig("lineNumberToStart", String.valueOf(lactString));


//                DBWork.deleteAllDataTable();

                DBWork.showDataTable();

                ConfWork.chageConfig("lineNumberToStart", "1");

                System.out.println("NEW ---------------------- NEW");
                for (String s : DBWork.getListWithForbiddenAddresses()) {
                    System.out.println("   -   " + s);
                }
                System.out.println("NEW ---------------------- NEW");

                mailLogHandler.makeResultFile(ConfWork.getResultFilePath(),DBWork.getListWithForbiddenAddresses());

                DBWork.disconnect();
            }
        });

        prog.start();



//        LogWork.logWrite("*** End program ***\n\n");
    }



}
