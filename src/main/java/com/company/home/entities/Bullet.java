package com.company.home.entities;

public class Bullet extends MovableEntity {

    private final static int DEFAULT_BULLET_HEIGHT = 1;
    private final static int DEFAULT_BULLET_WIDTH = 1;

    public Bullet(Player player) {
        super(player.getRow() + ((player.getHeight() - 1) / 2), 6, DEFAULT_BULLET_HEIGHT, DEFAULT_BULLET_WIDTH);
    }

}
