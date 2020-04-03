public class CombinationVariableAssignment {

    private static Parser parser = new Parser("");

    //Returns the raw assignment value for a newly initialized variable:
    public String getRawAssignmentValue(String line) {
        return line.split("= ")[1];
    }

    //Finds out if the assignment value is a combination of variables:
    public boolean isCombinationAssignmentValue(String line) {

        String rawAssignmentValue = this.getRawAssignmentValue(line);
        if (rawAssignmentValue.contains("+") || rawAssignmentValue.contains("-") || rawAssignmentValue.contains("*") || rawAssignmentValue.contains("/") || rawAssignmentValue.contains("^")) {
            return true;
        }

        return false;
    }

    //removes start and end quotes:
    public String removeStartAndEndQuotes(String term) {
        return term.replaceAll("^\"|\"$", "");
    }

    /*
    * Handles operations between two numbers:
    * Possibilities:
    * (var, var)
    * (num, num)
    * (var, num)
    * (num, var)
    * */
    public double handleMath(String assignment) {

        //Handles addition:
        if (assignment.contains("+")) {
            String _arg1 = assignment.split("\\+")[0];
            String _arg2 = assignment.split("\\+")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();
            double arg1 = Double.MIN_VALUE;
            double arg2 = Double.MIN_VALUE;

            try {
                arg1 = Double.valueOf(_arg1);
            }
            catch (NumberFormatException e) {
                arg1 = parser.findDoubleValue(_arg1);
            }

            try {
                arg2 = Double.valueOf(_arg2);
            }
            catch (NumberFormatException e) {
                arg2 = parser.findDoubleValue(_arg2);
            }

            return arg1 + arg2;
        }

        //Handles subtraction:
        if (assignment.contains("-")) {
            String _arg1 = assignment.split("-")[0];
            String _arg2 = assignment.split("-")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();
            double arg1 = Double.MIN_VALUE;
            double arg2 = Double.MIN_VALUE;

            try {
                arg1 = Double.valueOf(_arg1);
            }
            catch (NumberFormatException e) {
                arg1 = parser.findDoubleValue(_arg1);
            }

            try {
                arg2 = Double.valueOf(_arg2);
            }
            catch (NumberFormatException e) {
                arg2 = parser.findDoubleValue(_arg2);
            }

            return arg1 - arg2;
        }

        //Handles multiplication:
        if (assignment.contains("*")) {
            String _arg1 = assignment.split("\\*")[0];
            String _arg2 = assignment.split("\\*")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();
            double arg1 = Double.MIN_VALUE;
            double arg2 = Double.MIN_VALUE;

            try {
                arg1 = Double.valueOf(_arg1);
            }
            catch (NumberFormatException e) {
                arg1 = parser.findDoubleValue(_arg1);
            }

            try {
                arg2 = Double.valueOf(_arg2);
            }
            catch (NumberFormatException e) {
                arg2 = parser.findDoubleValue(_arg2);
            }

            return arg1 * arg2;
        }

        //Handles division:
        if (assignment.contains("/")) {
            String _arg1 = assignment.split("/")[0];
            String _arg2 = assignment.split("/")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();
            double arg1 = Double.MIN_VALUE;
            double arg2 = Double.MIN_VALUE;

            try {
                arg1 = Double.valueOf(_arg1);
            }
            catch (NumberFormatException e) {
                arg1 = parser.findDoubleValue(_arg1);
            }

            try {
                arg2 = Double.valueOf(_arg2);
            }
            catch (NumberFormatException e) {
                arg2 = parser.findDoubleValue(_arg2);
            }

            return arg1 / arg2;
        }

        //Handles powers:
        if (assignment.contains("^")) {
            String _arg1 = assignment.split("\\^")[0];
            String _arg2 = assignment.split("\\^")[1];
            _arg1 = _arg1.trim();
            _arg2 = _arg2.trim();
            double arg1 = Double.MIN_VALUE;
            double arg2 = Double.MIN_VALUE;

            try {
                arg1 = Double.valueOf(_arg1);
            }
            catch (NumberFormatException e) {
                arg1 = parser.findDoubleValue(_arg1);
            }

            try {
                arg2 = Double.valueOf(_arg2);
            }
            catch (NumberFormatException e) {
                arg2 = parser.findDoubleValue(_arg2);
            }

            return Math.pow(arg1, arg2);
        }

        return 0;
    }
}