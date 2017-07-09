package Ball;

import Tile.*;
import GamePanel.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class Ball extends Ellipse2D.Double{

    private final String IMAGE_PATH_CONTROLLED = "Textures\\acid_ball_standard_small.png";
    private final String IMAGE_PATH_IDLE = "Textures\\acid_ball_wallhit_small.png";

    public final static int BALL_HEIGHT = 40;
    public final static int BALL_WIDTH = 40;

    public Ball(BallState currentBallState, int positionX, int positionY, int id, GamePanel gamePanel)
    {
        GamePanel = gamePanel;

        CurrentBallState = currentBallState;
        PreviousBallState = currentBallState;
        StartingPositionX = positionX;
        StartingPositionY = positionY;
        ID = id;
        IsMovable = true;
        IsControlledByLockedBall = false;

        this.height = BALL_HEIGHT;
        this.width = BALL_WIDTH;
        this.x = positionX * Tile.TILE_WIDTH - (BALL_WIDTH - Tile.TILE_WIDTH) / 2;
        this.y = positionY * Tile.TILE_HEIGHT - (BALL_HEIGHT - Tile.TILE_HEIGHT) / 2;

        LoadImage();
    }

    public Ball(BallState currentBallState, int positionX, int positionY, int id, GamePanel gamePanel, BallState previousBallState)
    {
        this(currentBallState, positionX, positionY, id, gamePanel);
        PreviousBallState = previousBallState;
        FirstActivatedBall = true;
    }


    protected GamePanel GamePanel;
    protected final int ID;
    protected BallState CurrentBallState;
    protected BallState PreviousBallState;
    protected final int StartingPositionX;
    protected final int StartingPositionY;
    protected BufferedImage Image;
    protected boolean FirstActivatedBall;
    protected boolean IsMovable;
    protected boolean IsControlledByLockedBall;

    public int GetID()
    {
        return ID;
    }

    public BallState GetState()
    {
        return CurrentBallState;
    }

    public BallState GetPreviousBallState()
    {
        return PreviousBallState;
    }

    public int GetStartingPositionX()
    {
        return StartingPositionX;
    }

    public int GetStartingPositionY()
    {
        return StartingPositionY;
    }

    public boolean IsFirstActivatedBall()
    {
        return FirstActivatedBall;
    }

    public boolean IsMovable()
    {
        return IsMovable;
    }

    public boolean IsControlledByLockedBall()
    {
        return IsControlledByLockedBall;
    }

    public boolean IsActivated()
    {
        return CurrentBallState == BallState.controlled;
    }

    public boolean IsAnimated()
    {
        return CurrentBallState == BallState.animated || CurrentBallState == BallState.animated_locked;
    }

    public void MoveHorizontal(double movement)
    {
        if (IsActivated() && IsMovable || IsAnimated() || IsControlledByLockedBall)
        {
            movement = GetCorrectMovementByCheckingCollisionWithBorder(movement, true);
            x += movement;
        }
    }

    public void MoveVertical(double movement)
    {
        if (IsActivated() && IsMovable || IsAnimated() || IsControlledByLockedBall)
        {
            movement = GetCorrectMovementByCheckingCollisionWithBorder(movement, false);
            y += movement;
        }
    }

    public double GetCorrectMovementByCheckingCollisionWithBorder(double movement, boolean isHorizontal)
    {
        ArrayList<Tile> tiles = GamePanel.GetCurrentLevel().GetTilesByTileState(TileState.border);
        ArrayList<Tile> unmovableTiles = GamePanel.GetCurrentLevel().GetTilesByTileState(TileState.unmovable);
        ArrayList<Tile> gates = GamePanel.GetCurrentLevel().GetTilesByTileState(TileState.gate);

        if (gates.size() != 0)
        {
            for (int gateCounter = 0; gateCounter < gates.size(); gateCounter++)
            {
                GateTile gateTile = (GateTile) gates.get(gateCounter);
                if (!gateTile.GetIsOpened())
                {
                    tiles.add(gateTile);
                }
            }
        }

        if (IsActivated())
        {
            tiles.addAll(unmovableTiles);
        }

        for (int i = 0; i < tiles.size(); i++)
        {
            Tile tile = tiles.get(i);

            double borderTileX1 = tile.getX();
            double borderTileX2 = borderTileX1 + tile.getWidth();
            double borderTileY1 = tile.getY();
            double borderTileY2 = borderTileY1 + tile.getHeight();

            double ballX2 = x + width;
            double ballY2 = y + height;

            if (isHorizontal)
            {
                boolean isTileOnTheSameHeight = (borderTileY1 < y && y < borderTileY2 || borderTileY1 < ballY2 && ballY2 < borderTileY2);
                boolean isTileOnTheRight = borderTileX1 >= ballX2;
                if (movement > 0) //Ball moves right
                {
                    if (((ballX2 + movement) > borderTileX1) && isTileOnTheRight && isTileOnTheSameHeight)
                    {
                        return borderTileX1 - ballX2;
                    }
                }
                else //Ball moves left
                {
                    if (((x + movement) < borderTileX2) && !isTileOnTheRight && isTileOnTheSameHeight)
                    {
                        return borderTileX2 - x;
                    }
                }

            }
            else //!isHorizontal
            {
                boolean isTileOnTheSameWidth = (borderTileX1 < x && x < borderTileX2 || borderTileX1 < ballX2 && ballX2 < borderTileX2);
                boolean isTileUnder = borderTileY1 >= ballY2;
                if (movement < 0) //Ball moves up
                {
                    if (((y + movement) < borderTileY2) && !isTileUnder && isTileOnTheSameWidth)
                    {
                        return borderTileY2 - y;
                    }
                }
                else //Ball moves down
                {
                    if (((ballY2 + movement) > borderTileY1) && isTileUnder && isTileOnTheSameWidth)
                    {
                        return borderTileY1 - ballY2;
                    }
                }
            }
        }

        return movement;
    }

    public void CheckForCollisionWithUnmovableTile(ArrayList<Tile> unmovableTiles)
    {
        boolean collision = false;

        for (int i = 0; i < unmovableTiles.size(); i++)
        {
            if (this.getBounds2D().intersects(unmovableTiles.get(i).getBounds2D()))
            {
                collision = true;
                IsMovable = false;
            }
        }

        if (!collision)
        {
            if (!IsMovable)
            {
                IsMovable = true;
            }
        }
    }

    protected void LoadImage()
    {
        String path = "";
        if (CurrentBallState == BallState.controlled)
        {
            path = IMAGE_PATH_CONTROLLED;
        }
        else if (CurrentBallState == BallState.idle)
        {
            path = IMAGE_PATH_IDLE;
        }
        else if (CurrentBallState == BallState.animated)
        {
            path = IMAGE_PATH_IDLE;
        }
        else if (CurrentBallState == BallState.locked)
        {
            path = IMAGE_PATH_IDLE;
        }
        else if (CurrentBallState == BallState.animated_locked)
        {
            path = IMAGE_PATH_IDLE;
        }

        try
        {
            URL imageUrl = getClass().getClassLoader().getResource(path);
            if (imageUrl != null)
            {
                Image = ImageIO.read(imageUrl);
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void DrawBall(Graphics graphics)
    {
        graphics.drawImage(Image, (int) x, (int) y, null);
    }

    public void Activate()
    {
        CurrentBallState = BallState.controlled;
        LoadImage();
    }

    public void Deactivate()
    {
        if (IsActivated())
        {
            CurrentBallState = PreviousBallState;
            LoadImage();
        }
    }
}
