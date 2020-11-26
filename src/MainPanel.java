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

    public MainPanel(int hatSize, int hatSpeed, int defaultXLocation, int screenWidth, int screenHeight) {
        this.hatSize = hatSize;
        this.hatSpeed = hatSpeed;
        this.xHat = defaultXLocation; //начальное положение шапки

        loadResources(); //загружаем картинки: бэкраунд и шапку
        setFrameRefreshment(FRAME_RATE); //создаем таймер, задаем ему частоту вызова метода actionPerformed() и запускаем его

        addKeyListener(new MyKeyListener()); //добавляем MyKeyListener в качестве "слушателя" событий клавиатуры происходящих с панелью MainPanel
        setFocusable(true); //фокусируем панель, то есть делаем так, чтобы все события с клавы и мыши случались с панелью, у которой есть слушаетль
    }

    @Override
    public void paintComponent(Graphics graphics) {
        int width = getWidth();
        int height = getHeight();
        graphics.drawImage(background, 0, 0, width, height, null);

        drawHatIntelligently(graphics);
    }

    private void drawHatIntelligently(Graphics graphics) {
        int width = getWidth();
        int height = getHeight();

        /*
        * Используем оператор % (остаток от деления) для "зацикливания" координаты х
        * В Java % - это оператор принимающий два числа справа и слева и возвращающий
        * остаток деления правого на левое
        * Например:
        *      10%3 = 1, 29%6 = 5, 100%2 = 0 и т.д.
        * Полученые остатки всегда больше 0 и меньше делителя, то есть если
        *           a%b = c, то 0 <= c < b для любых чисел a и b
        * Но в Java % несовсем коректно работает с отрицательными числами.
        * По сему вот математически правильное взятие остатка от деления.
        * Но сначала определение.
        * Определение:
        *      остатком от деление числа a на число b называется
        *             такое число r от 0 до |а|, что
        *                    a = n*b + r,
        *      где n - целое число.
        * То есть 0 <= r < |a|.
        * Примеры:
        *     1) -5 = (-1)*9 + 4 -> остаток = 4
        *     2) -191 = (-64)*3 + 1 -> остаток = 1
        *     3) 14 = 3*4 + 2 -> остаток = 2
        *     4) 100 = 20*5 + 0 -> остаток = 0
        * В java оператор % - возвращает отрицательное значение, если число, которое делят,
        * меньше 0 (например -5%9 = -5, а не 4 как должно быть или -191%3 = -2, а должно быть 1),
        * а это не корректно, поскольку в математике остаток всегда > или = 0, не зависимо от знака делимого и делителя!
        * Сообственно поэтому Я написал не просто xHat%width, а (xHat%width + width)%width
        * для x, чтобы для отрицательных чисел результат был коректный (положительный).
        * */

        // x будет принимать значения в промежутке [0, width) и будет равен остатку деления xHat на width
        int x = (xHat%width + width)%width;

        int y = height - hatSize; // y всегда одно и тоже

        //рисуем шапку
        graphics.drawImage(hat, x, y, hatSize, hatSize, null);
        if (x + hatSize > width) {
            //если нарисованная шапка захоидт за границы экрана слева, то рисуем еще одну справа
            graphics.drawImage(hat, x - width, y, hatSize, hatSize, null);
        }

        /*
        * Тут у вас ребятки скорее всего возникает вопрсо, а как тогда это работает для случая, когда шапка
        * движется влево, а не врпаво?! Как появляется 2я шапка справа, когда "настоящая" шапка заходит
        * слева за экран?! Ведь тут только логика для случая захода "настоящей" шапки справа за экран!!!
        * Хе-хе-хе
        * Кто поймет, тому + 1 балл ;)
        * Подсказка:
        *      Посмотрите на коррдинату х спомощью команды System.out.println() и что с ней происходит.
        *      Какая шапка нарисована на экране в этот момент)?
        * */
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
            int key = e.getKeyCode();
            //погулите про switch конструкцию в Java. Это тоже самое что и if else.
            switch (key) {
                case KeyEvent.VK_RIGHT: xHat += hatSpeed; break;
                case KeyEvent.VK_LEFT: xHat -= hatSpeed; break;
                case KeyEvent.VK_ESCAPE: System.exit(0); break; //звершаем программу при нажатии Esc
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
