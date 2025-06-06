package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Class representing the player
 */
public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    /**
     * Constructor setting initial values
     * @param gp main panel that the user sees
     * @param keyH KeyHandler that checks for current keys being pressed
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle();
        solidArea.x = gp.tileSize / 4;
        solidArea.y = gp.tileSize / 4;
        solidArea.height = gp.tileSize / 2;
        solidArea.width = gp.tileSize / 2;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets default values for position, speed, and direction
     */
    public void setDefaultValues() {
        worldX = gp.tileSize * 4;
        worldY = gp.tileSize * 29;
        speed = 4;
        direction = "down";
    }

    /**
     * Updates player for different key inputs and image cycling
     */
    @SuppressWarnings("ConstantValue")
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            }
            else if (keyH.downPressed) {
                direction = "down";
            }
            else if(keyH.leftPressed) {
                direction = "left";
            }
            else if(keyH.rightPressed) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);
            if (!collisionOn) {
                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 3;
                }
                else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    /**
     * Checks which image of the specific player should be shown at different times and key presses
     * @param g2 main panel displaying the player
     */
    public void draw(Graphics g2) {
        BufferedImage image = null;

        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                else if (spriteNum == 2) {
                    image = up2;
                }
                else if (spriteNum == 3) {
                    image = up3;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                else if (spriteNum == 2) {
                    image = down2;
                }
                else if (spriteNum == 3) {
                    image = down3;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                else if (spriteNum == 2) {
                    image = left2;
                }
                else if (spriteNum == 3) {
                    image = left3;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                else if (spriteNum == 2) {
                    image = right2;
                }
                else if (spriteNum == 3) {
                    image = right3;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    /**
     * Gets the image from resource source for different positions
     */
    public void getPlayerImage() {
        try {
            sourceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/players/wizard_red.png")));
            up1 = sourceImage.getSubimage(0, 48, 16, 16);
            up2 = sourceImage.getSubimage(16, 48, 16, 16);
            up3 = sourceImage.getSubimage(32, 48, 16, 16);
            down1 = sourceImage.getSubimage(0, 0, 16, 16);
            down2 = sourceImage.getSubimage(16, 0, 16, 16);
            down3 = sourceImage.getSubimage(32, 0, 16, 16);
            left1 = sourceImage.getSubimage(0, 16, 16, 16);
            left2 = sourceImage.getSubimage(16, 16, 16, 16);
            left3 = sourceImage.getSubimage(32, 16, 16, 16);
            right1 = sourceImage.getSubimage(0, 32, 16, 16);
            right2 = sourceImage.getSubimage(16, 32, 16, 16);
            right3 = sourceImage.getSubimage(32, 32, 16, 16);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
