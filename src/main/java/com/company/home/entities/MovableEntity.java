package com.company.home.entities;

public abstract class MovableEntity {

    private int row;
    private int column;
    private int height;
    private int width;

    public MovableEntity(int row, int column, int height, int width) {
        this.row = row;
        this.column = column;
        this.height = height;
        this.width = width;
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

    public boolean isIntersect(MovableEntity movableEntity) {
        return ((this.row + this.height - 1) >= movableEntity.row &&
                this.row <= (movableEntity.row + movableEntity.height) &&
                this.column == movableEntity.column);
    }
}
