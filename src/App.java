public class App {
    private static GameLogic gameLogic = new GameLogic();

    public static void main(String[] args) throws Exception {
        var state = gameLogic.getState();
        draw(state);
    }

    private static void draw(char[][] state) {
        final String ANSI_BLACK = "\u001B[40m";
        final String ANSI_WHITE = "\u001B[47m";
        final String ANSI_GREEN = "\u001B[42m";
        final String ANSI_RESET = "\u001B[0m";

        for (char[] row : state) {
            for (char cell : row) {
                cell = cell == '\0' ? ' ' : cell;
                final String toPrint = (cell == 'b' ? ANSI_BLACK : cell == 'w' ? ANSI_WHITE : ANSI_GREEN) + cell
                        + ANSI_RESET;
                System.out.print(toPrint);
            }
            System.out.println();
        }
    }
}
