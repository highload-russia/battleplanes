package com.company.home.entities;

public abstract class MovableEntity {

    private int row;
    private int column;
    private int height;
    private int width;

    public MovableEntity(int row, int column, int height, int width) {
        this.getRow() = row;
        this.getColumn() = column;
        this.height = height;
        this.width = width;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.getRow() = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.getColumn() = column;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isIntersect(MovableEntity movableEntity) {
        int tw = this.width;
        int th = this.height;
        int rw = movableEntity.width;
        int rh = movableEntity.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = this.getRow();
        int ty = this.getColumn();
        int rx = movableEntity.getRow();
        int ry = movableEntity.getColumn();
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;

        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }
}
