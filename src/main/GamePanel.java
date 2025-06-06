package main;

import Entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

/**
 * The main panel that controls the image currently being viewed on the application
 */
public class GamePanel extends JPanel implements Runnable {
    final int basicTileSize = 16; // 16x16 object
    final int scale = 4;
    public final int tileSize = basicTileSize * scale;
    public final int maxTileCol = 16;
    public final int maxTileRow = 12;
    public final int screenWidth = maxTileCol * tileSize;
    public final int screenHeight = maxTileRow * tileSize;

    // World Settings
    public final int maxWorldCol = 30;
    public final int maxWorldRow = 32;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    final int fps = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);

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
     * Places the object on the world map
     */
    public void setupGame() {
        // assetSetter.setObject();
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
     * @param g drawing tool to draw different images
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);
        g2.dispose();
    }
}
