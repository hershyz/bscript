import javax.annotation.processing.SupportedSourceVersion;

public class PrintCommand {

    private static Parser parser = new Parser("");

    //Print command:
    public void print(String line) {

        //Checks to see if there is a valid string argument:
        String triedStringArg = parser.getStringArg(line);
        if (triedStringArg.length() > 1) {
            System.out.print(triedStringArg);
        }

        //If not, variables are accessed:
        else {
            String variableName = parser.getGeneralArg(line);

            //Looks for string variables:
            if (parser.variableStorage.stringNames.contains(variableName)) {
                System.out.print(parser.findStringValue(variableName));
            }

            //Looks for double variables:
            if (parser.variableStorage.doubleNames.contains(variableName)) {
                System.out.print(parser.findDoubleValue(variableName));
            }

            //Looks for boolean variables:
            if (parser.variableStorage.boolNames.contains(variableName)) {
                System.out.print(parser.findBoolValue(variableName));
            }
        }
    }

    //Print line command:
    public void println(String line) {

        //Checks to see if there is a valid string argument:
        String triedStringArg = parser.getStringArg(line);
        if (triedStringArg.length() > 1) {
            System.out.println(triedStringArg);
        }

        //If not, variables are accessed:
        else {
            String variableName = parser.getGeneralArg(line);

            //Looks for string variables:
            if (parser.variableStorage.stringNames.contains(variableName)) {
                System.out.println(parser.findStringValue(variableName));
            }

            //Looks for double variables:
            if (parser.variableStorage.doubleNames.contains(variableName)) {
                System.out.println(parser.findDoubleValue(variableName));
            }

            //Looks for boolean variables:
            if (parser.variableStorage.boolNames.contains(variableName)) {
                System.out.println(parser.findBoolValue(variableName));
            }
        }
    }
}