
package Ball;

import GamePanel.LevelPanel;


/**
 * Created by s.gockner on 26.06.2017.
 */

public class AnimatedBall extends Ball {
    protected boolean _isAnimated = false;

    public AnimatedBall(BallState ballState, int positionX, int positionY, int id, AnimationState currentAnimationState, LevelPanel levelPanel) {
        super(ballState, positionX, positionY, id, levelPanel);
        CurrentAnimationState = currentAnimationState;

        SetCurrentAnimationDirection(currentAnimationState);

        _isAnimated = true;
    }

    public AnimatedBall(BallState ballState, int positionX, int positionY, int id, LevelPanel levelPanel, BallState previousBallState, AnimationState currentAnimationState)
    {
        super(ballState, positionX, positionY, id, levelPanel, previousBallState);
        CurrentAnimationState = currentAnimationState;

        SetCurrentAnimationDirection(currentAnimationState);

        _isAnimated = false;
    }

    protected AnimationState CurrentAnimationState;

    public AnimationState GetAnimationState()
    {
        return CurrentAnimationState;
    }

    protected AnimationDirection CurrentAnimationDirection;

    public AnimationDirection GetCurrentAnimationDirection()
    {
        return CurrentAnimationDirection;
    }

    public void Animate() {
        if (_isAnimated)
        {
            switch (CurrentAnimationState)
            {
                case horizontal:
                {
                    if (CurrentAnimationDirection == AnimationDirection.right)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(LevelPanel.MOVEMENT_SPEED_HORIZONTAL, true) != 0)
                        {
                            MoveHorizontal(LevelPanel.MOVEMENT_SPEED_HORIZONTAL);
                        }
                        else //movement == 0 -> Hit a border
                        {
                            CurrentAnimationDirection = AnimationDirection.left;
                        }
                    }
                    else if (CurrentAnimationDirection == AnimationDirection.left)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(-LevelPanel.MOVEMENT_SPEED_HORIZONTAL, true) != 0)
                        {
                            MoveHorizontal(-LevelPanel.MOVEMENT_SPEED_HORIZONTAL);
                        }
                        else
                        {
                            CurrentAnimationDirection = AnimationDirection.right;
                        }
                    }

                    break;
                }
                case vertical:
                {
                    if (CurrentAnimationDirection == AnimationDirection.up)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(-LevelPanel.MOVEMENT_SPEED_VERTICAL, false) != 0)
                        {
                            MoveVertical(-LevelPanel.MOVEMENT_SPEED_VERTICAL);
                        }
                        else //movement == 0 -> Hit a border
                        {
                            CurrentAnimationDirection = AnimationDirection.down;
                        }
                    }
                    else if (CurrentAnimationDirection == AnimationDirection.down)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(LevelPanel.MOVEMENT_SPEED_VERTICAL, false) != 0)
                        {
                            MoveVertical(LevelPanel.MOVEMENT_SPEED_VERTICAL);
                        }
                        else
                        {
                            CurrentAnimationDirection = AnimationDirection.up;
                        }
                    }

                    break;
                }
                case clockwise:
                {
                    if (CurrentAnimationDirection == AnimationDirection.right)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(LevelPanel.MOVEMENT_SPEED_HORIZONTAL, true) != 0)
                        {
                            MoveHorizontal(LevelPanel.MOVEMENT_SPEED_HORIZONTAL);
                        }
                        else //movement == 0 -> Hit a border
                        {
                            CurrentAnimationDirection = AnimationDirection.down;
                        }
                    }
                    else if (CurrentAnimationDirection == AnimationDirection.left)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(-LevelPanel.MOVEMENT_SPEED_HORIZONTAL, true) != 0)
                        {
                            MoveHorizontal(-LevelPanel.MOVEMENT_SPEED_HORIZONTAL);
                        }
                        else
                        {
                            CurrentAnimationDirection = AnimationDirection.up;
                        }
                    }
                    else if (CurrentAnimationDirection == AnimationDirection.up)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(-LevelPanel.MOVEMENT_SPEED_VERTICAL, false) != 0)
                        {
                            MoveVertical(-LevelPanel.MOVEMENT_SPEED_VERTICAL);
                        }
                        else //movement == 0 -> Hit a border
                        {
                            CurrentAnimationDirection = AnimationDirection.right;
                        }
                    }
                    else if (CurrentAnimationDirection == AnimationDirection.down)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(LevelPanel.MOVEMENT_SPEED_VERTICAL, false) != 0)
                        {
                            MoveVertical(LevelPanel.MOVEMENT_SPEED_VERTICAL);
                        }
                        else
                        {
                            CurrentAnimationDirection = AnimationDirection.left;
                        }
                    }

                    break;
                }
                case counterClockwise:
                {
                    if (CurrentAnimationDirection == AnimationDirection.right)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(LevelPanel.MOVEMENT_SPEED_HORIZONTAL, true) != 0)
                        {
                            MoveHorizontal(LevelPanel.MOVEMENT_SPEED_HORIZONTAL);
                        }
                        else //movement == 0 -> Hit a border
                        {
                            CurrentAnimationDirection = AnimationDirection.up;
                        }
                    }
                    else if (CurrentAnimationDirection == AnimationDirection.left)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(-LevelPanel.MOVEMENT_SPEED_HORIZONTAL, true) != 0)
                        {
                            MoveHorizontal(-LevelPanel.MOVEMENT_SPEED_HORIZONTAL);
                        }
                        else
                        {
                            CurrentAnimationDirection = AnimationDirection.down;
                        }
                    }
                    else if (CurrentAnimationDirection == AnimationDirection.up)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(-LevelPanel.MOVEMENT_SPEED_VERTICAL, false) != 0)
                        {
                            MoveVertical(-LevelPanel.MOVEMENT_SPEED_VERTICAL);
                        }
                        else //movement == 0 -> Hit a border
                        {
                            CurrentAnimationDirection = AnimationDirection.left;
                        }
                    }
                    else if (CurrentAnimationDirection == AnimationDirection.down)
                    {
                        if (GetCorrectMovementByCheckingCollisionWithBorder(LevelPanel.MOVEMENT_SPEED_VERTICAL, false) != 0)
                        {
                            MoveVertical(LevelPanel.MOVEMENT_SPEED_VERTICAL);
                        }
                        else
                        {
                            CurrentAnimationDirection = AnimationDirection.right;
                        }
                    }

                    break;
                }
                default:
                {
                    _isAnimated = false;
                    throw new IllegalStateException("Illegal Animation State");
                }
            }
        }
    }

    protected void SetCurrentAnimationDirection(AnimationState animationState)
    {
        switch (animationState) {
            case horizontal: {
                CurrentAnimationDirection = AnimationDirection.right;
                break;
            }
            case vertical: {
                CurrentAnimationDirection = AnimationDirection.down;
                break;
            }
            case clockwise: {
                CurrentAnimationDirection = AnimationDirection.right;
                break;
            }
            case counterClockwise: {
                CurrentAnimationDirection= AnimationDirection.left;
                break;
            }
        }
    }

    @Override
    public void Activate() {
        super.Activate();
        _isAnimated = false;
    }

    @Override
    public void Deactivate() {
        super.Deactivate();
        _isAnimated = true;
    }
}

