package Ball;

/**
 * Created by s.gockner on 26.06.2017.
 */
public abstract class Ball {

    public Ball(BallState ballState, int positionX, int positionY)
    {
        BallState = ballState;
        PreviousBallState = null;
        StartingPositionX = positionX;
        PositionX = positionX;
        StartingPositionY = positionY;
        PositionY = positionY;
        ID = 0; //TODO: Set ID to next available ID
    }

    protected final int ID;
    protected BallState BallState;
    protected BallState PreviousBallState;
    protected int PositionX;
    protected int PositionY;
    protected final int StartingPositionX;
    protected final int StartingPositionY;

    public int GetID()
    {
        return ID;
    }

    public BallState GetState()
    {
        return BallState;
    }

    public BallState GetPreviousBallState()
    {
        return PreviousBallState;
    }

    public int GetPositionX()
    {
        return PositionX;
    }

    public int GetPositionY()
    {
        return PositionY;
    }

    public int GetStartingPositionX()
    {
        return StartingPositionX;
    }

    public int GetStartingPositionY()
    {
        return StartingPositionY;
    }

    public boolean IsActivated()
    {
        return BallState == BallState.controlled;
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

    public void ChangeBallType(BallState ballState)
    {
        PreviousBallState = BallState;
        BallState = ballState;
    }

    abstract void Activate();
}
