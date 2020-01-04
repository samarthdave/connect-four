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
//		int amount = validInt(s,"How many players (1-2)? ",1,2);
		int amount = 1;
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