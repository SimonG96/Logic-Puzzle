package Level;

import Ball.*;
import GamePanel.*;
import Tile.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Acer on 08.07.2017.
 */
public class LevelHelper {
    public static ArrayList<Level> LEVELS;

    public void ReadLevels(LevelPanel levelPanel)
    {
        LEVELS = new ArrayList<Level>();

        int levelCount = 0;
        URL url;

        do {
            levelCount++;

            String fileName = "Levels/Level" + levelCount + ".txt";
            url = getClass().getResource(fileName);

            if (url != null)
            {
                File file = new File(url.getPath());

                try
                {
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    String line = "";
                    while (!line.contains("<Levelnumber>"))
                    {
                        line = bufferedReader.readLine();
                    }

                    String levelNumberSplit1 = line.split(">")[1];
                    String levelNumberSplit2 = levelNumberSplit1.split(";")[0];
                    int levelNumber = Integer.parseInt(levelNumberSplit2);

                    while (!line.contains("<Tipp>"))
                    {
                        line = bufferedReader.readLine();
                    }

                    String tipp = line.split("'")[1];

                    while (!line.contains("<SizeX>"))
                    {
                        line = bufferedReader.readLine();
                    }

                    String sizeXSplit1 = line.split(">")[1];
                    String sizeXSplit2 = sizeXSplit1.split(";")[0];
                    int sizeX = Integer.parseInt(sizeXSplit2);

                    while (!line.contains("<SizeY>"))
                    {
                        line = bufferedReader.readLine();
                    }

                    String sizeYSplit1 = line.split(">")[1];
                    String sizeYSplit2 = sizeYSplit1.split(";")[0];
                    int sizeY = Integer.parseInt(sizeYSplit2);

                    while (!line.contains("<AmountOfBalls>"))
                    {
                        line = bufferedReader.readLine();
                    }

                    String amountOfBallsSplit1 = line.split(">")[1];
                    String amountOfBallsSplit2 = amountOfBallsSplit1.split(";")[0];
                    int amountOfBalls = Integer.parseInt(amountOfBallsSplit2);

                    while (!line.contains("<Balls>"))
                    {
                        line = bufferedReader.readLine();
                    }

                    Ball[] balls = new Ball[amountOfBalls];
                    int ballCount = 0;
                    line = bufferedReader.readLine();

                    do {
                        balls[ballCount] = ReadBallLine(line, levelPanel);
                        line = bufferedReader.readLine();
                        ballCount++;
                    } while (!line.contains("</Balls>"));

                    while (!line.contains("<Tiles>"))
                    {
                        line = bufferedReader.readLine();
                    }

                    Tile[][] tiles = new Tile[sizeY][sizeX];
                    int tilesYCount = 0;
                    line = bufferedReader.readLine();

                    do {
                        tiles[tilesYCount] = ReadTileLine(line, sizeX, tilesYCount, levelPanel);
                        line = bufferedReader.readLine();
                        tilesYCount++;
                    } while (!line.contains("</Tiles>"));

                    LEVELS.add(new Level(levelNumber, tiles, balls, tipp, sizeX, sizeY));
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        } while (url != null);
    }

    private static Ball ReadBallLine(String line, LevelPanel levelPanel)
    {
        line = line.replaceAll("\\t", "").replaceAll("\\n", "");
        String[] ballSplit = line.split(";");

        BallState ballState = BallState.values()[Integer.parseInt(ballSplit[0])];
        int positionX = Integer.parseInt(ballSplit[1]);
        int positionY = Integer.parseInt(ballSplit[2]);
        int id = Integer.parseInt(ballSplit[3]);

        if (ballState == BallState.controlled)
        {
            BallState prevBallState = BallState.values()[Integer.parseInt(ballSplit[4])];

            if (prevBallState == BallState.animated)
            {
                AnimationState animationState = AnimationState.values()[Integer.parseInt(ballSplit[5])];
                return new AnimatedBall(ballState, positionX, positionY, id, levelPanel, prevBallState, animationState);
            }
            else if (prevBallState == BallState.locked)
            {
                int[] ballIDs = new int[ballSplit.length-5];
                for (int i = 5; i < ballSplit.length; i++)
                {
                    ballIDs[i-5] = Integer.parseInt(ballSplit[i]);
                }

                return new LockedBall(ballState, positionX, positionY, id, levelPanel, prevBallState, ballIDs);
            }
            else
            {
                return new Ball(ballState, positionX, positionY, id, levelPanel, prevBallState);
            }
        }
        else if (ballState == BallState.animated)
        {
            AnimationState animationState = AnimationState.values()[Integer.parseInt(ballSplit[4])];
            return new AnimatedBall(ballState, positionX, positionY, id, animationState, levelPanel);
        }
        else if (ballState == BallState.locked)
        {
            int[] ballIDs = new int[ballSplit.length-4];
            for (int i = 4; i < ballSplit.length; i++)
            {
                ballIDs[i-4] = Integer.parseInt(ballSplit[i]);
            }

            return new LockedBall(positionX, positionY, id, levelPanel, ballIDs);
        }
        else if (ballState == BallState.animated_locked)
        {
            AnimationState animationState = AnimationState.values()[Integer.parseInt(ballSplit[4])];

            int[] ballIDs = new int[ballSplit.length-5];
            for (int i = 5; i < ballSplit.length; i++)
            {
                ballIDs[i-5] = Integer.parseInt(ballSplit[i]);
            }

            return new AnimatedLockedBall(positionX, positionY, id, animationState, levelPanel, ballIDs);
        }
        else
        {
            return new Ball(ballState, positionX, positionY, id, levelPanel);
        }
    }

    private static Tile[] ReadTileLine(String line, int sizeX, int yCount, LevelPanel levelPanel)
    {
        Tile[] tileLine = new Tile[sizeX];

        line = line.replaceAll("\\t", "").replaceAll("\\n", "");
        String[] tileLineSplit = line.split(";");

        for (int i = 0; i < sizeX; i++)
        {
            String tileStateSplit;
            String tileIDSplit = "";
            if (tileLineSplit[i].contains(",")) //Contains ID for Gate/GateOpener
            {
                String[] tileLineSplitStateID = tileLineSplit[i].split(",");
                tileStateSplit = tileLineSplitStateID[0];
                tileIDSplit = tileLineSplitStateID[1];
            }
            else
            {
                tileStateSplit = tileLineSplit[i];
            }


            TileState tileState = TileState.values()[Integer.parseInt(tileStateSplit)];

            switch (tileState)
            {
                case defaultTile:
                {
                    tileLine[i] = new DefaultTile(i, yCount);
                    break;
                }
                case target:
                {
                    tileLine[i] = new TargetTile(i, yCount);
                    break;
                }
                case border:
                {
                    tileLine[i] = new BorderTile(i, yCount);
                    break;
                }
                case untouchable:
                {
                    tileLine[i] = new UntouchableTile(i, yCount);
                    break;
                }
                case gate:
                {
                    tileLine[i] = new GateTile(i, yCount, Integer.parseInt(tileIDSplit));
                    break;
                }
                case openGate:
                {
                    tileLine[i] = new GateOpenerTile(i, yCount, Integer.parseInt(tileIDSplit));
                    break;
                }
                case unmovable:
                {
                    tileLine[i] = new UnmovableTile(i, yCount);
                    break;
                }
            }
        }

        return tileLine;
    }
}



/*
 {
         new Levels.Level(
         1,
         new Tile[][]
         {
         {new BorderTile()}
         },
         new Ball[]
         {

         },
         "",
         10,
         10
         )
         };*/
