package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

public class PlayerBullet extends MovableEntity {

    private final static int BULLET_HEIGHT = PropertiesProvider.getIntValue("bullet.height");
    private final static int BULLET_WIDTH = PropertiesProvider.getIntValue("bullet.width");

    public PlayerBullet(Player player, GameField gameField) {
        super(player.getX() + 1, player.getY() + ((player.getHeight() - 1) / 2), BULLET_WIDTH, BULLET_HEIGHT, gameField);
    }

    public void move() {
        this.setX(this.getX() + 1);

        if (this.getX() == this.getGameField().getWidth()) {
            this.setMarkedToRemove();
        }
    }
}
