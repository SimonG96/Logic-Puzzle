package Ball;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class IdleBall extends Ball {
    public IdleBall(int positionX, int positionY)
    {
        super(BallState.idle, positionX, positionY);
    }

    public void MoveLeft()
    {
        throw new IllegalStateException("Idle Ball can't be moved left");
    }

    public void MoveRight()
    {
        throw new IllegalStateException("Idle Ball can't be moved right");
    }

    public void MoveUp()
    {
        throw new IllegalStateException("Idle Ball can't be moved up");
    }

    public void MoveDown()
    {
        throw new IllegalStateException("Idle Ball can't be moved down");
    }


    public void Activate()
    {

    }

    public void ChangeBallType(BallState ballState)
    {
        super.ChangeBallType(ballState);
    }
}
