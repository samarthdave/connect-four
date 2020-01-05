import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;

class ConnectFourFrame extends JFrame {
	private ConnectFourGame cfg;
	static int SQUARE_LENGTH = 72;

	public ConnectFourFrame(int players) {
		super("Connect Four");

		cfg = new ConnectFourGame(players);

		// frame settings
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new ConnectFourPanel(players, cfg));
		pack();
		Insets s = getInsets();

		int w = (SQUARE_LENGTH * ConnectFourGame.COLUMNS) + s.left + s.right;
		int h = (SQUARE_LENGTH * ConnectFourGame.ROWS) + s.top + s.bottom;
		setPreferredSize(new Dimension(w,h));
		pack();
		setLocationRelativeTo(null); // center window

	}

}