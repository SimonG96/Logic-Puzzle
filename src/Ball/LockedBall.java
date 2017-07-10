package Ball;

import GamePanel.LevelPanel;

import java.util.ArrayList;


/**
 * Created by s.gockner on 26.06.2017.
 */

public class LockedBall extends Ball {
    public LockedBall(int positionX, int positionY, int id, LevelPanel levelPanel, int[] partnerBallIDs)
    {
        super(BallState.locked, positionX, positionY, id, levelPanel);
        PartnerBallIDs = partnerBallIDs;
        PartnerBalls = null;
    }

    public LockedBall(BallState ballState, int positionX, int positionY, int id, LevelPanel levelPanel, BallState previousBallState, int[] partnerBallIDs)
    {
        super(ballState, positionX, positionY, id, levelPanel, previousBallState);
        PartnerBallIDs = partnerBallIDs;
        PartnerBalls = null;
    }

    private int[] PartnerBallIDs;
    private ArrayList<Ball> PartnerBalls;

    @Override
    public void MoveHorizontal(double movement) {
        super.MoveHorizontal(movement);

        if (PartnerBalls == null)
        {
            PartnerBalls = GetPartnerBalls();
        }

        if (IsActivated())
        {
            for (int i = 0; i < PartnerBalls.size(); i++)
            {
                Ball ball = PartnerBalls.get(i);
                ball.IsControlledByLockedBall = true;
                double correctedMovement = GetCorrectMovementByCheckingCollisionWithBorder(movement, true);
                ball.MoveHorizontal(correctedMovement);
                ball.IsControlledByLockedBall = false;
            }
        }
    }

    @Override
    public void MoveVertical(double movement) {
        super.MoveVertical(movement);

        if (PartnerBalls == null)
        {
            PartnerBalls = GetPartnerBalls();
        }

        if (IsActivated())
        {
            for (int i = 0; i < PartnerBalls.size(); i++)
            {
                Ball ball = PartnerBalls.get(i);
                ball.IsControlledByLockedBall = true;
                double correctedMovement = GetCorrectMovementByCheckingCollisionWithBorder(movement, false);
                ball.MoveVertical(correctedMovement);
                ball.IsControlledByLockedBall = false;
            }
        }
    }

    private ArrayList<Ball> GetPartnerBalls()
    {
        ArrayList<Ball> partnerBalls = new ArrayList<Ball>();
        for (int i = 0; i < PartnerBallIDs.length; i++)
        {
            for (int ballsCounter = 0; ballsCounter < LevelPanel.GetCurrentLevel().GetBalls().length; ballsCounter++)
            {
                if (PartnerBallIDs[i] == LevelPanel.GetCurrentLevel().GetBalls()[ballsCounter].GetID())
                {
                    partnerBalls.add(LevelPanel.GetCurrentLevel().GetBalls()[ballsCounter]);
                }
            }
        }

        return partnerBalls;
    }
}

