public class IncrementOperators {

    private static Parser parser = new Parser("");
    private double incrementFactor = 1.00;

    //Increments an existing double variable by the increment factor:
    public void increment(String variableName, String incrementType) {

        variableName = variableName.trim();
        incrementType = incrementType.trim();
        double existingValue = parser.findDoubleValue(variableName);

        //Adds:
        if (incrementType.equals("++")) {

            int i;
            for (i = 0; i < parser.variableStorage.doubleNames.size(); i++) {
                if (parser.variableStorage.doubleNames.get(i).equals(variableName)) {
                    double newValue = existingValue + incrementFactor;
                    parser.variableStorage.doubleValues.set(i, newValue);
                }
            }
        }

        //Subtracts:
        if (incrementType.equals("--")) {

            int i;
            for (i = 0; i < parser.variableStorage.doubleNames.size(); i++) {
                if (parser.variableStorage.doubleNames.get(i).equals(variableName)) {
                    double newValue = existingValue - incrementFactor;
                    parser.variableStorage.doubleValues.set(i, newValue);
                }
            }
        }
    }
}