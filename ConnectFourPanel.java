import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

class ConnectFourPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L; // annoying warnings

	private String gameState = "";

	// dimensions
	private static int SQUARE_LENGTH = ConnectFourFrame.SQUARE_LENGTH;
	private static int PANEL_WIDTH = SQUARE_LENGTH * ConnectFourGame.COLUMNS;
	private static int PANEL_HEIGHT = SQUARE_LENGTH * ConnectFourGame.ROWS;

	private ConnectFourGame game;

	public ConnectFourPanel(int players) {
		this.game = new ConnectFourGame(players);
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

		for (int r = ConnectFourGame.ROWS - 1; r >= 0; r--) {
			for (int c = ConnectFourGame.COLUMNS - 1; c >= 0; c--) {
				if (game.get(r, c) == ConnectFourGame.YELLOW) {
					g.setColor(Color.YELLOW);
					g.fillOval((c * SQUARE_LENGTH)+6, (r*SQUARE_LENGTH)+6, 60, 60);
				} else if (game.get(r, c) == ConnectFourGame.RED) {
					g.setColor(Color.RED);
					g.fillOval((c*SQUARE_LENGTH)+6, (r*SQUARE_LENGTH)+6, 60, 60);
				}
			}
		}

		// text
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.drawString("CFG by Samarth Dave", 5, 15);
		g.setFont(new Font("Arial", Font.PLAIN, 25));
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
				repaint();
				return;
			}
		}

		// if any other mouse button then ignore
		if (e.getButton() != MouseEvent.BUTTON1) {
			return;
		}

		// -----------------------------------
		// left click code
		// -----------------------------------

		if (game.status() != ConnectFourGame.PLAYING)
			return;

		// generate intervals...
		int[] columnIntervals = new int[ConnectFourGame.COLUMNS + 2];

		for (int i = 0; i < ConnectFourGame.COLUMNS + 2; i++) {
			// [0, 72, 216, ... 504]
			columnIntervals[i] = SQUARE_LENGTH * i;
			// check if x value is below
			if (x <= columnIntervals[i]) {
				if (!game.dropPiece(i - 1)) {
					return;
				}
				break;
			}
		}

		// alternate player or run computer
		if (game.isSinglePlayer) {
			ConnectFourGame.computer(game);
		}

		// update status string & repaint
		if (game.status() != ConnectFourGame.PLAYING && gameState.equals("")) {
			switch (game.status()) {
				case ConnectFourGame.YELLOW_WINS:
					gameState = "Yellow wins! Right click to restart";
					break;
				case ConnectFourGame.RED_WINS:
					gameState = "Red wins! Right click to restart";
					break;
				case ConnectFourGame.DRAW:
					gameState ="Draw! Right click to restart";
					break;
			}
		}

		repaint();
	} // end mouse click

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}