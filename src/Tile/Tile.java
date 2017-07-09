package Tile;

import Ball.Ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by s.gockner on 26.06.2017.
 */
public abstract class Tile extends Rectangle2D.Double{
    public final static int TILE_HEIGHT = 50;
    public final static int TILE_WIDTH = 50;

    public Tile(TileState currentTileState, int positionX, int positionY)
    {
        CurrentTileState = currentTileState;

        LevelPositionX = positionX;
        LevelPositionY = positionY;

        this.height = TILE_HEIGHT;
        this.width = TILE_WIDTH;
        this.x = LevelPositionX * TILE_WIDTH;
        this.y = LevelPositionY * TILE_HEIGHT;
    }

    protected TileState CurrentTileState;
    protected int LevelPositionX;
    protected int LevelPositionY;
    protected BufferedImage Image;

    public TileState GetCurrentTileState()
    {
        return CurrentTileState;
    }

    public int GetLevelPositionX()
    {
        return LevelPositionX;
    }

    public int GetLevelPositionY()
    {
        return LevelPositionY;
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
        graphics.drawImage(Image, (int) x, (int) y, null);
    }
}
