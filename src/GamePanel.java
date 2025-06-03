import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int basicTileSize = 16;
    final int scale = 4;
    final int tileSize = basicTileSize * scale;
    final int maxTileCol = 16;
    final int maxTileRow = 12;
    final int screenWidth = maxTileCol * tileSize;
    final int screenHeight = maxTileRow * tileSize;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while(gameThread != null) {
            System.out.println("running game");
        }
    }
}
