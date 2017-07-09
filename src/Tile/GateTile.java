package Tile;

import Ball.Ball;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class GateTile extends Tile {
    private final String IMAGE_CLOSED = "Textures\\GateTile.png";
    private final String IMAGE_OPENED = "Textures\\GateTile_opened.png";

    public GateTile(int positionX, int positionY, int id)
    {
        super(TileState.gate, positionX, positionY);

        ID = id;
        IsOpened = false;

        LoadImage(IMAGE_CLOSED);
    }

    private int ID;
    private boolean IsOpened;

    public int GetID()
    {
        return ID;
    }

    public boolean GetIsOpened()
    {
        return IsOpened;
    }

    public void OpenGate()
    {
        IsOpened = true;
        LoadImage(IMAGE_OPENED);
    }

    public void CloseGate()
    {
        IsOpened = false;
        LoadImage(IMAGE_CLOSED);
    }

    public boolean CheckForCollision(Ball[] balls)
    {
        for (int i = 0; i < balls.length; i++)
        {
            if (this.getBounds2D().intersects(balls[i].getBounds2D()))
            {
                return true;
            }
        }

        return false;
    }
}
