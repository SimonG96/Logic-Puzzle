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

    public BufferedImage GetImage()
    {
        return Image;
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


    public boolean Intersects(Ball ball)
    {
        Rectangle2D.Double cut = (Double) this.createIntersection(ball.getBounds2D());

        if (cut.width < 1 || cut.height < 1)
        {
            return false;
        }

        Rectangle2D.Double sub_this = GetSubRectangle(this, cut);
        Rectangle2D.Double sub_ball = GetSubRectangle(ball, cut);

        BufferedImage img_this = Image.getSubimage((int) sub_this.x, (int) sub_this.y, (int) sub_this.width, (int) sub_this.height);
        BufferedImage img_ball = ball.GetImage().getSubimage((int) sub_ball.x, (int) sub_ball.y, (int) sub_ball.width, (int) sub_ball.height);


        for (int i=0; i < img_this.getWidth(); i++)
        {
            for (int j=0; j < img_ball.getHeight(); j++)
            {
                int rgb1 = img_this.getRGB(i, j);
                int rgb2 = img_ball.getRGB(i, j);

                if (IsOpaque(rgb1) && IsOpaque(rgb2)) //check if at specific pixel on both Images the color is different to white -> if true -> collision
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected Rectangle2D.Double GetSubRectangle(Rectangle2D.Double source, Rectangle2D.Double part)
    {
        //get parts of both rectangles that could collide
        Rectangle2D.Double sub = new Rectangle2D.Double();

        if (source.x > part.x)
        {
            sub.x = 0;
        }
        else
        {
            sub.x = part.x-source.x;
        }

        if (source.y > part.y)
        {
            sub.y = 0;
        }
        else
        {
            sub.y = part.y - source.y;
        }

        sub.width = part.width;
        sub.height = part.height;

        return sub;
    }

    protected boolean IsOpaque(int rgb)
    {
        int alpha = (rgb >> 24) & 0xff; //shift rgb bits 24 digits to the right and bitwise and with white

        if (alpha == 0)
        {
            return false;
        }

        return true;
    }
}
