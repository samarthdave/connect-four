import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// we are going to create a simple 2-players Connect Four implementation in Java 8
public class ConnectFourGame {

  // we define players (R - Red, Y - Yellow)
  public static final char[] PLAYERS = {'R', 'Y'};
  public static final char EMPTY = '.', RED = 'R', YELLOW = 'Y'; // just in case
   // dimensions (default)
  public static final int WIDTH = 7, HEIGHT = 6, MAX_MOVES = WIDTH * HEIGHT;
  
  // grid for the board
  private final char[][] grid;
  private int lastCol = -1, lastTop = -1; // store last move made by a player

  public ConnectFourGame(int numPlayers) {
    grid = new char[HEIGHT][];

    // init the grid will blank cell
    for (int i = 0; i < HEIGHT; i++) {
      Arrays.fill(grid[i] = new char[WIDTH], '.');
    }
  }

  // we use Streams to make a more concise method 
  // for representing the board
  public String toString() {
    return IntStream.range(0,  WIDTH).
           mapToObj(Integer::toString).
           collect(Collectors.joining()) + 
           "\n" +
           Arrays.stream(grid).
           map(String::new).
           collect(Collectors.joining("\n"));
  }

  // get string representation of the row containing 
  // the last play of the user
  public String horizontal() {
    return new String(grid[lastTop]);
  }

  // get string representation fo the col containing 
  // the last play of the user
  public String vertical() {
    StringBuilder sb = new StringBuilder(HEIGHT);

    for (int h = 0; h < HEIGHT; h++) {
      sb.append(grid[h][lastCol]);
    }

    return sb.toString();
  }

  // get string representation of the "/" diagonal 
  // containing the last play of the user
  public String slashDiagonal() {
    StringBuilder sb = new StringBuilder(HEIGHT);

    for (int h = 0; h < HEIGHT; h++) {
      int w = lastCol + lastTop - h;

      if (0 <= w && w < WIDTH) {
        sb.append(grid[h][w]);
      }
    }

    return sb.toString();
  }

  // get string representation of the "\" 
  // diagonal containing the last play of the user
  public String backslashDiagonal() {
    StringBuilder sb = new StringBuilder(HEIGHT);

    for (int h = 0; h < HEIGHT; h++) {
      int w = lastCol - lastTop + h;

      if (0 <= w && w < WIDTH) {
        sb.append(grid[h][w]);
      }
    }

    return sb.toString();
  }

  // static method checking if a substring is in str
  public static boolean contains(String str, String substring) {
    return str.indexOf(substring) >= 0;
  }

  // now, we create a method checking if last play is a winning play
  public boolean isWinningPlay() {
    if (lastCol == -1) {
      System.err.println("No move has been made yet");
      return false;
    }

    char sym = grid[lastTop][lastCol];
    // winning streak with the last play symbol
    String streak = String.format("%c%c%c%c", sym, sym, sym, sym);

    // check if streak is in row, col, 
    // diagonal or backslash diagonal
    return contains(horizontal(), streak) || 
           contains(vertical(), streak) || 
           contains(slashDiagonal(), streak) || 
           contains(backslashDiagonal(), streak);
  }

  // prompts the user for a column, repeating until a valid choice is made
  public void chooseAndDrop(char symbol, Scanner input) {
    do {
      System.out.println("\nPlayer " + symbol + " turn: ");
      int col = input.nextInt();

      // check if column is ok
      if (!(0 <= col && col < WIDTH)) {
        System.out.println("Column must be between 0 and " + (WIDTH - 1));
        continue;
      }

      // now we can place the symbol to the first 
      // available row in the asked column
      for (int h = HEIGHT - 1; h >= 0; h--) {
        if (grid[h][col] == '.') {
          grid[lastTop = h][lastCol = col] = symbol;
          return;
        }
      }

      // if column is full ==> we need to ask for a new input
      System.out.println("Column " + col + " is full.");
    } while (true);
  }

  public static void main(String[] args) {
    System.out.println("Connect Four Game by Samarth Dave");
		
    // no need to calculate
    // int moves = ConnectFourGame.HEIGHT * ConnectFourGame.WIDTH;
		
    int numPlayers = 1;
		new ConnectFourFrame(numPlayers);

    // we create the board instance
    ConnectFourGame board = new ConnectFourGame(1);

    // we explain users how to enter their choices
    System.out.println("Use 0-" + (ConnectFourGame.WIDTH - 1) + " to choose a column");
    // we display initial board
    System.out.println(board);

    // a simpler solution that iterates turns
    boolean isRedTurn = true;
    boolean isWinner = false;

    while (true) {
      int loc = (isRedTurn ? 0 : 1);
      char symbol = PLAYERS[loc];

      // no scanner yet...
      // board.chooseAndDrop(symbol, input);

      // update the java swing board (using repaint)
      // repaint()
      // System.out.println(board); // and reprint the board for debugging

      // board already checks for wins (panel will call this function)
      if (board.isWinningPlay()) {
        System.out.println("\nPlayer " + symbol + " wins!");
        isWinner = true;
        break;
      }

      // alternate turn
      isRedTurn = !isRedTurn;
    }

    if (!isWinner) {
      System.out.println("Game over. No winner. Try again!");
    }

  }

}
