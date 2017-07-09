package GamePanel;

import Ball.*;
import Tile.*;
import Level.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by Acer on 03.07.2017.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{
    public final int MOVEMENT_SPEED_HORIZONTAL = 2;
    public final int MOVEMENT_SPEED_VERTICAL = 2;

    private Thread _mainThread;
    private boolean _isUpPressed;
    private boolean _isDownPressed;
    private boolean _isLeftPressed;
    private boolean _isRightPressed;

    public GamePanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));

        Frame = new JFrame("LogicPuzzle");
        Frame.setLocation(100, 100);
        Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Frame.add(this);
        Frame.addKeyListener(this);
        Frame.pack();
        Frame.setVisible(true);

        LevelHelper levelHelper = new LevelHelper();
        levelHelper.ReadLevels(this);
        CurrentLevel = LevelHelper.LEVELS.get(0); //TODO only for testing right now

        _mainThread = new Thread(this);
        _mainThread.start();
    }

    private JFrame Frame;
    private Level CurrentLevel;
    private int Deaths;

    public Level GetCurrentLevel()
    {
        return CurrentLevel;
    }

    public int GetDeaths()
    {
        return Deaths;
    }

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
                    if (CurrentLevel.GetBallsSortedByID()[i].IsActivated())
                    {
                        CurrentLevel.GetBallsSortedByID()[i].Deactivate();
                        if ((i+1) < (CurrentLevel.GetBalls().length))
                        {
                            CurrentLevel.GetBallsSortedByID()[i+1].Activate();
                        }
                        else
                        {
                            CurrentLevel.GetBallsSortedByID()[0].Activate();
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
            CheckForEndOfLevel();
            CheckForCollisionWithUntouchableTile();
            CheckForCollisionWithGateOpenerTile();
            CheckForCollisionWithUnmovableTile();
            MoveAnimatedBalls();

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

        if (CurrentLevel != null)
        {
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

            Ball currentControlledBall = null;
            for (int b = 0; b < CurrentLevel.GetBalls().length; b++)
            {
                Ball ball = CurrentLevel.GetBalls()[b];
                if (ball.IsActivated())
                {
                    currentControlledBall = ball;
                }
                else
                {
                    ball.DrawBall(graphics);
                }
            }

            if (currentControlledBall != null)
            {
                currentControlledBall.DrawBall(graphics);
            }
        }
    }

    private void CheckKeyboardInputs()
    {
        if (_isUpPressed)
        {
            if (CurrentLevel.GetCurrentActiveBall() != null)
            {
                CurrentLevel.GetCurrentActiveBall().MoveVertical(-MOVEMENT_SPEED_VERTICAL);
            }
        }

        if (_isDownPressed)
        {
            if (CurrentLevel.GetCurrentActiveBall() != null)
            {
                CurrentLevel.GetCurrentActiveBall().MoveVertical(MOVEMENT_SPEED_VERTICAL);
            }
        }

        if (_isLeftPressed)
        {
            if (CurrentLevel.GetCurrentActiveBall() != null)
            {
                CurrentLevel.GetCurrentActiveBall().MoveHorizontal(-MOVEMENT_SPEED_HORIZONTAL);
            }
        }

        if (_isRightPressed)
        {
            if (CurrentLevel.GetCurrentActiveBall() != null)
            {
                CurrentLevel.GetCurrentActiveBall().MoveHorizontal(MOVEMENT_SPEED_HORIZONTAL);
            }
        }
    }

    private void ResetKeyboardInputs()
    {
        _isUpPressed = false;
        _isDownPressed = false;
        _isLeftPressed = false;
        _isRightPressed = false;

        //MOVEMENT_SPEED_HORIZONTAL = 0;
        //MOVEMENT_SPEED_VERTICAL = 0;
    }

    private void CheckForEndOfLevel()
    {
        ArrayList<Tile> targetTiles = CurrentLevel.GetTilesByTileState(TileState.target);
        for (int i = 0; i < targetTiles.size(); i++)
        {
            TargetTile targetTile = (TargetTile) targetTiles.get(i);
            if (targetTile.CheckForCollision(CurrentLevel.GetBalls()))
            {
                ResetKeyboardInputs();

                int levelNumber = CurrentLevel.GetLevelNumber();
                if (LevelHelper.LEVELS.size() > levelNumber)
                {
                    //CurrentLevel = null;
                    //JOptionPane.showMessageDialog(this, "Level Completed. Try Level " + (levelNumber+1), "Level Completed", JOptionPane.INFORMATION_MESSAGE);
                    CurrentLevel = LevelHelper.LEVELS.get(levelNumber);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Congratulations! You have beaten the Game!", "Game Finished", JOptionPane.INFORMATION_MESSAGE);
                    
                }

                break;
            }
        }
    }

    private void CheckForCollisionWithUntouchableTile()
    {
        ArrayList<Tile> untouchableTiles = CurrentLevel.GetTilesByTileState(TileState.untouchable);
        if (untouchableTiles.size() != 0)
        {
            for (int i = 0; i < untouchableTiles.size(); i++)
            {
                UntouchableTile untouchableTile = (UntouchableTile) untouchableTiles.get(i);
                if (untouchableTile.CheckForCollision(CurrentLevel.GetBalls()))
                {
                    CurrentLevel.RestartLevel();
                    JOptionPane.showMessageDialog(this, "You died!", "Dead", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void CheckForCollisionWithGateOpenerTile()
    {
        ArrayList<Tile> gateOpenerTiles = CurrentLevel.GetTilesByTileState(TileState.openGate);
        if (gateOpenerTiles.size() != 0)
        {
            for (int i = 0; i < gateOpenerTiles.size(); i++)
            {
                GateOpenerTile gateOpenerTile = (GateOpenerTile) gateOpenerTiles.get(i);
                if (gateOpenerTile.CheckForCollision(CurrentLevel.GetGateByID(gateOpenerTile.GetID()), CurrentLevel.GetBalls()))
                {
                    break;
                }
            }
        }
    }

    public void CheckForCollisionWithUnmovableTile()
    {
        ArrayList<Tile> unmovableTiles = CurrentLevel.GetTilesByTileState(TileState.unmovable);
        if (unmovableTiles.size() != 0)
        {
            /*for (int i = 0; i < unmovableTiles.size(); i++)
            {
                UnmovableTile unmovableTile = (UnmovableTile) unmovableTiles.get(i);
                unmovableTile.CheckForCollision(CurrentLevel.GetBalls());
            }*/

            for (int i = 0; i < CurrentLevel.GetBalls().length; i++)
            {
                CurrentLevel.GetBalls()[i].CheckForCollisionWithUnmovableTile(unmovableTiles);
            }
        }
    }

    private void MoveAnimatedBalls()
    {
        ArrayList<AnimatedBall> animatedBalls = CurrentLevel.GetAnimatedBalls();
        if (animatedBalls.size() != 0)
        {
            for (int i = 0; i < animatedBalls.size(); i++)
            {
                animatedBalls.get(i).Animate();
            }
        }
    }

    /*private void ReadLevels()
    {
        //TODO only for testing right now
        LEVELS = new Levels.Level[1];
        Ball[] balls = new Ball[2];
        balls[0] = new Ball(BallState.controlled, 1,1,1, this, BallState.idle);
        balls[1] = new Ball(BallState.idle, 100,200,2, this);
        Tile[][] tiles = {
                { new BorderTile(0,0), new BorderTile(0,1), new BorderTile(0,2)}
        };
        LEVELS[0] = new Levels.Level(1, tiles, balls, "", 10, 10);
    }*/
}
