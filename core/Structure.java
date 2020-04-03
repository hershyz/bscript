import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Structure {

    private static Parser parser = new Parser("");
    private static Syntax syntax = new Syntax();

    //Finds the semicolon that goes with an if, while, or void statement, the index of the initial calculated farthest semicolon must be passed:
    public int findMatchingSemicolon(int listIndex) {

        List<Integer> indexesOfSemicolons = new ArrayList<Integer>();

        int i = listIndex;
        boolean _continue = true;

        while (i < parser.rawLines.size() && _continue) {

            String currentLine = parser.rawLines.get(i);
            currentLine = currentLine.trim();

            if (parser.is(currentLine, syntax.ifCondition) || parser.is(currentLine, syntax.whileLoop) || parser.is(currentLine, syntax.voidSyntax)) {
                _continue = false;
            }

            if (currentLine.equals(";")) {
                indexesOfSemicolons.add(i);
            }

            i++;
        }

        Collections.sort(indexesOfSemicolons);
        return indexesOfSemicolons.get(indexesOfSemicolons.size() - 1);
    }
}