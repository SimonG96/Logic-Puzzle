package Tile;

import Ball.Ball;

/**
 * Created by Acer on 26.06.2017.
 */
public class TargetTile extends Tile {
    private final String IMAGE = "Textures\\TargetTile.png";

    public TargetTile(int positionX, int positionY)
    {
        super(TileState.target, positionX, positionY);
        LoadImage(IMAGE);
    }

    public boolean CheckForCollision(Ball[] balls)
    {
        for (int i = 0; i < balls.length; i++)
        {
            if (this.getBounds2D().intersects(balls[i].getBounds2D()))
            {
                balls[i].Deactivate();
                return true;
            }
        }

        return false;
    }
}
