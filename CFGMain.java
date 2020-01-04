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

public class CFGMain {
	public static void main(String[] args) {
		System.out.println("Connect Four Game by Samarth Dave");
		
		Scanner s = new Scanner(System.in);
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