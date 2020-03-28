package com.company.home.entities;

public class Player extends MovableEntity {

    private final static int PLAYER_LIFE = 5;
    private final static int PLAYER_X_POSITION = 1;
    private final static int PLAYER_HEIGHT = 5;
    private final static int PLAYER_WIDTH = 4;

    private int life;

    public Player(GameField gameField) {
        super(PLAYER_X_POSITION, (gameField.getHeight() / 2) - (PLAYER_HEIGHT / 2), PLAYER_WIDTH, PLAYER_HEIGHT, gameField);
        this.life = PLAYER_LIFE;
    }

    public void moveUp() {
        if (this.getY() > 0) {
            this.setY(this.getY() - 1);
        }
    }

    public void moveDown() {
        if (this.getY() + this.getHeight() < this.getGameField().getHeight()) {
            this.setY(this.getY() + 1);
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void move() {
        // no implementation needed for Player
    }
}