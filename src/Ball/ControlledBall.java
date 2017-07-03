package Ball;

import javax.swing.*;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class ControlledBall extends Ball{

    private final String IMAGE_PATH = "Textures\\acid_ball_standard_small.png";

    public ControlledBall(double positionX, double positionY, int id, BallState defaultBallState, JPanel gamePanel)
    {
        super(BallState.controlled, positionX, positionY, id, gamePanel);

        Instance.PreviousBallState = defaultBallState;

        LoadImage(IMAGE_PATH);
    }

    public void Activate()
    {
        throw new IllegalStateException("Ball is already activated");
    }

    public void Deactivate()
    {
        ChangeBallType(Instance.PreviousBallState);
    }

    public void ChangeBallType(BallState ballState)
    {
        super.ChangeBallType(ballState);
    }
}
