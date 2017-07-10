package Level;

import Ball.*;
import Tile.*;

import java.util.ArrayList;

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

    public ArrayList<Tile> GetTilesByTileState(TileState tileState)
    {
        ArrayList<Tile> tiles = new ArrayList<Tile>();

        for (int rows = 0; rows < Tiles.length; rows++)
        {
            for (int columns = 0; columns < Tiles[rows].length; columns++)
            {
                Tile tile = Tiles[rows][columns];
                if (tile.GetCurrentTileState() == tileState)
                {
                    tiles.add(tile);
                }
            }
        }

        return tiles;
    }

    public ArrayList<GateOpenerTile> GetGateOpenerTilesForOneGate(int id)
    {
        ArrayList<GateOpenerTile> gateOpenerTilesForGate = new ArrayList<GateOpenerTile>();

        ArrayList<Tile> gateOpenerTiles = GetTilesByTileState(TileState.openGate);

        for (int i = 0; i < gateOpenerTiles.size(); i++)
        {
            GateOpenerTile gateOpenerTile = (GateOpenerTile) gateOpenerTiles.get(i);
            if (gateOpenerTile.GetID() == id)
            {
                gateOpenerTilesForGate.add(gateOpenerTile);
            }
        }

        return gateOpenerTilesForGate;
    }

    public GateTile GetGateByID(int id)
    {
        GateTile gateTile = null;

        ArrayList<Tile> gates = GetTilesByTileState(TileState.gate);
        for (int i = 0; i < gates.size(); i++)
        {
            GateTile currentGateTile = (GateTile) gates.get(i);
            if (currentGateTile.GetID() == id)
            {
                gateTile = currentGateTile;
                break;
            }
        }

        return gateTile;
    }

    public Ball[] GetBalls()
    {
        return Balls;
    }

    public Ball[] GetBallsSortedByID()
    {
        Ball[] balls = new Ball[Balls.length];

        for (int i = 0; i < Balls.length; i++)
        {
            for (int j = 0; j < Balls.length; j++)
            {
                if (i+1 == Balls[j].GetID())
                {
                    balls[i] = Balls[j];
                }
            }
        }

        return balls;
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

    public ArrayList<AnimatedBall> GetAnimatedBalls()
    {
        ArrayList<AnimatedBall> animatedBalls = new ArrayList<AnimatedBall>();

        for (int i = 0; i < Balls.length; i++)
        {
            if (Balls[i].GetState() == BallState.animated || Balls[i].GetState() == BallState.animated_locked)
            {
                animatedBalls.add((AnimatedBall) Balls[i]);
            }
        }

        return animatedBalls;
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

    public void RestartLevel()
    {
        for (int i = 0; i < Balls.length; i++)
        {
            Balls[i].x = Balls[i].GetStartingPositionX() * Tile.TILE_WIDTH - (Ball.BALL_WIDTH - Tile.TILE_WIDTH) / 2;
            Balls[i].y = Balls[i].GetStartingPositionY() * Tile.TILE_HEIGHT - (Ball.BALL_HEIGHT - Tile.TILE_HEIGHT) / 2;

            if (Balls[i].IsActivated())
            {
                Balls[i].Deactivate();
            }

            if (Balls[i].IsFirstActivatedBall())
            {
                Balls[i].Activate();
            }
        }
    }
}