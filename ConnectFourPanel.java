import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

class ConnectFourPanel extends JPanel implements MouseListener {
	static final int red = 1, black = 2, emp = 0;
	static final int d = 3, rw = 4, bw = 5, p = 6;
	static int current = red;
	public String gameState = "";

	// dimensions
	private static int SQUARE_LENGTH = ConnectFourFrame.SQUARE_LENGTH;
	private static int PANEL_WIDTH = SQUARE_LENGTH * ConnectFourGame.COLUMNS;
	private static int PANEL_HEIGHT = SQUARE_LENGTH * ConnectFourGame.ROWS;

	private ConnectFourGame game;

	public ConnectFourPanel(int players, ConnectFourGame cfg) {
		this.game = cfg;
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(new Color(180,180,0));
		g.setColor(Color.GRAY);

		for (int i = 0; i < PANEL_WIDTH; i += SQUARE_LENGTH) {
			for (int q = 0; q < PANEL_HEIGHT; q += SQUARE_LENGTH) {
				g.fillOval(i + 6, q + 6, 60, 60);
			}
		}

		for (int r = 5; r >= 0; r--) {
			for (int c = 6; c >= 0; c--) {
				if (game.board[r][c] == black) {
					g.setColor(Color.BLACK);
					g.fillOval((c * SQUARE_LENGTH)+6, (r*SQUARE_LENGTH)+6, 60, 60);
				} else if (game.board[r][c] == red) {
					g.setColor(Color.RED);
					g.fillOval((c*SQUARE_LENGTH)+6, (r*SQUARE_LENGTH)+6, 60, 60);
				}
			}
		}

		// text
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial",Font.PLAIN,10));
		g.drawString("CFG by Samarth Dave",5,15);
		g.setFont(new Font("Arial",Font.PLAIN,30));
		g.drawString(gameState, 30, 50);
	}

	// mouse events
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();

		// if right click (restart)
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (game.restart()) {
				gameState = "";
			}
		}

		// if not a left click
		if (e.getButton() != MouseEvent.BUTTON1) {
			return;
		}

		// remaining code for left click...

		if (game.isSingle) {
			if (game.status() != ConnectFourGame.PLAYING)
				return;
			
			// generate intervals...
			int[] columnIntervals = new int[ConnectFourGame.COLUMNS + 2];

			for (int i = 0; i < ConnectFourGame.COLUMNS + 2; i++) {
				columnIntervals[i] = SQUARE_LENGTH * i;
				// check if x value is below
				if (x <= columnIntervals[i]) {
					if (!game.dropPiece(i - 1, red)) {
						return;
					}
				}
			}

			// run the computer
			game.computer();

		} else {
			// if human vs. human
			if (game.status() != ConnectFourGame.PLAYING)
				return;

			if (game.isRedTurn)
				current = red;
			else
				current = black;
			
			// generate intervals...
			int[] columnIntervals = new int[ConnectFourGame.COLUMNS + 2];

			for (int i = 0; i < ConnectFourGame.COLUMNS + 2; i++) {
				columnIntervals[i] = SQUARE_LENGTH * i;
				// check if x value is below
				if (x <= columnIntervals[i]) {
					if (!game.dropPiece(i - 1, red)) {
						return; // can't drop here
					}
				}
			}

			game.alternateTurn();
			
		} // end else block

		if (game.status() != p && gameState.equals("")) {
			switch (game.status()) {
				case bw:
					gameState = "Black wins! Right click to restart";
					break;
				case rw:
					gameState = "Red wins! Right click to restart";
					break;
				case d:
					gameState ="Draw! Right click to restart";
					break;
			}
		}
	
		repaint();
	} // end mouse click

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