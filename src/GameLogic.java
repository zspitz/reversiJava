import java.util.Collections;
import java.util.Map;

public class GameLogic {
    private char[][] state = new char[8][8];
    private char currentPlayer = 'b';
    private Map<String, String[]> availableMoves = Collections.emptyMap();

    public GameLogic() {
        super();

        state[3][3] = 'b';
        state[3][4] = 'w';
        state[4][3] = 'w';
        state[4][4] = 'b';
        updateAvailableMoves();
    }

    private void updateAvailableMoves() {

    }

    public char[][] getState() {
        // TODO return immutable copy
        return state;
    }
}
