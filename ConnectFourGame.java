// we are going to create a simple 2-players Connect Four implementation in Java 8
public class ConnectFourGame {
    public static int[][] board;
    // player codes
    public static final int EMPTY = 0, RED = 1, BLACK = 2;
    // game status
    public static final int DRAW = 3, RED_WINS = 4, BLACK_WINS = 5, PLAYING = 6;
    // dimensions
    public static final int COLUMNS = 7, ROWS = 6, MAX_MOVES = COLUMNS * ROWS;

    public int numPlayers;
    boolean isRedTurn, isSinglePlayer;

    public ConnectFourGame(int numPlayers) {
        this.numPlayers = numPlayers;
        this.isSinglePlayer = numPlayers == 1;
        this.isRedTurn = true;
        // grid is initialized to 0 or EMPTY
        board = new int[ROWS][COLUMNS];
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLUMNS; c++)
                board[r][c] = EMPTY;
    }

    // copy board
    public int[][] cloneBoard() {
        int[][] boardCopy = new int[ROWS][COLUMNS]; // 7x6 board
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLUMNS; c++)
                boardCopy[r][c] = this.board[r][c];
        return boardCopy;
    }

    public boolean dropPiece(int c, int piece) {
        return ConnectFourGame.dropPiece(board, c, piece);
    }

    private static boolean dropPiece(int[][] b, int c, int piece) {
        if (c < 0 || c > COLUMNS - 1) {
            return false;
        }
        for (int r = (ROWS - 1); r >= 0; r--) {
            if (b[r][c] == EMPTY) {
                b[r][c] = piece;
                return true;
            }
        }
        return false;
    }

    public static boolean undoDrop(int[][] b, int c) {
        for (int r = (ROWS - 1); r >= 0; r--) {
            if (b[r][c] != EMPTY) {
                b[r][c] = EMPTY;
                return true;
            }
        }
        return false;
    }

    // RESTART - clears the board
    public boolean restart() {
        if (status() == PLAYING)
            return false;
        // clear the board & reset state
        isRedTurn = true;
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLUMNS; c++)
                board[r][c] = EMPTY;
        return true;
    }

    public void alternateTurn() {
        isRedTurn = !isRedTurn;
    }

    // "smart" computer
    public int computer(int player) {
        // random computer
        int rand = 0;
        do {
            if (status() == DRAW)
                break;
            rand = (int)(Math.floor(Math.random() * COLUMNS));
        } while (!dropPiece(rand, player));

        if (status() == DRAW)
            return -1;
        return rand;
    }

    // don't understand this code? I guess that makes 2 of us
    // I didn't comment a couple of years back... whoops
    public int status() {
        return ConnectFourGame.status(board);
    }

    // static version of status for minimax algorithm
    private static int status(int[][] b) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c <= 3; c++) {
                if (b[r][c] == RED && b[r][c + 1] == RED &&
                    b[r][c + 2] == RED && b[r][c + 3] == RED)
                    return RED_WINS;
                else if (b[r][c] == BLACK && b[r][c + 1] == BLACK &&
                    b[r][c + 2] == BLACK && b[r][c + 3] == BLACK)
                    return BLACK_WINS;
            }
        }
        // vertical
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                if (b[r][c] == RED && b[r + 1][c] == RED &&
                    b[r + 2][c] == RED && b[r + 3][c] == RED)
                    return RED_WINS;
                else if (b[r][c] == BLACK && b[r + 1][c] == BLACK &&
                    b[r + 2][c] == BLACK && b[r + 3][c] == BLACK)
                    return BLACK_WINS;
            }
        }
        for (int r = 0; r <= 2; r++) {
            for (int c = 3; c < COLUMNS; c++) {
                if (b[r][c] == RED && b[r + 1][c - 1] == RED &&
                    b[r + 2][c - 2] == RED && b[r + 3][c - 3] == RED)
                    return RED_WINS;
                else if (b[r][c] == BLACK && b[r + 1][c - 1] == BLACK &&
                    b[r + 2][c - 2] == BLACK && b[r + 3][c - 3] == BLACK)
                    return BLACK_WINS;
            }
        }
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 3; c++) {
                if (b[r][c] == RED && b[r + 1][c + 1] == RED &&
                    b[r + 2][c + 2] == RED && b[r + 3][c + 3] == RED)
                    return RED_WINS;
                else if (b[r][c] == BLACK && b[r + 1][c + 1] == BLACK &&
                    b[r + 2][c + 2] == BLACK && b[r + 3][c + 3] == BLACK)
                    return BLACK_WINS;
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
            sb.append("|");
            for (int c = 0; c < COLUMNS; c++) {
                sb.append(" ");
                if (board[r][c] == EMPTY)
                    sb.append(" ");
                else if (board[r][c] == RED)
                    sb.append("R");
                else
                    sb.append("B");
                sb.append(" |");
            }
            sb.append("\n");
        }
        sb.append("-----------------------------\n");

        return sb.toString();
    }

    // -----------------------------------------
    // my attempt at using the minimax algorithm
    // -----------------------------------------

    // scores for minimax algo.
    private static final int POS_INF         = Integer.MAX_VALUE;
    private static final int NEG_INF         = Integer.MIN_VALUE;
    private static final int RED_WIN_SCORE   =  1000;
    private static final int BLACK_WIN_SCORE = -1000;
    private static final int TIE_SCORE       = 0;

    // next 2 methods implement a smarter computer
    // (At the moment, a very useless computer since I haven't implemented alpha-beta pruning.)
    private static int minimax(int[][] b, int depth, int alpha, int beta, boolean isMaximizing) {
        switch (status(b)) {
            case DRAW:
                return TIE_SCORE;
            case RED_WINS:
                return RED_WIN_SCORE;
            case BLACK_WINS:
                return BLACK_WIN_SCORE;
            default:
                // do nothing
        }

        if (depth == 0) {
            System.out.println("depth is 0...");
            return BLACK_WIN_SCORE;
        }

        int value = 0;
        int piece = isMaximizing ? RED : BLACK;
        if (isMaximizing) {
            value = NEG_INF;
            for (int i = 0; i < COLUMNS; i++) {
                // drop & undo
                dropPiece(b, i, piece);
                value = Math.max(value, minimax(b, depth - 1, alpha, beta, false));
                undoDrop(b, i); // so we don't need to make a copy of the board
                // get max (since we're maximizing)
                alpha = Math.max(alpha, value);
                // snip snip?
                if (alpha >= beta) { break; }
            }
            return value;
        } else {
            // minimizing player (black in this case)
            value = POS_INF;
            for (int i = 0; i < COLUMNS; i++) {
                // drop & undo
                dropPiece(b, i, piece);
                value = Math.min(value, minimax(b, depth - 1, alpha, beta, true));
                undoDrop(b, i);
                // get minimum values
                beta = Math.min(beta, value);
                // snip snip?
                if (alpha >= beta) { break; }
            }
            return value;
        }

    }

    // scores the moves and plays the "best" one from above method
    public int minimaxComputer(int player) {
        String playerString = (player == ConnectFourGame.BLACK) ? "BLACK" : "RED";
        System.out.println("PLAYING FOR " + playerString);

        int d = 3;
        boolean isMax = player == RED; // true if red, false if black
        return minimax(board, d, NEG_INF, POS_INF, isMax);
    }

}