import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

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
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 0, red))
						return;
				} else if(x > 72 && x < 145) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 1, red))
						return;
				} else if(x > 144 && x < 217) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 2, red))
						return;
				} else if(x > 216 && x < 289) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 3, red))
						return;
				} else if(x > 287 && x < 361) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 4, red))
						return;
				} else if(x > 299 && x < 433) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 5, red))
						return;
				} else {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 6, red))
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
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 0, current))
						return;
				} else if(x > 72 && x < 145) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 1, current))
						return;
				} else if(x > 144 && x < 217) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 2, current))
						return;
				} else if(x > 216 && x < 289) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 3, current))
						return;
				} else if(x > 287 && x < 361) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 4, current))
						return;
				} else if(x > 299 && x < 433) {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 5, current))
						return;
				} else {
					if(!ConnectFourFrame.dropPiece(ConnectFourFrame.board, 6, current))
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