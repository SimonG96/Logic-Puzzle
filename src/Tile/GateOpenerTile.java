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
        GateTile = null;

        LoadImage(IMAGE);
    }

    private int ID;
    private GateTile GateTile;

    public int GetID()
    {
        return ID;
    }

    public GateTile GetGateTile()
    {
        return GateTile;
    }

    public void SetGateTile(GateTile gateTile)
    {
        GateTile = gateTile;
    }

    public boolean CheckForCollision(Ball[] balls)
    {
        boolean collision = false;

        for (int i = 0; i < balls.length; i++)
        {
            if (this.Intersects(balls[i]))
            {
                collision = true;

                if (!GateTile.GetIsOpened())
                {
                    GateTile.OpenGate();
                }
            }
        }

        if (!collision)
        {
            if (GateTile.GetIsOpened() && !GateTile.CheckForCollision(balls))
            {
                GateTile.CloseGate();
            }
        }

        return collision;
    }
}
