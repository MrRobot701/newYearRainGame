import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Present {
    private Image image;
    public int x, y;
    public boolean active;
    private Timer presentAnimation;
    private int widthScreen;
    private int heightScreen;
    private int presentSpeed;
    private int size;

    public Present(Image image, int width, int height, int presentSpeed, int size) {
        presentAnimation = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown();
            }
        });

        this.image = image;
        this.active = false;
        this.widthScreen = width;
        this.heightScreen = height;
        this.presentSpeed = presentSpeed;
        this.size = size;
    }

    public void start() {
        y = (int)(Math.random()*heightScreen/2);
        x = (int)(Math.random()*widthScreen); // 0 <= x < widthScreen
        active = true;
        presentAnimation.start();
    }

    private void moveDown() {
        if (active) {
            y+=presentSpeed;
        }

        if (y + size >= heightScreen) {
            presentAnimation.stop();
        }
    }

    public void draw(Graphics graphics) {
        if (active) {
            graphics.drawImage(image, x, y, size, size, null);
        }
    }
}
