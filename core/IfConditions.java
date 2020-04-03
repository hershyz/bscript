public class IfConditions {

    private static Parser parser = new Parser("");

    /*
    * Solves a condition between two variables:
    * Condition can be:
    * <
    * >
    * ==
    * !=
    * <=
    * >=
    * */
    public boolean solveCondition(String rawParameter) {

        //Handles less than or equal to statements, can only be between doubles:
        if (rawParameter.contains("<=")) {
            String _arg1 = rawParameter.split("<=")[0];
            String _arg2 = rawParameter.split("<=")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();
            double arg1 = parser.findDoubleValue(_arg1);
            double arg2 = parser.findDoubleValue(_arg2);

            if (arg1 <= arg2) {
                return true;
            }

            return false;
        }

        //Handles greater than or equal to statements, can only be between doubles:
        if (rawParameter.contains(">=")) {
            String _arg1 = rawParameter.split(">=")[0];
            String _arg2 = rawParameter.split(">=")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();
            double arg1 = parser.findDoubleValue(_arg1);
            double arg2 = parser.findDoubleValue(_arg2);

            if (arg1 >= arg2) {
                return true;
            }

            return false;
        }

        //Handles less than statements, can only be between doubles:
        if (rawParameter.contains("<")) {
            String _arg1 = rawParameter.split("<")[0];
            String _arg2 = rawParameter.split("<")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();
            double arg1 = parser.findDoubleValue(_arg1);
            double arg2 = parser.findDoubleValue(_arg2);

            if (arg1 < arg2) {
                return true;
            }

            return false;
        }

        //Handles greater than statements, can only be between doubles:
        if (rawParameter.contains(">")) {
            String _arg1 = rawParameter.split(">")[0];
            String _arg2 = rawParameter.split(">")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();
            double arg1 = parser.findDoubleValue(_arg1);
            double arg2 = parser.findDoubleValue(_arg2);

            if (arg1 > arg2) {
                return true;
            }

            return false;
        }

        //Handles equal to statements, can be between any variable:
        if (rawParameter.contains("==")) {
            String _arg1 = rawParameter.split("==")[0];
            String _arg2 = rawParameter.split("==")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();

            //If strings are being compared:
            if (this.isString(_arg1)) {
                String arg1 = parser.findStringValue(_arg1);
                String arg2 = parser.findStringValue(_arg2);

                if (arg1.equals(arg2)) {
                    return true;
                }

                return false;
            }

            //If booleans are being compared:
            if (this.isBoolean(_arg1)) {
                boolean arg1 = parser.findBoolValue(_arg1);
                boolean arg2 = parser.findBoolValue(_arg2);

                if (arg1 == arg2) {
                    return true;
                }

                return false;
            }

            //If the variable name isn't a string or a boolean, the program finds doubles:
            double arg1 = parser.findDoubleValue(_arg1);
            double arg2 = parser.findDoubleValue(_arg2);

            if (arg1 == arg2) {
                return true;
            }

            return false;
        }

        //Handles not equal to statements, can be between any variable:
        if (rawParameter.contains("!=")) {
            String _arg1 = rawParameter.split("!=")[0];
            String _arg2 = rawParameter.split("!=")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();

            //If strings are being compared:
            if (this.isString(_arg1)) {
                String arg1 = parser.findStringValue(_arg1);
                String arg2 = parser.findStringValue(_arg2);

                if (!arg1.equals(arg2)) {
                    return true;
                }

                return false;
            }

            //If booleans are being compared:
            if (this.isBoolean(_arg1)) {
                boolean arg1 = parser.findBoolValue(_arg1);
                boolean arg2 = parser.findBoolValue(_arg2);

                if (arg1 != arg2) {
                    return true;
                }

                return false;
            }

            //If the variable name isn't a string or a boolean, the program finds doubles:
            double arg1 = parser.findDoubleValue(_arg1);
            double arg2 = parser.findDoubleValue(_arg2);

            if (arg1 != arg2) {
                return true;
            }

            return false;
        }

        return false;
    }

    //Checks if a string variable exists:
    public boolean isString(String variableName) {
        return parser.variableStorage.stringNames.contains(variableName);
    }

    //Checks if a boolean variable exists:
    public boolean isBoolean(String variableName) {
        return parser.variableStorage.boolNames.contains(variableName);
    }
}