import java.util.ArrayList;
import java.util.List;

public class Voids {

    private static Parser parser = new Parser("");
    private static Structure structure = new Structure();

    public static List<String> voidNames = new ArrayList<>();
    public static List<Integer> voidIndexes = new ArrayList<>();

    public void registerVoidName(String name, int index) {
        voidNames.add(name);
        voidIndexes.add(index);
    }

    public void executeVoid(String line) {

        //Removes the parenthesis from the string:
        String voidName = line.replace("()", "");

        //Finds the index of the void:
        int i;
        int voidIndex = Integer.MIN_VALUE;
        for (i = 0; i < voidNames.size(); i++) {
            if (voidNames.get(i).equals(voidName)) {
                voidIndex = voidIndexes.get(i);
            }
        }

        //Finds the min and max indexes:
        int minIndex = voidIndex + 1;
        int maxIndex = voidIndex + 1;

        while (!parser.rawLines.get(maxIndex).trim().equals(";")) {
            maxIndex++;
        }

        maxIndex = structure.findMatchingSemicolon(maxIndex);

        //Actually executes the code in the void:
        int currentIndex = minIndex;

        while (currentIndex < maxIndex) {

            parser.parse(parser.rawLines.get(currentIndex), currentIndex);
            currentIndex++;
        }
    }
}