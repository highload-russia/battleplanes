package com.company.home.entities;

public class Bullet extends MovableEntity {

    private final static int DEFAULT_BULLET_HEIGHT = 1;
    private final static int DEFAULT_BULLET_WIDTH = 1;

    public Bullet(Player player, GameField gameField) {
        super(player.getRow() + ((player.getHeight() - 1) / 2), 6, DEFAULT_BULLET_HEIGHT, DEFAULT_BULLET_WIDTH, gameField);
    }

    public void move() {
        this.setColumn(this.getColumn() + 1);
    }

    public void markToRemove() {
        if (this.getColumn() == this.getGameField().getWidth()) {
            this.setMarkedToRemove();
        }
    }
}
