import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class MailLogHandler {
    private File sourceFile;

    MailLogHandler(String mailLogPath) {
        sourceFile = new File(mailLogPath);
    }

    void dummyMethod() {
        System.out.println("Dummy method mail log handler");
    }

    public void scanFile() {
        //
        try (
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {

            String workLine;
            String tmpLine;

            for (long i = 1; (workLine = bufferedReader.readLine()) != null; i++) {

                if (i >= ConfWork.getLineNumberToStart()) {
                    System.out.println(i + "   ---   " + workLine);
                }

            }

        } catch (IOException ioe) {

        }
    }

}
