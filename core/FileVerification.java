import java.io.File;

public class FileVerification {

    Syntax syntax = new Syntax();

    public boolean verifyFile(String path) {

        //Checks if the file exists:
        File tempFile = new File(path);

        //If the file exists, checks if the extension is valid:
        if (tempFile.exists()) {

            String tempExtension = "";
            String[] _split = path.split("");
            int min = path.length() - 2;
            int max = path.length() - 1;

            tempExtension = tempExtension + _split[min] + _split[max];

            if (tempExtension.equals(syntax.extension)) {
                return true;
            }

            return false;
        }

        return false;
    }
}