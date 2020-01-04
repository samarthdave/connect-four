import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;

class ConnectFourFrame extends JFrame {
	private static final long serialVersionUID = -5612056140561429545L;
	
	public ConnectFourFrame(int players) {
		super("Connect Four Game by Samarth Dave");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ConnectFourPanel j = new ConnectFourPanel(players);
		add(j);
		pack();
		Insets s = getInsets();

		int SQUARE_LENGTH = 72;
		int w = (SQUARE_LENGTH * ConnectFourGame.WIDTH) + s.left + s.right;
		int h = (SQUARE_LENGTH * ConnectFourGame.HEIGHT) + s.top + s.bottom;
		setPreferredSize(new Dimension(w,h));
		pack();
		setLocationRelativeTo(null); // center window
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
			if (status() == DRAW)
				break;
			rand = (int)(Math.floor(Math.random()*ConnectFourGame.WIDTH));
		} while (!dropPiece(rand,BLACK));

		if (status() == DRAW)
			return -1;
		else
			return rand;
	}
	
}