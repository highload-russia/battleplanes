package com.company.home.entities;

public class Player extends MovableEntity {

    private final static int DEFAULT_PLAYER_HEIGHT = 5;
    private final static int DEFAULT_PLAYER_WIDTH = 4;

    public Player(int row, int column, int height, int width) {
        super(row, column, height, width);
    }

    public Player(int row, int column) {
        super(row, column, DEFAULT_PLAYER_HEIGHT, DEFAULT_PLAYER_WIDTH);
    }

    public void moveUp() {
        if (this.getRow() > 0) {
            this.setRow(this.getRow() - 1);
        }
    }

    public void moveDown(int maxHeight) {
        if (this.getRow() + this.getHeight() - 1 < maxHeight) {
            this.setRow(this.getRow() + 1);
        }
    }

}