package com.company.home.entities;

public class Player extends MovableEntity {

    public final static int DEFAULT_PLAYER_LIFE = 5;

    private final static int DEFAULT_PLAYER_HEIGHT = 5;
    private final static int DEFAULT_PLAYER_WIDTH = 4;

    private int lifes;

    public Player(int lifes, GameField gameField) {
        super((gameField.getHeight() / 2) - (DEFAULT_PLAYER_HEIGHT / 2), 1, DEFAULT_PLAYER_HEIGHT, DEFAULT_PLAYER_WIDTH, gameField);
        this.lifes = lifes;
    }

    public void moveUp() {
        if (this.getRow() > 0) {
            this.setRow(this.getRow() - 1);
        }
    }

    public void moveDown() {
        if (this.getRow() + this.getHeight() < this.getGameField().getHeight()) {
            this.setRow(this.getRow() + 1);
        }
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public void move() {
    }

    @Override
    public void markToRemove() {

    }
}