// we are going to create a simple 2-players Connect Four implementation in Java 8
public class ConnectFourGame {
    // each game gets it's own board
    private char[][] board;
    // recent drop locations & count
    private int dropCount;
    // player codes
    // NOTE: 'R' = 82 & ' ' = 32 & 'Y' = 89
    // becomes more relevant when checking for wins...
    public static final char EMPTY = ' ', RED = 'R', YELLOW = 'Y';
    // game status
    public static final int DRAW = 3, RED_WINS = 4, YELLOW_WINS = 5, PLAYING = 6;
    // dimensions
    public static final int COLUMNS = 7, ROWS = 6, MAX_MOVES = COLUMNS * ROWS;

    // keep state of game
    private char current; // current is who should play NEXT
    private boolean isRedTurn;
    public boolean isSinglePlayer;

    public ConnectFourGame(int players) {
        // grid is initialized to 0 or EMPTY
        board = new char[ROWS][COLUMNS];
        this.isSinglePlayer = players == 1;
        ConnectFourGame.cleanBoard(board);
        // set all values to defaults
        this.dropCount = 0;
        ConnectFourGame.resetValues(this);
    }

    // ======================================
    // UTILITY methods for use by constructor
    // and reset
    // ======================================
    private static void resetValues(ConnectFourGame g) {
        // red goes first by convention
        // (or b/c abstracting this logic is too difficult)
        
        // clear the board & reset state
        g.isRedTurn = true;
        g.current = 'R';
        // sets drops to 0
        g.dropCount = 0;
    }

    // RESTART - clears the board
    // similar to constructor
    public boolean restart() {
        if (ConnectFourGame.status(this) == PLAYING)
            return false;
        ConnectFourGame.resetValues(this);
        // set all values to empty
        ConnectFourGame.cleanBoard(this.board);
        return true;
    }
    // ======================================

    // ======================================
    // get value @ index & column
    public char get(int r, int c) {
        return ConnectFourGame.get(this, r, c);
    }
    private static char get(ConnectFourGame g, int r, int c) {
        if (r < 0 || r > ROWS - 1) {
            return ' ';
        }
        if (c < 0 || c > COLUMNS - 1) {
            return ' ';
        }
        return g.board[r][c];
    }
    // ======================================

    // copy board
    public char[][] cloneBoard() {
        char[][] boardCopy = new char[ROWS][COLUMNS]; // 7x6 board
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLUMNS; c++)
                boardCopy[r][c] = this.board[r][c];
        return boardCopy;
    }

    // all implementations of drop piece are static
    public boolean dropPiece(int col) {
        boolean result = ConnectFourGame.dropPiece(this.board, col, this.current);
        if (result) {
            this.dropCount += 1;
            // only if the drop actually suceeds do you switch turn
            this.alternateTurn();
        }
        // manually change the turn here
        return result;
    }

    // the static version is the utility function that does the work
    private static boolean dropPiece(char[][] b, int col, char current) {
        // check for valid location
        if (col < 0 || col > COLUMNS - 1) {
            return false;
        }
        // if there's any space, return true
        for (int r = (ROWS - 1); r >= 0; r--) {
            if (b[r][col] == EMPTY) {
                b[r][col] = current;
                return true;
            }
        }
        // else, no space in the column; return false
        return false;
    }

    // undo the most recent drop (static)
    public static boolean undoDrop(ConnectFourGame g, int c) {
        // start from the top of the row (highest point)
        for (int r = (ROWS - 1); r >= 0; r--) {
            // once you hit the topmost non-empty space
            if (g.board[r][c] != EMPTY) {
                g.board[r][c] = EMPTY;
                g.alternateTurn();
                return true;
            }
        }
        return false;
    }

    private void alternateTurn() {
        this.isRedTurn = !this.isRedTurn;
        this.current = (this.current == RED ? YELLOW : RED);
    }

    // utility function (static)
    private static void cleanBoard(char[][] b) {
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLUMNS; c++)
                b[r][c] = EMPTY;
    }

    // "smart" computer - really just random
    public static int computer(ConnectFourGame g) {
        // random computer
        int rand = 0;
        do {
            if (g.status() == DRAW)
                break;
            rand = (int)(Math.floor(Math.random() * COLUMNS));
        } while (!g.dropPiece(rand));

        if (ConnectFourGame.status(g) == DRAW)
            return -1;
        return rand;
    }

    // static version is used
    public int status() {
        return ConnectFourGame.status(this);
    }
    // static version of status for minimax algorithm
    private static int status(ConnectFourGame g) {
        char[][] b = g.board;

        // check horizontal
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS - 4; c++) {
                int sum = b[r][c] + b[r][c+1] + b[r][c+2] + b[r][c+3];
                if (sum == RED * 4) { return RED_WINS; }
                if (sum == YELLOW * 4) { return YELLOW_WINS; }
            }
        }

        // vertical
        for (int c = 0; c < COLUMNS; c++) {
            for (int r = 0; r <= ROWS - 4; r++) {
                int sum = b[r][c] + b[r+1][c] + b[r+2][c] + b[r+3][c];
                if (sum == RED * 4) { return RED_WINS; }
                if (sum == YELLOW * 4) { return YELLOW_WINS; }
            }
        }
        // backslash diagonal
        for (int r = 0; r <= ROWS - 4; r++) {
            for (int c = 3; c < COLUMNS; c++) {
                int sum = b[r][c] + b[r+1][c-1] + b[r+2][c-2] + b[r+3][c-3];
                if (sum == RED * 4) { return RED_WINS; }
                if (sum == YELLOW * 4) { return YELLOW_WINS; }
            }
        }
        // regular diagonal (forward slash)
        for (int r = 0; r <= ROWS - 4; r++) {
            for (int c = 0; c <= COLUMNS - 4; c++) {
                int sum = b[r][c] + b[r+1][c+1] + b[r+2][c+2] + b[r+3][c+3];
                if (sum == RED * 4) { return RED_WINS; }
                if (sum == YELLOW * 4) { return YELLOW_WINS; }
            }
        }

        if (g.dropCount < MAX_MOVES) {
            return PLAYING;
        }
        return DRAW;
    }

    // used for pretty print
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < ROWS; r++) {
            sb.append('|');
            for (int c = 0; c < COLUMNS; c++) {
                sb.append(EMPTY);
                if (this.board[r][c] == EMPTY)
                    sb.append(EMPTY);
                else if (this.board[r][c] == RED)
                    sb.append(RED);
                else
                    sb.append(YELLOW);
                sb.append(" |");
            }
            sb.append('\n');
        }
        sb.append("-----------------------------\n");

        return sb.toString();
    }

    // get drop count
    public int getDropCount() {
        return this.dropCount;
    }

}