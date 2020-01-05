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
    boolean isRedTurn, isSingle;

    public ConnectFourGame(int numPlayers) {
        this.numPlayers = numPlayers;
        this.isSingle = numPlayers == 1;
        this.isRedTurn = true;
        // grid is initialized to 0 or EMPTY
        board = new int[ROWS][COLUMNS];
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLUMNS; c++)
                board[r][c] = EMPTY;
    }

    // copy board
    public int[][] copyBoard() {
        int[][] boardCopy = new int[ROWS][COLUMNS]; // 7x6 board
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLUMNS; c++)
                boardCopy[r][c] = this.board[r][c];
        return boardCopy;
    }

    // dropPiece & undo are both static b/c
    // these methods will be used by the minimax algo.
    public boolean dropPiece(int c, int piece) {
        if (c < 0 || c > COLUMNS - 1) {
            return false;
        }
        for (int r = (ROWS - 1); r >= 0; r--) {
            if (board[r][c] == EMPTY) {
                board[r][c] = piece;
                return true;
            }
        }
        return false;
    }

    public void undoDrop(int c) {
        for (int r = (ROWS - 1); r >= 0; r--) {
            if (board[r][c] != EMPTY) {
                board[r][c] = EMPTY;
                return;
            }
        }
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

    // like toString
    public String draw() {
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

    // we use Streams to make a more concise method 
    // for  representing the board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] s1: board) {
            sb.append(Arrays.toString(s1)).append('\n');
        }
        return sb.toString();
    }

    public void alternateTurn() {
        isRedTurn = !isRedTurn;
    }

    // don't understand this code? I guess that makes 2 of us
    // I didn't comment a couple of years back... whoops
    public int status() {
        int[][] b = board;
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

    // scores for minimax algorithm
    public static int RED_WIN_SCORE = 10;
    public static int BLACK_WIN_SCORE = -10;
    public static int TIE_SCORE = 0;
    // the heart of it all
    // public static int minimax(int[][] b, int depth, boolean isMaximizing) {
    // 	int gameStatus = status(b);
    // 	if (gameStatus == RED_WINS) { return RED_WIN_SCORE; }
    // 	if (gameStatus == BLACK_WINS) { return BLACK_WIN_SCORE; }

    // 	if (depth >= 5) {
    // 		return (isMaximizing ? 500 : -500);
    // 	}

    // 	if (!isMaximizing) {
    // 		int bestScore = -500;
    // 		for (int c = 0; c < 7; c++) {
    // 			if (dropPiece(b, c, BLACK)) {
    // 				int score = minimax(b, depth + 1, false);
    // 				undoMove(b, c);
    // 				bestScore = Math.max(score, bestScore);
    // 			}
    // 		}
    // 		return bestScore;
    // 	} else {
    // 		int bestScore = 500;
    // 		for (int c = 0; c < 7; c++) {
    // 			if (dropPiece(b, c, RED)) {
    // 				int score = minimax(b, depth + 1, true);
    // 				undoMove(b, c);
    // 				bestScore = Math.min(score, bestScore);
    // 			}
    // 		}
    // 		return bestScore;
    // 	}

    // }

    // "smart" computer
    public int computer() {
        // random computer
        int rand = 0;
        do {
            if (status() == DRAW)
                break;
            rand = (int)(Math.floor(Math.random() * COLUMNS));
        } while (!dropPiece(rand, BLACK));

        if (status() == DRAW)
            return -1;
        else
            return rand;
        
        // hopefully a smarter computer:
        // int[][] copiedBoard = copyBoard();

        // int bestMoveLoc = 0;
        // int bestScore = -500;

        // for (int c = 0; c < 7; c++) {
        // 	if (dropPiece(copiedBoard, c, BLACK)) {
        // 		int score = minimax(copiedBoard, 0, false);
        // 		undoMove(copiedBoard, c);
        // 		if (score > bestScore) {
        // 			bestScore = score;
        // 			bestMoveLoc = c;
        // 		}
        // 	}
        // }

        // play the location
        // dropPiece(ConnectFourFrame.board, bestMoveLoc, BLACK);
        // return bestMoveLoc;

        //		return 1;
    }

    // public static void main(String[] args) {
    //     System.out.println("Connect Four Game by Samarth Dave");

    //     // no need to calculate
    //     // int moves = ROWS * COLUMNS;

    //     int numPlayers = 1;
    //     new ConnectFourFrame(numPlayers);

    //     // we create the board instance
    //     ConnectFourGame board = new ConnectFourGame(1);

    //     // we explain users how to enter their choices
    //     System.out.println("Use 0-" + (COLUMNS - 1) + " to choose a column");
    //     // we display initial board
    //     System.out.println(board);

    //     // a simpler solution that iterates turns
    //     boolean isWinner = false;

    //     while (true) {
    //         int loc = (isRedTurn ? 0 : 1);
    //         char symbol = PLAYERS[loc];

    //         // no scanner yet...
    //         // board.chooseAndDrop(symbol, input);

    //         // update the java swing board (using repaint)
    //         // repaint()
    //         // System.out.println(board); // and reprint the board for  debugging

    //         // board already checks for  wins (panel will call this function)
    //         if (board.isWinningPlay()) {
    //             System.out.println("\nPlayer " + symbol + " wins!");
    //             isWinner = true;
    //             break;
    //         }

    //         // alternate turn
    //         isRedTurn = !isRedTurn;
    //     }

    //     if (!isWinner) {
    //         System.out.println("Game over. No winner. Try again!");
    //     }

    // }

}