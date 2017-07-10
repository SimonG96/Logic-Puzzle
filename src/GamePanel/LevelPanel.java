package GamePanel;

import Ball.*;
import Tile.*;
import Level.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by Acer on 03.07.2017.
 */
public class LevelPanel extends JPanel implements Runnable, KeyListener{
    public final double MOVEMENT_SPEED_HORIZONTAL = 1.7;
    public final double MOVEMENT_SPEED_VERTICAL = 1.7;

    private Thread _mainThread;
    private boolean _isUpPressed;
    private boolean _isDownPressed;
    private boolean _isLeftPressed;
    private boolean _isRightPressed;

    public LevelPanel(int width, int height) {
        //this.setPreferredSize(new Dimension(width, height));

        Frame = new JFrame("LogicPuzzle");
        Frame.setLocation(100, 100);
        Frame.setPreferredSize(new Dimension(width, height));
        Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Deaths = 0;

        LevelHelper levelHelper = new LevelHelper();
        levelHelper.ReadLevels(this);
        CurrentLevel = LevelHelper.LEVELS.get(0); //TODO only for testing right now

        HeaderLabelText = CurrentLevel.GetTipp() + " | " + Deaths + " Deaths | Level " + CurrentLevel.GetLevelNumber();
        HeaderLabel = new JLabel(HeaderLabelText);
        Frame.add(HeaderLabel, BorderLayout.BEFORE_FIRST_LINE);
        Frame.add(this, BorderLayout.CENTER);
        Frame.addKeyListener(this);
        Frame.pack();
        Frame.setVisible(true);

        _mainThread = new Thread(this);
        _mainThread.start();
    }

    private JLabel HeaderLabel;
    private String HeaderLabelText;
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

    private void ResetKeyPressed()
    {
        while (_isUpPressed || _isDownPressed || _isRightPressed || _isLeftPressed)
        {
            try
            {
                Robot robot = new Robot();
                robot.keyRelease(KeyEvent.VK_UP);
                robot.keyRelease(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_LEFT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
            }
            catch (AWTException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void CheckForEndOfLevel()
    {
        ArrayList<Tile> targetTiles = CurrentLevel.GetTilesByTileState(TileState.target);
        for (int i = 0; i < targetTiles.size(); i++)
        {
            TargetTile targetTile = (TargetTile) targetTiles.get(i);
            if (targetTile.CheckForCollision(CurrentLevel.GetBalls()))
            {
                int levelNumber = CurrentLevel.GetLevelNumber();
                if (LevelHelper.LEVELS.size() > levelNumber)
                {
                    CurrentLevel = null;
                    JOptionPane.showMessageDialog(this, "Level Completed. Try Level " + (levelNumber+1), "Level Completed", JOptionPane.INFORMATION_MESSAGE);
                    CurrentLevel = LevelHelper.LEVELS.get(levelNumber);
                    HeaderLabelText = CurrentLevel.GetTipp() + " | " + Deaths + " Deaths | Level " + CurrentLevel.GetLevelNumber();
                    HeaderLabel.setText(HeaderLabelText);

                    ResetKeyPressed();
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Congratulations! You have beaten the Game!", "Game Finished", JOptionPane.INFORMATION_MESSAGE);
                    Frame.dispatchEvent(new WindowEvent(Frame, WindowEvent.WINDOW_CLOSING));
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
                    Deaths++;
                    HeaderLabelText = CurrentLevel.GetTipp() + " | " + Deaths + " Deaths | Level " + CurrentLevel.GetLevelNumber();
                    HeaderLabel.setText(HeaderLabelText);
                    JOptionPane.showMessageDialog(this, "You died!", "Dead", JOptionPane.INFORMATION_MESSAGE);
                    ResetKeyPressed();
                }
            }
        }
    }

    private void CheckForCollisionWithGateOpenerTile()
    {
        ArrayList<Tile> gateTiles = CurrentLevel.GetTilesByTileState(TileState.gate);
        if (gateTiles.size() != 0)
        {
            for (int i = 0; i < gateTiles.size(); i++)
            {
                GateTile gateTile = (GateTile) gateTiles.get(i);
                ArrayList<GateOpenerTile> gateOpenerTiles = CurrentLevel.GetGateOpenerTilesForOneGate(gateTile.GetID());

                for (int gateOpenerCount = 0; gateOpenerCount < gateOpenerTiles.size(); gateOpenerCount++)
                {
                    GateOpenerTile gateOpenerTile = gateOpenerTiles.get(gateOpenerCount);

                    if (gateOpenerTile.GetGateTile() == null)
                    {
                        gateOpenerTile.SetGateTile(CurrentLevel.GetGateByID(gateOpenerTile.GetID()));
                    }

                    if (gateOpenerTile.CheckForCollision(CurrentLevel.GetBalls()))
                    {
                        break;
                    }
                }

            }
        }
    }

    public void CheckForCollisionWithUnmovableTile()
    {
        ArrayList<Tile> unmovableTiles = CurrentLevel.GetTilesByTileState(TileState.unmovable);
        if (unmovableTiles.size() != 0)
        {
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
}
