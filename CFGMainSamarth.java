import java.util.Scanner;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;

public class CFGMainSamarth {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Connect Four Game by Samarth Dave");
		int amount = validInt(s,"How many players (1-2)? ",1,2);
		s.close();
		new ConnectFourFrame(amount);
	}
	public static int validInt(Scanner s, String t, int min, int max) {
		int sel = -1;
		String e = "Error, try again: ";
		if(min == max) {
			do {
				System.out.print(t);
				while(!s.hasNextInt()) {
					System.out.print(e);
					s.next();
				}
				sel = s.nextInt();
			} while(sel != min);
		} else {
			do {
				System.out.print(t);
				while(!s.hasNextInt()) {
					System.out.print(e);
					s.next();
				}
				sel = s.nextInt();
			} while(sel < min || sel > max);
		}
		s.nextLine();
		return sel;
	}
}

class ConnectFourPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1467814600109299597L;
	static final int red = 1, black = 2, emp = 0;
	final int d = 3, rw = 4, bw = 5, p = 6;
	String t = "";
	boolean isSingle = true;
	static int current = red;
	boolean isRed = true;
	public ConnectFourPanel(int players) {
		if(players != 1)
			isSingle = false;
		setSize(504,432);
		addMouseListener(this);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(new Color(180,180,0));
		g.setColor(Color.GRAY);
		for(int i = 0; i < 504; i+=72) {
			for(int q = 0; q < 432; q+=72) {
				g.fillOval(i+6, q+6, 60, 60);
			}
		}
		for(int r = 5; r >= 0; r--) {
			for(int c = 6; c >= 0; c--) {
				if(ConnectFourFrame.board[r][c] == black) {
					g.setColor(Color.BLACK);
					g.fillOval((c*72)+6, (r*72)+6, 60, 60);
				} else if(ConnectFourFrame.board[r][c] == red) {
					g.setColor(Color.RED);
					g.fillOval((c*72)+6, (r*72)+6, 60, 60);
				}
			}
		}
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial",Font.PLAIN,10));
		g.drawString("CFG by Samarth Dave",5,15);
		g.setFont(new Font("Arial",Font.PLAIN,30));
		g.drawString(t, 30, 50);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(isSingle) {
				if(!t.equals(""))
					return;
				if(x <= 72) {
					if(!ConnectFourFrame.dropPiece(0, red))
						return;
				} else if(x > 72 && x < 145) {
					if(!ConnectFourFrame.dropPiece(1, red))
						return;
				} else if(x > 144 && x < 217) {
					if(!ConnectFourFrame.dropPiece(2, red))
						return;
				} else if(x > 216 && x < 289) {
					if(!ConnectFourFrame.dropPiece(3, red))
						return;
				} else if(x > 287 && x < 361) {
					if(!ConnectFourFrame.dropPiece(4, red))
						return;
				} else if(x > 299 && x < 433) {
					if(!ConnectFourFrame.dropPiece(5, red))
						return;
				} else {
					if(!ConnectFourFrame.dropPiece(6, red))
						return;
				}
				ConnectFourFrame.computer();
				if(ConnectFourFrame.status() != p && t.equals("")) {
					switch(ConnectFourFrame.status()) {
						case bw:
							t = "Black wins! Right click to restart";
							break;
						case rw:
							t = "Red wins! Right click to restart";
							break;
						case d:
							t ="Draw! Right click to restart";
							break;
					}
				}
			} else {
				if(!t.equals(""))
					return;
				if(isRed)
					current = red;
				else
					current = black;
				if(x <= 72) {
					if(!ConnectFourFrame.dropPiece(0, current))
						return;
				} else if(x > 72 && x < 145) {
					if(!ConnectFourFrame.dropPiece(1, current))
						return;
				} else if(x > 144 && x < 217) {
					if(!ConnectFourFrame.dropPiece(2, current))
						return;
				} else if(x > 216 && x < 289) {
					if(!ConnectFourFrame.dropPiece(3, current))
						return;
				} else if(x > 287 && x < 361) {
					if(!ConnectFourFrame.dropPiece(4, current))
						return;
				} else if(x > 299 && x < 433) {
					if(!ConnectFourFrame.dropPiece(5, current))
						return;
				} else {
					if(!ConnectFourFrame.dropPiece(6, current))
						return;
				}
				isRed = !isRed;
				if(ConnectFourFrame.status() != p && t.equals("")) {
					switch(ConnectFourFrame.status()) {
						case bw:
							t = "Black wins! Right click to restart";
							break;
						case rw:
							t = "Red wins! Right click to restart";
							break;
						case d:
							t ="Draw! Right click to restart";
							break;
					}
				}
			}
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			if(!t.equals("")) {
				isRed = true;
				ConnectFourFrame.clear();
				t = "";
			}
		}
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
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
		setLocationRelativeTo(null);
		board = new int[6][7];
		for(int r=0; r<6;r++)
			for(int c=0; c<7; c++)
				board[r][c] = EMPTY;
	}
	public static boolean dropPiece(int c, int piece) {
		for(int r=5; r>=0; r--)	{
			if(board[r][c] == EMPTY) {
				board[r][c] = piece;
				return true;
			}
		}
		return false;
	}
	public static void clear() {
		for(int r=0; r<6;r++)
			for(int c=0; c<7; c++)
				board[r][c] = EMPTY;
	}
	public static int status()	{
		for(int r=0; r<6;r++)
			for(int c=0; c<=3; c++) {
				if(board[r][c] == RED &&board[r][c+1] == RED
					&&board[r][c+2] == RED &&board[r][c+3] == RED)
					return RED_WINS;
				else if(board[r][c] == BLACK &&board[r][c+1] == BLACK
					&&board[r][c+2] == BLACK &&board[r][c+3] == BLACK)
					return BLACK_WINS;
			}
		// vertical
		for(int r=0; r<=2;r++) {
			for(int c=0; c<7; c++) {
				if(board[r][c] == RED &&board[r+1][c] == RED
					&&board[r+2][c] == RED &&board[r+3][c] == RED)
					return RED_WINS;
				else if(board[r][c] == BLACK &&board[r+1][c] == BLACK
					&&board[r+2][c] == BLACK &&board[r+3][c] == BLACK)
					return BLACK_WINS;
			}
		}
		for(int r=0; r<=2;r++) {
			for(int c=3; c<7; c++) {
				if(board[r][c] == RED &&board[r+1][c-1] == RED
					&&board[r+2][c-2] == RED &&board[r+3][c-3] == RED)
					return RED_WINS;
				else if(board[r][c] == BLACK &&board[r+1][c-1] == BLACK
					&&board[r+2][c-2] == BLACK &&board[r+3][c-3] == BLACK)
					return BLACK_WINS;
			}
		}
		for(int r=0; r<=2;r++) {
			for(int c=0; c<=3; c++) {
				if(board[r][c] == RED &&board[r+1][c+1] == RED
					&&board[r+2][c+2] == RED &&board[r+3][c+3] == RED)
					return RED_WINS;
				else if(board[r][c] == BLACK &&board[r+1][c+1] == BLACK
					&&board[r+2][c+2] == BLACK &&board[r+3][c+3] == BLACK)
					return BLACK_WINS;
			}
		}
		for(int r=0; r<6;r++) {
			for(int c=0; c<7; c++) {
				if(board[r][c] == EMPTY)
					return PLAYING;
			}
		}
		return DRAW;
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
	public static int computer() {
		int rand = 0;
		do {
			if(status() == DRAW)
				break;
			rand = (int)(Math.floor(Math.random()*7));
		} while(!dropPiece(rand,BLACK));
		
		if(status() == DRAW)
			return -1;
		else
			return rand;

	}
}