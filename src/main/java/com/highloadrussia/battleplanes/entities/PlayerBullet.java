package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

import static com.highloadrussia.battleplanes.entities.MovingDirection.RIGHT;

public class PlayerBullet extends MovableEntity {

    private final static int BULLET_HEIGHT = PropertiesProvider.getIntValue("bullet.height");
    private final static int BULLET_WIDTH = PropertiesProvider.getIntValue("bullet.width");
    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = PropertiesProvider.getIntValue("player.bullet.frequency.of.movement.in.timeslots");

    public PlayerBullet(Player player, GameField gameField) {
        super(player.x + 1,
                player.y + ((player.getHeight() - 1) / 2),
                BULLET_WIDTH,
                BULLET_HEIGHT,
                FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS,
                RIGHT,
                gameField);
    }
}
