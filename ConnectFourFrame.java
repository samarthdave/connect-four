import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;

class ConnectFourFrame extends JFrame {
	private static final long serialVersionUID = -5612056140561429545L;
	public static int[][] board;
	public static final int EMPTY = 0, RED = 1, BLACK = 2;
	public static final int DRAW = 3, RED_WINS = 4, BLACK_WINS = 5, PLAYING = 6;
	
	public ConnectFourFrame(int players) {
		super("Connect Four Game by Samarth Dave");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ConnectFourPanel j = new ConnectFourPanel(players);
		add(j);
		pack();
		Insets s = getInsets();
		int w = 504 + s.left + s.right;
		int h = 432 + s.top + s.bottom;
		setPreferredSize(new Dimension(w,h));
		pack();
		setLocationRelativeTo(null); // center window
		
		// game stuff
		board = new int[6][7];
		for(int r=0; r<6;r++)
			for(int c=0; c<7; c++)
				board[r][c] = EMPTY;
	}
	// copy board
	public static int[][] copyBoard() {
		int[][] boardCopy = new int[6][7];
		for(int r=0; r<6;r++)
			for(int c=0; c<7; c++)
				boardCopy[r][c] = ConnectFourFrame.board[r][c];
		return boardCopy;
	}
	
	public static boolean dropPiece(int[][] sampleBoard, int c, int piece) {
		for(int r=5; r>=0; r--)	{
			if(sampleBoard[r][c] == EMPTY) {
				sampleBoard[r][c] = piece;
				return true;
			}
		}
		return false;
	}
	
	public static void undoMove(int[][] sampleBoard, int c) {
		for(int r = 5; r >= 0; r--)	{
			if(sampleBoard[r][c] != EMPTY) {
				sampleBoard[r][c] = EMPTY;
				return;
			}
		}
	}
	
	public static void clear() {
		// clear the board
		for(int r=0; r<6;r++)
			for(int c=0; c<7; c++)
				board[r][c] = EMPTY;
	}
	
	public static void draw() {
		for(int r=0; r<6;r++) {
			System.out.print("|");
			for(int c=0; c<7; c++) {
				System.out.print(" ");
				if(board[r][c] == EMPTY)
					System.out.print(" ");
				else if(board[r][c] == RED)
					System.out.print("R");
				else
					System.out.print("B");
				System.out.print(" |");
			}
			System.out.print("\n");
		}
		System.out.print("-----------------------------\n");
	}
	
	public static int RED_WIN_SCORE = 10;
	public static int BLACK_WIN_SCORE = -10;
	public static int TIE_SCORE = 0;
	// the heart of it all
	public static int minimax(int[][] b, int depth, boolean isMaximizing) {
		int gameStatus = status(b);
		if (gameStatus == RED_WINS) { return RED_WIN_SCORE; }
		if (gameStatus == BLACK_WINS) { return BLACK_WIN_SCORE; }
		
		if (depth >= 5) {
			return (isMaximizing ? 500 : -500);
		}
		
		if (!isMaximizing) {
			int bestScore = -500;
			for (int c = 0; c < 7; c++) {
				if (dropPiece(b, c, BLACK)) {
					int score = minimax(b, depth + 1, false);
					undoMove(b, c);
					bestScore = Math.max(score, bestScore);
				}
			}
			return bestScore;
		} else {
			int bestScore = 500;
			for (int c = 0; c < 7; c++) {
				if (dropPiece(b, c, RED)) {
					int score = minimax(b, depth + 1, true);
					undoMove(b, c);
					bestScore = Math.min(score, bestScore);
				}
			}
			return bestScore;
		}
		
//		return 1;
		
	}
	
	// "smart" computer
	public static int computer() {
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
		
		int rand = 0;
		do {
			if(status() == DRAW)
				break;
			rand = (int)(Math.floor(Math.random()*7));
		} while(!dropPiece(ConnectFourFrame.board, rand, BLACK));

		return 1;
	}
	
	public static int status() {
		return status(ConnectFourFrame.board);
	}
	
	public static int status(int b[][])	{
		for(int r=0; r<6;r++)
			for(int c=0; c<=3; c++) {
				if(b[r][c] == RED &&b[r][c+1] == RED
					&&b[r][c+2] == RED &&b[r][c+3] == RED)
					return RED_WINS;
				else if(b[r][c] == BLACK &&b[r][c+1] == BLACK
					&&b[r][c+2] == BLACK &&b[r][c+3] == BLACK)
					return BLACK_WINS;
			}
		// vertical
		for(int r=0; r<=2;r++) {
			for(int c=0; c<7; c++) {
				if(b[r][c] == RED &&b[r+1][c] == RED
					&&b[r+2][c] == RED &&b[r+3][c] == RED)
					return RED_WINS;
				else if(b[r][c] == BLACK &&b[r+1][c] == BLACK
					&&b[r+2][c] == BLACK &&b[r+3][c] == BLACK)
					return BLACK_WINS;
			}
		}
		for(int r=0; r<=2;r++) {
			for(int c=3; c<7; c++) {
				if(b[r][c] == RED &&b[r+1][c-1] == RED
					&&b[r+2][c-2] == RED &&b[r+3][c-3] == RED)
					return RED_WINS;
				else if(b[r][c] == BLACK &&b[r+1][c-1] == BLACK
					&&b[r+2][c-2] == BLACK &&b[r+3][c-3] == BLACK)
					return BLACK_WINS;
			}
		}
		for(int r=0; r<=2;r++) {
			for(int c=0; c<=3; c++) {
				if(b[r][c] == RED &&b[r+1][c+1] == RED
					&&b[r+2][c+2] == RED &&b[r+3][c+3] == RED)
					return RED_WINS;
				else if(b[r][c] == BLACK &&b[r+1][c+1] == BLACK
					&&b[r+2][c+2] == BLACK &&b[r+3][c+3] == BLACK)
					return BLACK_WINS;
			}
		}
		for(int r=0; r<6;r++) {
			for(int c=0; c<7; c++) {
				if(b[r][c] == EMPTY)
					return PLAYING;
			}
		}
		return DRAW;
	}
	
	
}