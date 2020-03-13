// we are going to create a simple 2-players Connect Four implementation in Java 8
public class ConnectFourGame {
    // each game gets it's own board
    private char[][] board;
    // player codes
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
        // red goes first by convention
        // (or b/c abstracting this logic is too difficult)
        this.isRedTurn = true;
        this.current = 'R';
        // if only one player
        this.isSinglePlayer = players == 1;

        // grid is initialized to 0 or EMPTY
        board = new char[ROWS][COLUMNS];
        ConnectFourGame.cleanBoard(board);
    }

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
            // only if the drop actually suceeds do you switch turn
            this.alternateTurn();
        }
        // manually change the turn here
        return result;
    }

    // more abstraction that comes as a necessity, not as clean
    // but code that I'm somewhat proud of ... :/
    private static boolean dropPiece(char[][] b, int col, char current) {
        // check for valid location
        if (col < 0 || col > COLUMNS - 1) {
            return false;
        }
        // if there's any space, return true
        for (int r = (ROWS - 1); r >= 0; r--) {
            if (b[r][col] == EMPTY) {
                b[r][col] = current;
                // g.alternateTurn();
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

    // RESTART - clears the board
    // similar to constructor
    public boolean restart() {
        if (ConnectFourGame.status(this) == PLAYING)
            return false;
        // clear the board & reset state
        this.isRedTurn = true;
        this.current = 'R';
        // set all values to empty
        ConnectFourGame.cleanBoard(this.board);
        return true;
    }

    public void alternateTurn() {
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
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c <= 3; c++) {
                if (b[r][c] == RED && b[r][c + 1] == RED &&
                    b[r][c + 2] == RED && b[r][c + 3] == RED)
                    return RED_WINS;
                else if (b[r][c] == YELLOW && b[r][c + 1] == YELLOW &&
                    b[r][c + 2] == YELLOW && b[r][c + 3] == YELLOW)
                    return YELLOW_WINS;
            }
        }
        // vertical
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                if (b[r][c] == RED && b[r + 1][c] == RED &&
                    b[r + 2][c] == RED && b[r + 3][c] == RED)
                    return RED_WINS;
                else if (b[r][c] == YELLOW && b[r + 1][c] == YELLOW &&
                    b[r + 2][c] == YELLOW && b[r + 3][c] == YELLOW)
                    return YELLOW_WINS;
            }
        }
        for (int r = 0; r <= 2; r++) {
            for (int c = 3; c < COLUMNS; c++) {
                if (b[r][c] == RED && b[r + 1][c - 1] == RED &&
                    b[r + 2][c - 2] == RED && b[r + 3][c - 3] == RED)
                    return RED_WINS;
                else if (b[r][c] == YELLOW && b[r + 1][c - 1] == YELLOW &&
                    b[r + 2][c - 2] == YELLOW && b[r + 3][c - 3] == YELLOW)
                    return YELLOW_WINS;
            }
        }
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 3; c++) {
                if (b[r][c] == RED && b[r + 1][c + 1] == RED &&
                    b[r + 2][c + 2] == RED && b[r + 3][c + 3] == RED)
                    return RED_WINS;
                else if (b[r][c] == YELLOW && b[r + 1][c + 1] == YELLOW &&
                    b[r + 2][c + 2] == YELLOW && b[r + 3][c + 3] == YELLOW)
                    return YELLOW_WINS;
            }
        }
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                if (b[r][c] == EMPTY)
                    return PLAYING;
            }
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

}