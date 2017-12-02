public class Main {
    public static void main(String ... args){

        LogWork.logWrite("\n\n   *** Start program ***");

        Thread prog = new Thread(new Runnable() {
            public void run() {

                ConfWork.readConfig();

                LogWork.logWrite("");
                LogWork.logWrite("- mailLogPath = " + ConfWork.getMailLogPath());
                LogWork.logWrite("- basePath = " + ConfWork.getBasePath());
                LogWork.logWrite("- allowableFrequency = " + ConfWork.getAllowableFrequency());
                LogWork.logWrite("- allowableAddressRepeatTime = " + ConfWork.getAllowableAddressRepeatTime());
                LogWork.logWrite("- amnestyPeriod = " + ConfWork.getAmnestyPeriod());
                LogWork.logWrite("- timeWaitRepeat = " + ConfWork.getTimeWaitRepeat());
                LogWork.logWrite("- lineNumberToStart = " + ConfWork.getLineNumberToStart());
                LogWork.logWrite("- trash = " + ConfWork.getTrash());
                LogWork.logWrite("- keyStringsForSearch: ");
                if (ConfWork.getKeyStringsForSearch() != null) {
                    for (String s: ConfWork.getKeyStringsForSearch()) {
                        LogWork.logWrite("      " + s);
                    }
                } else {
                    LogWork.logWrite("     EMPTY");
                    LogWork.logWrite("The list of key phrases for search is empty. " +
                            "\nThe execution of the program is aborted.");
                    return;
                }

                try {
                    DBWork.connect();
                } catch (Exception e) {
                    LogWork.logWrite("Atention  --  " + e.toString());
                    e.printStackTrace();
                }

                MailLogHandler mailLogHandler = new MailLogHandler(ConfWork.getMailLogPath());
                mailLogHandler.scanFile();


                ConfWork.chageConfig("allowableFrequency","10");


                //DBWork.deleteAllDataTable();

                DBWork.showDataTable();

                DBWork.disconnect();

            }
        });

        prog.start();



//        LogWork.logWrite("*** End program ***\n\n");
    }



}
