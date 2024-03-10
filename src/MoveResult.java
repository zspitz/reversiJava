public final class MoveResult {
    private String message;
    private char skippedPlayer;

    public String getMessage() {
        return message;
    }

    public char skippedPlayer() {
        return skippedPlayer;
    }

    public MoveResult() {
        this(null, '\0');
    }

    public MoveResult(String message) {
        this(message, '\0');
    }

    public MoveResult(String message, char skippedPlayer) {
        this.message = message;
        this.skippedPlayer = skippedPlayer;
    }
}
