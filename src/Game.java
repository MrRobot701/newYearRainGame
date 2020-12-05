public class Game {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public Game() {

    }

    public void start() {
        MainFrame mainFrame = new MainFrame(1200, 500);
    }
}
