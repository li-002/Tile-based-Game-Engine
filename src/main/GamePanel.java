package main;

import Entity.Player;

import javax.swing.*;
import java.awt.*;

/**
 * The main panel that controls the image currently being viewed on the application
 */
public class GamePanel extends JPanel implements Runnable {
    final int basicTileSize = 16; // 16x16 object
    final int scale = 4;
    public final int tileSize = basicTileSize * scale;
    final int maxTileCol = 16;
    final int maxTileRow = 12;
    final int screenWidth = maxTileCol * tileSize;
    final int screenHeight = maxTileRow * tileSize;
    final int fps = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    /**
     * Sets the initial values for the panel being displayed
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    /**
     * Initializes a new Thread which constantly looks for and performs the run() method
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000.0 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Check for and apply changes to different entities
     */
    public void update() {
        player.update();
    }

    /**
     * Updates the image of the entity
     * @param g image representing the entity
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
