package Ball;

import javax.swing.*;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class IdleBall extends Ball {
    private final String IMAGE_PATH = "Textures\\acid_ball_wallhit_small.png";

    public IdleBall(double positionX, double positionY, int id, JPanel gamePanel)
    {
        super(BallState.idle, positionX, positionY, id, gamePanel);
        LoadImage(IMAGE_PATH);
    }

    public void MoveHorizontal(double movement)
    {
        throw new IllegalStateException("Idle Ball can't be moved horizontal");
    }

    public void MoveVertical(double movement)
    {
        throw new IllegalStateException("Idle Ball can't be moved vertical");
    }


    public void Activate()
    {
        ChangeBallType(BallState.controlled);
    }

    public void ChangeBallType(BallState ballState)
    {
        super.ChangeBallType(ballState);
    }
}
