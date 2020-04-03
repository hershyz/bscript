import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Parser {

    private static Scanner scn = new Scanner(System.in); //For keyboard interrupts.
    public static boolean readLines = true;

    //Class imports:
    private static Syntax syntax = new Syntax();
    private static PrintCommand printCommand = new PrintCommand();
    private static CombinationVariableAssignment combinationVariableAssignment = new CombinationVariableAssignment();
    private static IfConditions ifConditions = new IfConditions();
    private static IncrementOperators incrementOperators = new IncrementOperators();
    private static WhileLoops whileLoops = new WhileLoops();
    public static Voids voids = new Voids();
    public static VariableStorage variableStorage = new VariableStorage();

    //Required class runtime variables:
    private String filePath;
    public static List<String> rawLines;

    //Constructor:
    public Parser(String _filePath) {
        this.filePath = _filePath;
    }

    //Parses each individual line:
    public static void parse(String line, int listIndex) {

        line = line.trim(); //Takes out initial spaces in line to avoid errors in interpretation.

        if (line.equals(";")) {
            readLines = true;
            return;
        }

        if (!readLines) {
            return;
        }

        //Ignores blank lines:
        if (line.length() < 1) {
            return;
        }

        //Ignores comments:
        if (is(line, syntax.comment)) {
            return;
        }

        //Command statement recognition:
        //Print line command:
        if (is(line, syntax.printLineCommand)) {
            printCommand.println(line);
            return;
        }

        //Print command:
        if (is(line, syntax.printCommand)) {
            printCommand.print(line);
            return;
        }

        //Keyboard interrupt command:
        if (is(line, syntax.waitForKeyPress)) {
            scn.nextLine();
            return;
        }

        //Structure syntax recognition:
        //Voids:
        if (is(line, syntax.voidSyntax)) {
            String[] raw = line.split(" ");
            String name = raw[1];
            voids.registerVoidName(name, listIndex);
            readLines = false;

            return;
        }

        //Executing voids:
        if (endsWithWellFormedParenthesis(line)) {
            voids.executeVoid(line);
            return;
        }

        //While loops:
        if (is(line, syntax.whileLoop)) {
            String rawParameter = getGeneralArg(line);
            whileLoops.loop(rawParameter, listIndex);
            return;
        }

        //If conditions:
        if (is(line, syntax.ifCondition)) {
            String rawParameter = getGeneralArg(line);
            readLines = ifConditions.solveCondition(rawParameter);
            return;
        }

        //Increment operators:
        if (line.contains("++") || line.contains("--")) {
            String[] raw = line.split(" ");
            incrementOperators.increment(raw[0], raw[1]);
            return;
        }

        //Variable declaration:
        String assignmentValue = combinationVariableAssignment.getRawAssignmentValue(line);
        boolean isVariableCombination = combinationVariableAssignment.isCombinationAssignmentValue(line);

        //If the assignment isn't a combination of variables,
        if (!isVariableCombination) {

            //Strings:
            if (is(line, syntax.stringVar)) {

                //Handles user input commands:
                if (line.contains(syntax.input)) {
                    String stringName = getVariableName(line);
                    String stringValue = scn.nextLine();
                    variableStorage.stringNames.add(stringName);
                    variableStorage.stringValues.add(stringValue);
                    return;
                }

                String stringName = getVariableName(line);
                String stringValue = getStringVariableValue(line);
                variableStorage.stringNames.add(stringName);
                variableStorage.stringValues.add(stringValue);
                return;
            }

            //Doubles:
            if (is(line, syntax.doubleVar)) {

                //Handles user input commands:
                if (line.contains(syntax.input)) {
                    String doubleName = getVariableName(line);
                    double doubleValue = Double.valueOf(scn.nextLine());
                    variableStorage.doubleNames.add(doubleName);
                    variableStorage.doubleValues.add(doubleValue);
                    return;
                }

                String doubleName = getVariableName(line);
                String doubleValueRaw = getGeneralVariableValue(line);
                doubleValueRaw = doubleValueRaw.replace(" ", "");
                double doubleValue = Double.valueOf(doubleValueRaw);
                variableStorage.doubleNames.add(doubleName);
                variableStorage.doubleValues.add(doubleValue);
                return;
            }

            //Booleans:
            if (is(line, syntax.boolVar)) {

                //Handles user input commands:
                if (line.contains(syntax.input)) {
                    String boolName = getVariableName(line);
                    boolean boolValue = Boolean.valueOf(scn.nextLine());
                    variableStorage.boolNames.add(boolName);
                    variableStorage.boolValues.add(boolValue);
                    return;
                }

                String boolName = getVariableName(line);
                String boolValueRaw = getGeneralVariableValue(line);
                boolValueRaw = boolValueRaw.replace(" ", "");
                boolean boolValue = Boolean.valueOf(boolValueRaw);
                variableStorage.boolNames.add(boolName);
                variableStorage.boolValues.add(boolValue);
                return;
            }
        }

        //If the assignment is a combination of variables:
        if (isVariableCombination) {

            double result = combinationVariableAssignment.handleMath(assignmentValue);
            String variableName = getVariableName(line);
            String[] arr = line.split(" ");

            //Assigns to new double variable:
            if (arr[0].equals("num")) {
                variableStorage.doubleNames.add(variableName);
                variableStorage.doubleValues.add(result);
                return;
            }
        }

        //Reassigning variable values:
        //THIS BLOCK OF CODE MUST BE AT THE END OF THE PARSE FUNCTION:
        String[] terms = line.split(" ");
        String variableName = terms[0];
        String value = getGeneralVariableValue(line);

        //Ignores if the variable name is not a well formed variable:
        if (!isVariable(variableName)) {
            return;
        }

        //If the variable is being reassigned to a string:
        if (isString(value)) {
            value = getStringVariableValue(line);
            int i;
            for (i = 0; i < variableStorage.stringNames.size(); i++) {
                if (variableStorage.stringNames.get(i).equals(variableName)) {
                    variableStorage.stringValues.set(i, getStringVariableValue(line));
                }
            }

            return;
        }

        //Looks for ints, doubles, and booleans if the value is not a string value and is not another variable:
        if (!isVariable(value)) {

            //Looks for doubles:
            if (variableStorage.doubleNames.contains(variableName)) {
                int i;
                for (i = 0; i < variableStorage.doubleNames.size(); i++) {
                    if (variableStorage.doubleNames.get(i).equals(variableName)) {
                        variableStorage.doubleValues.set(i, Double.valueOf(value));
                    }
                }

                return;
            }

            //Looks for booleans:
            if (variableStorage.boolNames.contains(variableName)) {
                int i;
                for (i = 0; i < variableStorage.boolNames.size(); i++) {
                    if (variableStorage.boolNames.get(i).equals(variableName)) {
                        variableStorage.boolValues.set(i, Boolean.valueOf(value));
                    }
                }

                return;
            }
        }
    }

    //Checks if the line starts with a certain string:
    public static boolean is(String line, String _syntax) {

        String temp = "";
        String[] _split = line.split("");

        int i;
        for (i = 0; i < _split.length; i++) {

            temp = temp + _split[i];
            if (temp.equals(_syntax)) {
                return true;
            }
        }

        return false;
    }

    //Returns a string from inside the parenthesis:
    public static String getStringArg(String line) {

        String temp = "";
        int i;
        String[] _line = line.split("");
        boolean startRead = false;
        boolean finishedRead = false;

        for (i = 0; i < line.length(); i++) {

            if (startRead) {

                if (_line[i].equals("\"")) {
                    startRead = false;
                    finishedRead = true;
                }

                if (!finishedRead) {
                    temp = temp + _line[i];
                }
            }

            if (_line[i].equals("\"") && !finishedRead) {
                startRead = true;
            }
        }

        return temp;
    }

    //Returns variable arguments passed into a command statement:
    public static String getGeneralArg(String line) {

        String temp = "";
        int i;
        boolean startRead = false;
        boolean endRead = false;
        String[] _line = line.split("");

        for (i = 0; i < _line.length; i++) {

            if (_line[i].equals("(")) {
                startRead = true;
            }

            if (startRead && !endRead) {

                if (_line[i].equals(")")) {
                    endRead = true;
                }

                temp = temp + _line[i];
            }
        }

        temp = temp.replace("(", "");
        temp = temp.replace(")", "");

        return temp;
    }

    //Returns the name of a variable:
    public static String getVariableName(String line) {
        String[] values = line.split(" ");
        return values[1];
    }

    //Returns the value of a string:
    public static String getStringVariableValue(String line) {

        String[] values = line.split("= ");
        String stringVariableValue = values[1];
        stringVariableValue = stringVariableValue.replace("\"", "");

        return stringVariableValue;
    }

    //Returns the value of a variable that is not a string (must be converted to respective data type later):
    public static String getGeneralVariableValue(String line) {

        String[] values = line.split("= ");

        String generalVariableValue = values[1];
        return generalVariableValue;
    }

    //Finds the value of a string variable:
    public static String findStringValue(String variableName) {

        int i;
        String value = "";
        for (i = 0; i < variableStorage.stringNames.size(); i++) {

            if (variableStorage.stringNames.get(i).equals(variableName)) {
                value = variableStorage.stringValues.get(i);
            }
        }

        return value;
    }

    //Finds the value of a double variable:
    public static double findDoubleValue(String variableName) {

        int i;
        double value = Double.MIN_VALUE;
        for (i = 0; i < variableStorage.doubleNames.size(); i++) {

            if (variableStorage.doubleNames.get(i).equals(variableName)) {
                value = variableStorage.doubleValues.get(i);
            }
        }

        return value;
    }

    //Finds the value of a boolean variable:
    public static boolean findBoolValue(String variableName) {

        int i;
        boolean value = false;
        for (i = 0; i < variableStorage.boolNames.size(); i++) {

            if (variableStorage.boolNames.get(i).equals(variableName)) {
                value = variableStorage.boolValues.get(i);
            }

        }

        return value;
    }

    //Checks if a term is a variable:
    public static boolean isVariable(String term) {

        if (!isString(term) && !isNumber(term) && !isBool(term)) {
            return true;
        }

        return false;
    }

    //Checks if a term is a string:
    public static boolean isString(String chars) {

        int min = 0;
        int max = chars.length() - 1;
        String[] _chars = chars.split("");

        if (_chars[min].equals("\"") && _chars[max].equals("\"")) {
            return true;
        }

        return false;
    }

    //Checks if a term is a number:
    public static boolean isNumber(String term) {

        try {
            Double.parseDouble(term);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    //Checks if a term is a boolean:
    public static boolean isBool(String term) {

        term = term.replace(" ", "");

        if (term.equals("true") || term.equals("false")) {
            return true;
        }

        return false;
    }

    //Checks if a string ends with '()':
    public static boolean endsWithWellFormedParenthesis(String line) {
        String[] raw = line.split("");
        String open = raw[raw.length - 2];
        String close = raw[raw.length - 1];

        if (open.equals("(") && close.equals(")")) {
            return true;
        }

        return false;
    }

    //Run function:
    public void run() {

        //Reads all lines in the file:
        try {
            Path path = Paths.get(filePath);
            rawLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Sends each individual line to parser:
        int i;
        for (i = 0; i < rawLines.size(); i++) {
            parse(rawLines.get(i), i);
        }
    }
}