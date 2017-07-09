package Tile;

/**
 * Created by Acer on 03.07.2017.
 */
public class BorderTile extends Tile {
    private final String IMAGE = "Textures\\BorderTile.png";

    public BorderTile(int positionX, int positionY)
    {
        super(TileState.border, positionX, positionY);
        LoadImage(IMAGE);
    }
}
