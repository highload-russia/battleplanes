package com.highloadrussia.battleplanes.entities;

public abstract class MovableEntity {

    private final int width;
    private final int height;
    protected final GameField gameField;

    protected int y;
    protected int x;
    private boolean destroyed;

    public MovableEntity(int x, int y, int width, int height, GameField gameField) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.destroyed = false;
        this.gameField = gameField;
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

    public GameField getGameField() {
        return gameField;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        this.destroyed = true;
    }

    public boolean isIntersect(MovableEntity movableEntity) {
        return ((this.y + this.height - 1) >= movableEntity.y &&
                this.y <= (movableEntity.y + movableEntity.height) &&
                this.x == movableEntity.x);
    }

    public abstract void move();
}
