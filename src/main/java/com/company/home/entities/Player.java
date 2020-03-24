package com.company.home.entities;

public class Player extends MovableEntity {

    public final static int DEFAULT_PLAYER_LIFES = 5;

    private final static int DEFAULT_PLAYER_HEIGHT = 5;
    private final static int DEFAULT_PLAYER_WIDTH = 4;

    private int lifes;

    public Player(int row, int column, int lifes) {
        super(row, column, DEFAULT_PLAYER_HEIGHT, DEFAULT_PLAYER_WIDTH);
        this.lifes = lifes;
    }

    public void moveUp() {
        if (this.getRow() > 0) {
            this.setRow(this.getRow() - 1);
        }
    }

    public void moveDown(int maxHeight) {
        if (this.getRow() + this.getHeight() < maxHeight) {
            this.setRow(this.getRow() + 1);
        }
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }
}