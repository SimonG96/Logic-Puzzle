package Ball;

import javax.swing.*;

/**
 * Created by s.gockner on 26.06.2017.
 */
public class AnimatedBall extends Ball {
    private boolean _isAnimated = false;
    private Thread _animationThread = null;

    public AnimatedBall(double positionX, double positionY, int id, AnimationState animationState, JPanel gamePanel)
    {
        super(BallState.animated, positionX, positionY, id, gamePanel);
        AnimationState = animationState;

        _isAnimated = true;
        _animationThread = new Thread(() -> Animate());
        _animationThread.run();
    }

    private AnimationState AnimationState;

    public AnimationState GetAnimationState()
    {
        return AnimationState;
    }

    public void Animate()
    {
        while (_isAnimated)
        {
            switch (AnimationState)
            {
                case horizontal:
                {

                    break;
                }
                case vertical:
                {

                    break;
                }
                case clockwise:
                {

                    break;
                }
                case counterClockwise:
                {

                    break;
                }
                default:
                {
                    _isAnimated = false;
                    throw new IllegalStateException("Illegal Animation State");
                }
            }
        }

        _animationThread.interrupt();
    }

    public void Activate()
    {
        _isAnimated = false;

    }

    public void ChangeBallType(BallState ballState)
    {
        super.ChangeBallType(ballState);
    }
}
