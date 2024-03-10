import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.IntStream;

public final class GameLogic {
    public final static String OUT_OF_BOUNDS = "Out of bounds";
    public final static String INVALID_MOVE = "Invalid move";
    public final static String NO_MOVES_FOR_CURRENT_PLAYER = "No moves for current player";
    public final static String NO_MOVES_FOR_ANY_PLAYER = "No moves for any player";

    private char[][] state = new char[8][8];
    private char currentPlayer = 'b';
    private Map<Point, Point[]> availableMoves = Collections.emptyMap();
    private boolean isGameOver = false;

    public GameLogic() {
        state[3][3] = 'b';
        state[3][4] = 'w';
        state[4][3] = 'w';
        state[4][4] = 'b';
        updateAvailableMoves();
    }

    final static Point[] directions = IntStream.rangeClosed(-1, 1)
            .boxed()
            .flatMap(x -> IntStream.rangeClosed(-1, 1).mapToObj(y -> new Point(x, y)))
            .filter(p -> p.getX() != 0 || p.getY() != 0)
            .toArray(Point[]::new);

    private Point[] getChanges(int row, int col) {
        final var changes = new ArrayList<Point>();
        if (state[row][col] != '\0') {
            return changes.toArray(Point[]::new);
        }

        for (var offset : directions) {
            final var line = new ArrayList<Point>();
            var offsetCount = 1;
            while (true) {
                var testRow = row + offset.getY() * offsetCount;
                var testCol = col + offset.getX() * offsetCount;

                // out of bounds of board
                if (testRow < 0 || testRow > 7 || testCol < 0 || testCol > 7) {
                    break;
                }

                final var testState = state[testRow][testCol];

                // found empty space
                if (testState == '\0') {
                    break;
                }

                // found end of line
                if (testState == currentPlayer) {
                    for (var change : line) {
                        changes.add(change);
                        break;
                    }
                }

                line.add(new Point(testRow, testCol));
                offsetCount += 1;
            }
        }

        return changes.toArray(Point[]::new);
    }

    private void updateAvailableMoves() {
        availableMoves.clear();

        for (var rowIndex = 0; rowIndex <= 7; rowIndex++) {
            for (var colIndex = 0; colIndex <= 7; colIndex++) {
                final var changes = getChanges(rowIndex, colIndex);
                if (changes.length == 0) {
                    continue;
                }
                availableMoves.put(
                        new Point(rowIndex, colIndex),
                        changes);
            }
        }
    }

    public char[][] getState() {
        // TODO return immutable copy
        return state;
    }

    public Map<Point, Point[]> getAvailableMoves() {
        // TODO return immtable copy
        return availableMoves;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    private void switchPlayer() {
        switch (currentPlayer) {
            case 'b':
                currentPlayer = 'w';
                break;
            case 'w':
                currentPlayer = 'b';
                break;
        }
    }

    public MoveResult playMove(int row, int col) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return new MoveResult(OUT_OF_BOUNDS);
        }

        var changes = getChanges(row, col);
        if (changes.length == 0) {
            return new MoveResult(INVALID_MOVE);
        }

        state[row][col] = currentPlayer;
        for (var change : changes) {
            state[change.getY()][change.getX()] = currentPlayer;
        }

        switchPlayer();
        updateAvailableMoves();
        if (!availableMoves.isEmpty()) {
            return new MoveResult();
        }

        final var skippedPlayer = currentPlayer;
        switchPlayer();
        updateAvailableMoves();
        if (!availableMoves.isEmpty()) {
            return new MoveResult(NO_MOVES_FOR_CURRENT_PLAYER, skippedPlayer);
        }

        this.isGameOver = true;
        return new MoveResult(NO_MOVES_FOR_ANY_PLAYER);
    }
}
