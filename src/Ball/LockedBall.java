package Ball;

import javax.swing.*;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class LockedBall extends Ball {
    public LockedBall(double positionX, double positionY, int id, JPanel gamePanel)
    {
        super(BallState.locked, positionX, positionY, id, gamePanel);
    }

    public void Activate()
    {

    }

    public void ChangeBallType(BallState ballState)
    {
        super.ChangeBallType(ballState);
    }
}
