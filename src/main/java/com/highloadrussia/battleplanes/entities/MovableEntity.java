package com.highloadrussia.battleplanes.entities;

public abstract class MovableEntity {

    private final int frequencyOfMovementInTimeslots;
    private final MovingDirection direction;

    private boolean destroyed;
    private int timeSlotsAwaitingToMove;

    protected final GameField gameField;
    protected int y;
    protected int x;
    protected final int width;
    protected final int height;

    public MovableEntity(int x,
                         int y,
                         int width,
                         int height,
                         int frequencyOfMovementInTimeslots,
                         MovingDirection direction,
                         GameField gameField) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.frequencyOfMovementInTimeslots = frequencyOfMovementInTimeslots;
        this.direction = direction;
        this.destroyed = false;
        this.gameField = gameField;
        this.timeSlotsAwaitingToMove = 0;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        this.destroyed = true;
    }

    public boolean isIntersect(MovableEntity movableEntity) {
        return ((y + height - 1) >= movableEntity.y &&
                y <= (movableEntity.y + movableEntity.height) &&
                x == movableEntity.x);
    }

    public boolean move() {
        if (timeSlotsAwaitingToMove == frequencyOfMovementInTimeslots) {
            timeSlotsAwaitingToMove = 0;
            switch (direction) {
                case RIGHT:
                    x++;
                    break;
                case LEFT:
                    x--;
                    break;
                case NONE: // nothing to do
                    break;
            }
            return true;
        } else {
            timeSlotsAwaitingToMove++;
            return false;
        }
    }
}
