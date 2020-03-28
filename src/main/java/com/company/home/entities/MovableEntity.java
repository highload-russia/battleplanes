package com.company.home.entities;

public abstract class MovableEntity {

    private final int height;
    private final int width;
    private final GameField gameField;

    private int row;
    private int column;
    private boolean markedToRemove;

    public MovableEntity(int row, int column, int height, int width, GameField gameField) {
        this.row = row;
        this.column = column;
        this.height = height;
        this.width = width;
        this.markedToRemove = false;
        this.gameField = gameField;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
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

    public boolean isMarkedToRemove() {
        return markedToRemove;
    }

    public void setMarkedToRemove() {
        this.markedToRemove = true;
    }

    public abstract void move();

    public abstract void markToRemove();

    public boolean isIntersect(MovableEntity movableEntity) {
        return ((this.row + this.height - 1) >= movableEntity.row &&
                this.row <= (movableEntity.row + movableEntity.height) &&
                this.column == movableEntity.column);
    }
}
