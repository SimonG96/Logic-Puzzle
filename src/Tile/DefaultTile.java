package Tile;

/**
 * Created by Acer on 26.06.2017.
 */
public class DefaultTile extends Tile {
    private final String IMAGE = "Textures\\DefaultTile.png";

    public DefaultTile(int positionX, int positionY)
    {
        super(TileState.defaultTile, positionX, positionY);
        LoadImage(IMAGE);
    }
}
