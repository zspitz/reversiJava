import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {
    private static GameLogic gameLogic = new GameLogic();

    private static Scanner scanner = new Scanner(System.in);
    private static Pattern pattern = Pattern.compile("^(\\d*)\\D+(\\d*)$");

    private static int[] getInput() {
        while (true) {
            System.out.println("Enter row,col separated by comma or non-digit:");
            final var matcher = pattern.matcher(scanner.nextLine());
            if (!matcher.matches()) {
                System.out.println("Invald input.");
            }

            // TODO handle numbers out of bounds for integer
            return new int[] {
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2))
            };
        }
    }

    public static void main(String[] args) throws Exception {
        draw();

        while (!gameLogic.getIsGameOver()) {
            final var pair = getInput();
            final var result = gameLogic.playMove(pair[0], pair[1]);
            final var msg = result.getMessage();

            if (GameLogic.INVALID_MOVE.equals(msg)) {
                System.out.println(msg);
                continue;
            }

            draw();

            if (GameLogic.NO_MOVES_FOR_CURRENT_PLAYER.equals(msg)) {
                System.out.println(msg + " Skipped player: " + result.skippedPlayer());
            } else if (GameLogic.NO_MOVES_FOR_ANY_PLAYER.equals(msg)) {
                break;
            }
        }

        System.out.println("Game over");
        // TODO print score
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

    private static void draw() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        final var output = Arrays.stream(gameLogic.getState())
                .map(row -> rowOutput(row))
                .collect(Collectors.joining("\n"));
        System.out.println(output);
    }
}
