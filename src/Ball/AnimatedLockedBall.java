package Ball;

import GamePanel.GamePanel;

import java.util.ArrayList;

/**
 * Created by Acer on 09.07.2017.
 */
public class AnimatedLockedBall extends AnimatedBall {
    public AnimatedLockedBall(int positionX, int positionY, int id, AnimationState currentAnimationState, GamePanel gamePanel, int[] partnerBallIDs)
    {
        super(BallState.animated_locked, positionX, positionY, id, currentAnimationState, gamePanel);

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
            for (int ballsCounter = 0; ballsCounter < GamePanel.GetCurrentLevel().GetBalls().length; ballsCounter++)
            {
                if (PartnerBallIDs[i] == GamePanel.GetCurrentLevel().GetBalls()[ballsCounter].GetID())
                {
                    partnerBalls.add(GamePanel.GetCurrentLevel().GetBalls()[ballsCounter]);
                }
            }
        }

        return partnerBalls;
    }
}
