

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
    private int xHat;
    private int hatSpeed;
    private static final int FRAME_RATE = 60;
    private int key;
    private int lvl;
    private int screenWidth;
    private int screenHeight;

    private Present[] presents;

    public MainPanel(int hatSize, int hatSpeed, int defaultXLocation, int screenWidth, int screenHeight, int lvl) {
        this.hatSize = hatSize;
        this.hatSpeed = hatSpeed;
        this.xHat = defaultXLocation; //начальное положение шапки
        this.lvl = lvl;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        initiatePresents();

        loadResources(); //загружаем картинки: бэкраунд и шапку
        setFrameRefreshment(FRAME_RATE); //создаем таймер, задаем ему частоту вызова метода actionPerformed() и запускаем его

        addKeyListener(new MyKeyListener()); //добавляем MyKeyListener в качестве "слушателя" событий клавиатуры происходящих с панелью com.MainPanel
        setFocusable(true); //фокусируем панель, то есть делаем так, чтобы все события с клавы и мыши случались с панелью, у которой есть слушаетль
    }

    private void initiatePresents() {
        presents = new Present[lvl];
        for (int i = 0; i < lvl; i++) {
            try {
                URL url = getClass().getResource("images/p"+i+".png");
                System.out.println(url.getPath());
                Image image = ImageIO.read(url);
                presents[i] = new Present(image, screenWidth, screenHeight - hatSize, 10, 50);
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        int width = getWidth();
        int height = getHeight();

        graphics.drawImage(background, 0, 0, width, height, null);

        drawHatIntelligently(graphics);

        drawPresents(graphics);

    }

    private void drawPresents(Graphics graphics) {
        for (int i = 0; i < lvl; i++) {
            presents[i].draw(graphics);
        }
    }

    private void drawHatIntelligently(Graphics graphics) {
        int width = getWidth();
        int height = getHeight();

        // x будет принимать значения в промежутке [0, width) и будет равен остатку деления xHat на width
        int x = (xHat%width + width)%width;

        int y = height - hatSize; // y всегда одно и тоже

        //рисуем шапку
        graphics.drawImage(hat, x, y, hatSize, hatSize, null);
        if (x + hatSize > width) {
            //если нарисованная шапка захоидт за границы экрана слева, то рисуем еще одну справа
            graphics.drawImage(hat, x - width, y, hatSize, hatSize, null);
        }
    }

    private void setFrameRefreshment(int frameRefreshment) {
        Timer animation = new Timer(1000/frameRefreshment, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePresents();

                switch (key) {
                    case KeyEvent.VK_RIGHT: xHat += hatSpeed; break;
                    case KeyEvent.VK_LEFT: xHat -= hatSpeed; break;
                    case KeyEvent.VK_ESCAPE: System.exit(0); break; //звершаем программу при нажатии Esc
                }

                repaint();
            }
        });
        animation.start();
    }

    private void updatePresents() {
        int count = 0;
        for (int i = 0; i < lvl; i++) {
            if (!presents[i].active) {
                if(count < lvl) {
                    presents[i].start();
                    break;
                }
            }
            count++;
        }
    }

    private void loadResources() {
        try {
            URL url = getClass().getResource("images/background.png");
            //System.out.println(url.getPath());
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
            key = e.getKeyCode();
            //погулите про switch конструкцию в Java. Это тоже самое что и if else.

        }

        @Override
        public void keyReleased(KeyEvent e) {
            key = -1;
        }
    }
}
