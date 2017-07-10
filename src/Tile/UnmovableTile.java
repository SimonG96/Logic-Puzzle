package Tile;

import Ball.Ball;

/**
 * Created by Acer on 09.07.2017.
 */
public class UnmovableTile extends Tile{
    private final String IMAGE = "Textures\\UnmovableTile.png";

    public UnmovableTile(int positionX, int positionY)
    {
        super(TileState.unmovable, positionX, positionY);
        LoadImage(IMAGE);
    }
}
