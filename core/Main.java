import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        FileVerification fv = new FileVerification();

        //Gets user file input:
        Scanner scn = new Scanner(System.in);
        System.out.print("> ");
        String filePath = scn.nextLine();

        //Verifies the file is valid:
        boolean fileVerified = fv.verifyFile(filePath);
        if (!fileVerified) {
            System.out.println("Invalid file: " + filePath);
            return;
        }

        //If the input file is valid, interpreter starts:
        Parser parser = new Parser(filePath);
        parser.run();
    }
}