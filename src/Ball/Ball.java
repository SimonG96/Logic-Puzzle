package Ball;

/**
 * Created by s.gockner on 26.06.2017.
 */
public abstract class Ball {

    public Ball(BallState ballState, int positionX, int positionY)
    {
        BallState = ballState;
        PositionX = positionX;
        PositionY = positionY;
        ID = 0; //TODO: Set ID to next available ID
    }

    private final int ID;
    private BallState BallState;
    private int PositionX;
    private int PositionY;

    public int GetID()
    {
        return ID;
    }

    public BallState GetState()
    {
        return BallState;
    }

    public int GetPositionX()
    {
        return PositionX;
    }

    public int GetPositionY()
    {
        return PositionY;
    }

    public boolean IsActivated()
    {
        if (BallState == BallState.controlled)
        {
            return true;
        }

        return false;
    }

    public void MoveLeft()
    {

    }

    public void MoveRight()
    {

    }

    public void MoveUp()
    {

    }

    public void MoveDown()
    {

    }

    abstract void Activate();
    abstract void ChangeBallType();
    abstract void ChangeBallType(BallState ballState);
}
