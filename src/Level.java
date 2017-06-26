import Ball.Ball;
import Tile.Tile;

/**
 * Created by s.gockner on 19.06.2017.
 */
public class Level {
    public Level(int levelNumber, Tile[][] tiles, Ball[] balls, String tipp)
    {
        LevelNumber = levelNumber;
        Tiles = tiles;
        Balls = balls;
        Tipp = tipp;
    }

    private int LevelNumber;
    private Tile[][] Tiles;
    private Ball[] Balls;
    private String Tipp;


    public int GetLevelNumber()
    {
        return LevelNumber;
    }

    public Tile[][] GetTiles()
    {
        return Tiles;
    }

    public Ball[] GetBalls()
    {
        return Balls;
    }

    public int GetNumberOfBalls()
    {
        return Balls.length;
    }

    public String GetTipp()
    {
        return Tipp;
    }
}