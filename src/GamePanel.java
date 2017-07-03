import Ball.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Acer on 03.07.2017.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{
    public static Level[] LEVELS;

    public final int MOVEMENT_SPEED_HORIZONTAL = 2;
    public final int MOVEMENT_SPEED_VERTICAL = 1;

    private Thread _mainThread;
    private boolean _isUpPressed;
    private boolean _isDownPressed;
    private boolean _isLeftPressed;
    private boolean _isRightPressed;

    public GamePanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));

        ReadLevels();

        Frame = new JFrame("LogicPuzzle");
        Frame.setLocation(100, 100);
        Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Frame.add(this);
        Frame.addKeyListener(this);
        Frame.pack();
        Frame.setVisible(true);

        CurrentLevel = LEVELS[0]; //TODO only for testing right now

        _mainThread = new Thread(this);
        _mainThread.start();
    }

    private JFrame Frame;
    private Level CurrentLevel;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP)
        {
            _isUpPressed = true;
        }

        if (keyCode == KeyEvent.VK_DOWN)
        {
            _isDownPressed = true;
        }

        if (keyCode == KeyEvent.VK_LEFT)
        {
            _isLeftPressed = true;
        }

        if (keyCode == KeyEvent.VK_RIGHT)
        {
            _isRightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE)
        {
            if (CurrentLevel.GetBalls().length > 1)
            {
                for (int i = 0; i < CurrentLevel.GetBalls().length; i++)
                {
                    if (CurrentLevel.GetBalls()[i].GetInstance().IsActivated())
                    {
                        ((ControlledBall)CurrentLevel.GetBalls()[i].GetInstance()).Deactivate();
                        if ((i+1) < (CurrentLevel.GetBalls().length))
                        {
                            CurrentLevel.GetBalls()[i+1].GetInstance().Activate();
                        }
                        else
                        {
                            CurrentLevel.GetBalls()[0].GetInstance().Activate();
                        }

                        return;
                    }
                }
            }
        }
        else
        {
            if (keyCode == KeyEvent.VK_UP)
            {
                _isUpPressed = false;
            }

            if (keyCode == KeyEvent.VK_DOWN)
            {
                _isDownPressed = false;
            }

            if (keyCode == KeyEvent.VK_LEFT)
            {
                _isLeftPressed = false;
            }

            if (keyCode == KeyEvent.VK_RIGHT)
            {
                _isRightPressed = false;
            }
        }
    }

    @Override
    public void run() {
        while (Frame.isVisible())
        {
            CheckKeyboardInputs();

            repaint();

            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        for (int b = 0; b < CurrentLevel.GetBalls().length; b++)
        {
            CurrentLevel.GetBalls()[b].GetInstance().DrawBall(graphics);
        }

        if (CurrentLevel.GetTiles() != null)
        {
            for (int rows = 0; rows < CurrentLevel.GetTiles().length; rows++)
            {
                for (int columns = 0; columns < CurrentLevel.GetTiles()[rows].length; columns++)
                {
                    CurrentLevel.GetTiles()[rows][columns].DrawTile(graphics);
                }
            }
        }
    }

    private void CheckKeyboardInputs()
    {
        if (_isUpPressed)
        {
            if (CurrentLevel.GetCurrentActiveBall() != null)
            {
                CurrentLevel.GetCurrentActiveBall().GetInstance().MoveVertical(-MOVEMENT_SPEED_VERTICAL);
            }
        }

        if (_isDownPressed)
        {
            if (CurrentLevel.GetCurrentActiveBall() != null)
            {
                CurrentLevel.GetCurrentActiveBall().GetInstance().MoveVertical(MOVEMENT_SPEED_VERTICAL);
            }
        }

        if (_isLeftPressed)
        {
            if (CurrentLevel.GetCurrentActiveBall() != null)
            {
                CurrentLevel.GetCurrentActiveBall().GetInstance().MoveHorizontal(-MOVEMENT_SPEED_HORIZONTAL);
            }
        }

        if (_isRightPressed)
        {
            if (CurrentLevel.GetCurrentActiveBall() != null)
            {
                CurrentLevel.GetCurrentActiveBall().GetInstance().MoveHorizontal(MOVEMENT_SPEED_HORIZONTAL);
            }
        }
    }

    private void ReadLevels()
    {
        //TODO only for testing right now
        LEVELS = new Level[1];
        Ball[] balls = new Ball[2];
        balls[0] = new ControlledBall(1,1,1, BallState.idle, this);
        balls[1] = new IdleBall(100,200,2, this);
        LEVELS[0] = new Level(1, null, balls, "", 10, 10);
    }
}
