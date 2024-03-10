public class App {
    private static GameLogic gameLogic = new GameLogic();

    public static void main(String[] args) throws Exception {
        var state = gameLogic.getState();
        draw(state);
    }

    private static void draw(char[][] state) {
        for (char[] row : state) {
            for (char cell : row) {
                System.out.print(cell == '\0' ? ' ' : cell);
            }
            System.out.println();
        }
    }
}
