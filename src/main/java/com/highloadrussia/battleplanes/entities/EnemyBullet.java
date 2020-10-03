package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

public class EnemyBullet extends MovableEntity {

    private final static int BULLET_HEIGHT = PropertiesProvider.getIntValue("bullet.height");
    private final static int BULLET_WIDTH = PropertiesProvider.getIntValue("bullet.width");

    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = PropertiesProvider.getIntValue("enemy.bullet.frequency.of.movement.in.timeslots");

    public EnemyBullet(Enemy enemy, GameField gameField) {
        super(enemy.x - 1,
                enemy.y + ((enemy.getHeight() - 1) / 2),
                BULLET_WIDTH,
                BULLET_HEIGHT,
                FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS,
                MovingDirection.LEFT,
                gameField);
    }
}
