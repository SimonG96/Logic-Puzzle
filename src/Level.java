import Ball.Ball;
import Tile.Tile;

/**
 * Created by s.gockner on 19.06.2017.
 */
public class Level {
    public Level(int levelNumber, Tile[][] tiles, Ball[] balls, String tipp, int sizeX, int sizeY)
    {
        LevelNumber = levelNumber;
        Tiles = tiles;
        Balls = balls;
        Tipp = tipp;
        SizeX = sizeX;
        SizeY = sizeY;
    }

    private int LevelNumber;
    private Tile[][] Tiles;
    private Ball[] Balls;
    private String Tipp;
    private int SizeX;
    private int SizeY;


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

    public Ball GetCurrentActiveBall()
    {
        for (int i = 0; i < Balls.length; i++)
        {
            if (Balls[i].IsActivated())
            {
                return Balls[i];
            }
        }

        return null;
    }

    public int GetNumberOfBalls()
    {
        return Balls.length;
    }

    public String GetTipp()
    {
        return Tipp;
    }

    public int GetSizeX()
    {
        return SizeX;
    }

    public int GetSizeY()
    {
        return SizeY;
    }
}