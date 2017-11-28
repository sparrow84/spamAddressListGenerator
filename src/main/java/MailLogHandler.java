import java.io.File;

class MailLogHandler {

//    private String sourceFileName;
    private File sourceFile;

    MailLogHandler(String mailLogPath) {
        sourceFile = new File(mailLogPath);


    }

    void dummyMethod() {
        System.out.println("Dummy method mail log handler");
    }

}
