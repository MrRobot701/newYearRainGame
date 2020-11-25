import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(int width, int height) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);

        MainPanel mainPanel = new MainPanel(100, 1, width/2, width, height);
        getContentPane().add(mainPanel);

        setVisible(true);
    }
}
