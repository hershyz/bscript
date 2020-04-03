public class WhileLoops {

    private static Parser parser = new Parser("");
    private static IfConditions ifConditions = new IfConditions();
    private static Structure structure = new Structure();

    public void loop(String parameter, int listIndex) {

        boolean conditionTrue = ifConditions.solveCondition(parameter);

        //Finds the index of the semicolon:
        int maxIndex = listIndex;
        while (!parser.rawLines.get(maxIndex).trim().equals(";")) {
            maxIndex++;
        }

        if (!conditionTrue) {
            parser.readLines = false;
        }

        if (conditionTrue) {

            maxIndex = structure.findMatchingSemicolon(maxIndex);

            boolean test = ifConditions.solveCondition(parameter);
            int minIndex = listIndex + 1;
            int currentIndex = listIndex + 1;

            while (test) {

                while (currentIndex < maxIndex && test) {

                    String line = parser.rawLines.get(currentIndex);
                    parser.parse(line, currentIndex);
                    currentIndex++;
                }


                test = ifConditions.solveCondition(parameter);
                currentIndex = minIndex;
            }
        }
    }
}