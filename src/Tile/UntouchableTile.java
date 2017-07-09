package Tile;

import Ball.Ball;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class UntouchableTile extends Tile {
    private final String IMAGE = "Textures\\UntouchableTile.png";

    public UntouchableTile(int positionX, int positionY)
    {
        super(TileState.untouchable, positionX, positionY);
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
