import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Printer implements FieldPrinter {
    private final ColoredPrinter printer;

    public Printer() {
        printer = new ColoredPrinter();
    }

    public void printField(String[][] matrix, TransferData properties) {
        if(!properties.getProfile().equals("dev")){
            System.out.println("\033[H\033[2J");
        }
        for (String[] arr : matrix) {
            for (String str : arr) {
                if (str.equals(properties.getEmptyChar())) {
                    printer.print(properties.getEmptyChar(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(properties.getEmptyColor()));
                } else if (str.equals(properties.getEnemyChar())) {
                    printer.print(properties.getEnemyChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(properties.getEnemyColor()));
                } else if (str.equals(properties.getGoalChar())) {
                    printer.print(properties.getGoalChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(properties.getGoalColor()));
                } else if (str.equals(properties.getPlayerChar())) {
                    printer.print(properties.getPlayerChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(properties.getPlayerColor()));
                } else if (str.equals(properties.getWallChar())) {
                    printer.print(properties.getWallChar(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, Ansi.BColor.valueOf(properties.getWallColor()));
                }
            }
            System.out.println();
        }
    }
}
