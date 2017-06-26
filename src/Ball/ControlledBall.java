package Ball;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class ControlledBall extends Ball{
    public ControlledBall(int positionX, int positionY)
    {
        super(BallState.controlled, positionX, positionY);
    }

    public void Activate()
    {
        throw new IllegalStateException("Ball.Ball is already activated");
    }

    public void Deactivate()
    {

    }

    public void ChangeBallType(BallState ballState)
    {
        super.ChangeBallType(ballState);
    }
}
