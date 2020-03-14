import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

class ConnectFourPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L; // annoying warnings
	// for the panel to output the string
	private String gameState = "";

	// dimensions
	private static int SQUARE_LENGTH = ConnectFourFrame.SQUARE_LENGTH;
	private static int PANEL_WIDTH = SQUARE_LENGTH * ConnectFourGame.COLUMNS;
	private static int PANEL_HEIGHT = SQUARE_LENGTH * ConnectFourGame.ROWS;
	// save the colors for semantics
	Color backgroundColor = new Color(0, 153, 255);
	Color textColor       = Color.BLACK;
	Color emptyCircle     = Color.GRAY;
	// notice I'm saving the player colors. It seems useless
	// until you consider letting "yellow" start first (usually red is the first player).
	// Just change the graphics color output and technically the first player is yellow
	// even though the game logic is storing 'R' in that spot
	Color redColor        = new Color(255, 50, 50);
	Color yellowColor     = new Color(255, 255, 50);

	// the game class is held by the panel (not Frame like previously)
	private ConnectFourGame game;

	public ConnectFourPanel(int players) {
		this.game = new ConnectFourGame(players);
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(backgroundColor);
		g.setColor(emptyCircle);

		// draw all empty circles
		for (int i = 0; i < PANEL_WIDTH; i += SQUARE_LENGTH) {
			for (int q = 0; q < PANEL_HEIGHT; q += SQUARE_LENGTH) {
				g.fillOval(i + 6, q + 6, 60, 60);
			}
		}

		// paint the circles
		for (int r = ConnectFourGame.ROWS - 1; r >= 0; r--) {
			for (int c = ConnectFourGame.COLUMNS - 1; c >= 0; c--) {
				switch (game.get(r, c)) {
					case ConnectFourGame.YELLOW:
						g.setColor(yellowColor);
						g.fillOval((c * SQUARE_LENGTH)+6, (r*SQUARE_LENGTH)+6, 60, 60);
						break;
					case ConnectFourGame.RED:
						g.setColor(redColor);
						g.fillOval((c*SQUARE_LENGTH)+6, (r*SQUARE_LENGTH)+6, 60, 60);
						break;
				}
			}
		}

		// text
		g.setColor(textColor);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("CFG by Samarth Dave", 5, 20);
		// draw remaining spots
		String spotsLeft = (ConnectFourGame.MAX_MOVES - game.getDropCount()) + " spots left";
		g.drawString(spotsLeft, 300, 20);
		// draw game status if not ""
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.drawString(gameState, 30, 70);
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
			// calling computer drops the piece & switches the turn
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