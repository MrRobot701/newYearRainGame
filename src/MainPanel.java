import utils.CyclicCoordinate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;

public class MainPanel extends JPanel {
    private Image background;
    private Image hat;
    private int hatSize;
    private CyclicCoordinate xHat;
    private int hatSpeed;
    private static final int FRAME_RATE = 60;

    public MainPanel(int hatSize, int hatSpeed, int defaultXLocation, int screenWidth, int screenHeight) {
        this.hatSize = hatSize;
        this.hatSpeed = hatSpeed;
        this.xHat = new CyclicCoordinate(100, 700, 150);

        loadResources();
        setFrameRefreshment(FRAME_RATE);

        addKeyListener(new MyKeyListener());
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        int width = getWidth();
        int height = getHeight();
        graphics.drawImage(background, 0, 0, width, height, null);

        //заново задаем границы для циклической координыт x, поскольку ширина JFrame могла поменятся
        //xHat.setStartAndEnd(-width, width);

        drawHatIntelligently(graphics);
    }

    private void drawHatIntelligently(Graphics graphics) {
        int xLeftHat = xHat.getCoordinate();
        //int xRightHat = xHat.cycle(xHat.getCoordinate() + getWidth());
        graphics.drawImage(hat, xLeftHat, getHeight() - hatSize, hatSize, hatSize,null);
        //graphics.drawImage(hat, xRightHat,getHeight() - hatSize, hatSize, hatSize, null);
    }

    private void setFrameRefreshment(int frameRefreshment) {
        Timer animation = new Timer(1000/frameRefreshment, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        animation.start();
    }

    private void loadResources() {
        try {
            URL url = getClass().getResource("images/background.png");
            System.out.println(url.getPath());
            background = ImageIO.read(url);
            url = getClass().getResource("images/hat.png");
            hat = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_RIGHT: xHat.plus(hatSpeed); break;
                case KeyEvent.VK_LEFT: xHat.minus(hatSpeed); break;
                case KeyEvent.VK_ESCAPE: System.exit(0); break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
