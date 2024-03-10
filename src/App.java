import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.stream.Collectors;

public class App {
    private static GameLogic gameLogic = new GameLogic();

    public static void main(String[] args) throws Exception {
        var state = gameLogic.getState();
        draw(state);

    }

    static final String ANSI_BLACK = "\u001B[40m";
    static final String ANSI_WHITE = "\u001B[47m";
    static final String ANSI_GREEN = "\u001B[42m";
    static final String ANSI_RESET = "\u001B[0m";

    private static String cellOutput(char cell) {
        final var color = cell == 'b' ? ANSI_BLACK : cell == 'w' ? ANSI_WHITE : ANSI_GREEN;
        cell = cell != '\0' ? cell : ' ';
        return color + cell + ANSI_RESET;
    }

    private static String rowOutput(char[] row) {
        return CharBuffer.wrap(row).chars()
                .mapToObj(x -> cellOutput((char) x))
                .collect(Collectors.joining());
    }

    private static void draw(char[][] state) {
        final var output = Arrays.stream(state)
                .map(row -> rowOutput(row))
                .collect(Collectors.joining("\n"));
        System.out.print(output);
    }
}
