package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Creates the map displayed to the user
 */
public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    /**
     * Constructor initializing the types of types and map
     * @param gp panel being displayed
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxTileCol][gp.maxTileRow];

        getTileImage();
        loadMap("/maps/map01");
    }

    /**
     * Retrieving the tile images into an array
     */
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/wall.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tiles/water.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map data from a document into an array representing the document
     * @param filePath path of a text document representing the map
     */
    public void loadMap(String filePath) {
        try {
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxTileRow; row++) {
                String line = br.readLine();
                String[] splitLine = line.split(" ");

                for (int col = 0; col < gp.maxTileCol; col++) {
                    mapTileNum[col][row] = Integer.parseInt(splitLine[col]);
                }
            }

            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays each tile on the panel based on the map data
     * @param g2 graphics generator
     */
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxTileCol && row < gp.maxTileRow) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxTileCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
