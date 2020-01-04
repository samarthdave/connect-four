import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// we are going to create a simple 2-players Connect Four implementation in Java 8
public class ConnectFourGame {
    public static int[][] board;
    public static final int EMPTY = 0, RED = 1, BLACK = 2;
    public static final int DRAW = 3, RED_WINS = 4, BLACK_WINS = 5, PLAYING = 6;
    public static final int WIDTH = 7, HEIGHT = 6, MAX_MOVES = WIDTH * HEIGHT;

    public ConnectFourGame(int numPlayers) {
        // grid is initialized to 0 or EMPTY
        board = new int[HEIGHT][WIDTH];
        for (int r = 0; r < HEIGHT; r++)
            for (int c = 0; c < WIDTH; c++)
                board[r][c] = EMPTY;
    }

    // copy board
    public int[][] copyBoard() {
        int[][] boardCopy = new int[HEIGHT][WIDTH]; // 7x6 board
        for (int r = 0; r < HEIGHT; r++)
            for (int c = 0; c < WIDTH; c++)
                boardCopy[r][c] = this.board[r][c];
        return boardCopy;
    }

    // dropPiece & undo are both static b/c
    // these methods will be used by the minimax algo.
    public static boolean dropPiece(int[][] board, int c, int piece) {
        for (int r = (ConnectFourGame.HEIGHT - 1); r >= 0; r--) {
            if (board[r][c] == EMPTY) {
                board[r][c] = piece;
                return true;
            }
        }
        return false;
    }

    public static void undoMove(int[][] sampleBoard, int c) {
        for (int r = (ConnectFourGame.HEIGHT - 1); r >= 0; r--) {
            if (sampleBoard[r][c] != EMPTY) {
                sampleBoard[r][c] = EMPTY;
                return;
            }
        }
    }

    // RESTART - clears the board
    public void restart() {
        // clear the board
        for (int r = 0; r < HEIGHT; r++)
            for (int c = 0; c < WIDTH; c++)
                board[r][c] = EMPTY;
    }

    // like toString
    public String draw() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < HEIGHT; r++) {
            sb.append("|");
            for (int c = 0; c < WIDTH; c++) {
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

    // don't understand this code? I guess that makes 2 of us
    // I didn't comment a couple of years back... whoops
    // HEIGHT = 6, WIDTH = 7
    public int status(int b[][]) {
        // for (int r = 0; r < HEIGHT; r++)
        // 	for (int c = 0; c < WIDTH; c++)
        // 		board[r][c] = EMPTY;
        for (int r = 0; r < HEIGHT; r++) {
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
            for (int c = 0; c < WIDTH; c++) {
                if (b[r][c] == RED && b[r + 1][c] == RED &&
                    b[r + 2][c] == RED && b[r + 3][c] == RED)
                    return RED_WINS;
                else if (b[r][c] == BLACK && b[r + 1][c] == BLACK &&
                    b[r + 2][c] == BLACK && b[r + 3][c] == BLACK)
                    return BLACK_WINS;
            }
        }
        for (int r = 0; r <= 2; r++) {
            for (int c = 3; c < WIDTH; c++) {
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

    // public static void main(String[] args) {
    //     System.out.println("Connect Four Game by Samarth Dave");

    //     // no need to calculate
    //     // int moves = ConnectFourGame.HEIGHT * ConnectFourGame.WIDTH;

    //     int numPlayers = 1;
    //     new ConnectFourFrame(numPlayers);

    //     // we create the board instance
    //     ConnectFourGame board = new ConnectFourGame(1);

    //     // we explain users how to enter their choices
    //     System.out.println("Use 0-" + (ConnectFourGame.WIDTH - 1) + " to choose a column");
    //     // we display initial board
    //     System.out.println(board);

    //     // a simpler solution that iterates turns
    //     boolean isRedTurn = true;
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