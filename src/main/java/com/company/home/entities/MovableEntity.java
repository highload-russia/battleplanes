package com.company.home.entities;

public abstract class MovableEntity {

    private final int width;
    private final int height;
    private final GameField gameField;

    private int y;
    private int x;
    private boolean markedToRemove;

    public MovableEntity(int x, int y, int width, int height, GameField gameField) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.markedToRemove = false;
        this.gameField = gameField;
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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isMarkedToRemove() {
        return markedToRemove;
    }

    public void setMarkedToRemove() {
        this.markedToRemove = true;
    }

    public boolean isIntersect(MovableEntity movableEntity) {
        return ((this.y + this.height - 1) >= movableEntity.y &&
                this.y <= (movableEntity.y + movableEntity.height) &&
                this.x == movableEntity.x);
    }

    public abstract void move();
}
