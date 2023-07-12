import java.util.Scanner;

public class CFGMain {
    public static void main(String[] args) {
        System.out.println("Connect Four Game by Samarth Dave");
        
        // get user input & send to constructor
        Scanner s = new Scanner(System.in);
        int players = validInt(s,"How many players (1-2)? ",1,2);
        s.close();

        new ConnectFourFrame(players);
    }

    public static int validInt(Scanner s, String t, int min, int max) {
        int sel = -1;
        String e = "Error, try again: ";
        do {
            System.out.print(t);
            while(!s.hasNextInt()) {
                System.out.print(e);
                s.next();
            }
            sel = s.nextInt();
        } while(sel < min || sel > max);
        s.nextLine();
        return sel;
    }
}