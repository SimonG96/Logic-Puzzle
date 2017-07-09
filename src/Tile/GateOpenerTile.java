package Tile;

import Ball.Ball;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class GateOpenerTile extends Tile {
    private final String IMAGE = "Textures\\GateOpenerTile.png";

    public GateOpenerTile(int positionX, int positionY, int id)
    {
        super(TileState.openGate, positionX, positionY);

        ID = id;

        LoadImage(IMAGE);
    }

    private int ID;

    public int GetID()
    {
        return ID;
    }

    public boolean CheckForCollision(GateTile gateTile, Ball[] balls)
    {
        boolean collision = false;

        for (int i = 0; i < balls.length; i++)
        {
            if (this.getBounds2D().intersects(balls[i].getBounds2D()))
            {
                collision = true;

                if (!gateTile.GetIsOpened())
                {
                    gateTile.OpenGate();
                }
            }
        }

        if (!collision)
        {
            if (gateTile.GetIsOpened() && !gateTile.CheckForCollision(balls))
            {
                gateTile.CloseGate();
            }
        }

        return collision;
    }
}
