import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private static final int RED_WIN_SCORE = 10,
                BLACK_WIN_SCORE = -10,
                TIE_SCORE = 0;

    // next 2 methods implement a smarter computer
    // (At the moment, a very useless computer since I haven't implemented alpha-beta pruning.)
    private static int minimax(int[][] b, int depth, boolean isMaximizing) {
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

        // vanilla minimax doesn't work w/ a game like Connect4
        // eventually, I hope to use alpha-beta pruning
        // but for now, these 3 lines prevent your computer from exploding...
        if (depth == 4) {
            return 0;
        }

        // assuming computer is BLACK and second player
        // I should generalize this method but will retain for now
        int current = isMaximizing ? BLACK : RED; // current player

        if (isMaximizing) {
            int bestScore = -1000;
            for (int c = 0; c < COLUMNS; c++) {
                    // Is the spot available?
                if (dropPiece(b, c, current)) {
                    int score = minimax(b, depth + 1, false);
                    undoDrop(b, c);
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int c = 0; c < COLUMNS; c++) {
                if (dropPiece(b, c, current)) { // use opponent/human here
                    int score = minimax(b, depth + 1, true);
                    undoDrop(b, c);
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    // scores the moves and plays the "best" one from above method
    public int minimaxComputer(int player) {
        int[][] clone = cloneBoard();
        int bestScore = -1000;
        int move = -1;

        for (int c = 0; c < COLUMNS; c++) {
            if (dropPiece(clone, c, player)) {
                int score = minimax(clone, 0, false);

                if (score > bestScore) {
                    bestScore = score;
                    move = c;
                }
            }
        }
        
        dropPiece(move, player);
        return move;
    }

}