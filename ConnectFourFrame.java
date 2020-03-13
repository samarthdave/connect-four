import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;

class ConnectFourFrame extends JFrame {
	private static final long serialVersionUID = 1L; // to stop the warning

	static int SQUARE_LENGTH = 72;
	
	public ConnectFourFrame(int players) {
		super("Connect Four");

		// frame settings
		setVisible(true);
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new ConnectFourPanel(players));
		pack();

		final Insets s = getInsets();
		final int w = (SQUARE_LENGTH * ConnectFourGame.COLUMNS) + s.left + s.right;
		final int h = (SQUARE_LENGTH * ConnectFourGame.ROWS) + s.top + s.bottom;
		setPreferredSize(new Dimension(w,h));
		pack();
		setLocationRelativeTo(null); // center window

	}

}