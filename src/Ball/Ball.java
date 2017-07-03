package Ball;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by s.gockner on 26.06.2017.
 */
public abstract class Ball extends Ellipse2D.Double{

    private final int BALL_HEIGHT = 50;
    private final int BALL_WIDTH = 50;

    public Ball(BallState currentBallState, double positionX, double positionY, int id, JPanel gamePanel)
    {
        GamePanel = gamePanel;

        CurrentBallState = currentBallState;
        PreviousBallState = null;
        StartingPositionX = positionX;
        PositionX = positionX;
        StartingPositionY = positionY;
        PositionY = positionY;
        ID = id;

        this.height = BALL_HEIGHT;
        this.width = BALL_WIDTH;

        Instance = this;
    }

    protected Ball Instance;

    public Ball GetInstance()
    {
        return Instance;
    }

    protected JPanel GamePanel;
    protected final int ID;
    protected BallState CurrentBallState;
    protected BallState PreviousBallState;
    protected double PositionX;
    protected double PositionY;
    protected final double StartingPositionX;
    protected final double StartingPositionY;
    protected BufferedImage Image;

    public int GetID()
    {
        return Instance.ID;
    }

    public BallState GetState()
    {
        return Instance.CurrentBallState;
    }

    public BallState GetPreviousBallState()
    {
        return Instance.PreviousBallState;
    }

    public double GetPositionX()
    {
        return Instance.PositionX;
    }

    public double GetPositionY()
    {
        return Instance.PositionY;
    }

    public double GetStartingPositionX()
    {
        return Instance.StartingPositionX;
    }

    public double GetStartingPositionY()
    {
        return Instance.StartingPositionY;
    }

    public boolean IsActivated()
    {
        return Instance.CurrentBallState == BallState.controlled;
    }

    public void MoveHorizontal(double movement)
    {
        if ((Instance.PositionX + movement) < 0)
        {
            Instance.PositionX = 0;
        }
        else if((Instance.PositionX + Instance.width + movement) > Instance.GamePanel.getWidth())
        {
            Instance.PositionX = Instance.GamePanel.getWidth() - Instance.width;
        }
        else
        {
            Instance.PositionX += movement;
        }
    }

    public void MoveVertical(double movement)
    {
        if ((Instance.PositionY + movement) < 0)
        {
            Instance.PositionY = 0;
        }
        else if((Instance.PositionY + Instance.height + movement) > Instance.GamePanel.getHeight())
        {
            Instance.PositionY = Instance.GamePanel.getHeight() - Instance.height;
        }
        else
        {
            Instance.PositionY += movement;
        }


        Instance.PositionY += movement;
    }

    public void ChangeBallType(BallState ballState)
    {
        double startingPositionX = Instance.StartingPositionX;
        double startingPositionY = Instance.StartingPositionY;
        double positionX = Instance.PositionX;
        double positionY = Instance.PositionY;
        int id = Instance.ID;
        BallState currentBallState = Instance.CurrentBallState;

        switch (ballState)
        {
            case idle:
            {
                Instance = new IdleBall(positionX, positionY, id, Instance.GamePanel);
                break;
            }
            case animated:
            {
                Instance = new AnimatedBall(positionX, positionY, id, null/*TODO: get previous animation state*/, Instance.GamePanel);
                break;
            }
            case controlled:
            {
                Instance = new ControlledBall(positionX, positionY, id, currentBallState, Instance.GamePanel);
                break;
            }
            case locked:
            {
                Instance = new LockedBall(positionX, positionY, id, Instance.GamePanel);
                break;
            }
            default:
            {
                break;
            }
        }
    }

    protected void LoadImage(String path)
    {
        try
        {
            URL imageUrl = getClass().getClassLoader().getResource(path);
            Image = ImageIO.read(imageUrl);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void DrawBall(Graphics graphics)
    {
        graphics.drawImage(Instance.Image, (int) Instance.PositionX, (int) Instance.PositionY, null);
    }

    public abstract void Activate();
}
