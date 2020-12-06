import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public Game() {

    }

    public void start() {
        String s = JOptionPane.showInputDialog("Enter a number from 1 through 7:");
        int lvl = s.charAt(0) - '0';

        if ((lvl >= 1) && (lvl <= 7)) {
            MainFrame mainFrame = new MainFrame(1200, 500, lvl);
        }
    }
}
