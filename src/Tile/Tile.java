package Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class Tile extends Rectangle2D.Double{
    private final int TILE_HEIGHT = 10;
    private final int TILE_WIDTH = 10;

    public Tile(TileState currentTileState)
    {
        CurrentTileState = currentTileState;

        this.height = TILE_HEIGHT;
        this.width = TILE_WIDTH;
    }

    public TileState CurrentTileState;

    protected double PositionX;
    protected double PositionY;
    protected BufferedImage Image;

    public double GetPositionX()
    {
        return PositionX;
    }

    public double GetPositionY()
    {
        return PositionY;
    }

    protected void LoadImage(String path)
    {
        try
        {
            URL imageUrl = getClass().getClassLoader().getResource(path);
            Image = ImageIO.read(imageUrl);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void DrawTile(Graphics graphics)
    {
        graphics.drawImage(Image, (int) PositionX, (int) PositionY, null);
    }
}
