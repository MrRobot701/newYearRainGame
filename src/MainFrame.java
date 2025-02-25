

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(int width, int height, int lvl) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);

        MainPanel mainPanel = new MainPanel(100, 10, width/2, width, height, lvl);
        getContentPane().add(mainPanel);

        setVisible(true);
    }
}
