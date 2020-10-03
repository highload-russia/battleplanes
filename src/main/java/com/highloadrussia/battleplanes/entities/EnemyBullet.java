package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

public class EnemyBullet extends MovableEntity {

    private final static int BULLET_HEIGHT = PropertiesProvider.getIntValue("bullet.height");
    private final static int BULLET_WIDTH = PropertiesProvider.getIntValue("bullet.width");

    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = PropertiesProvider.getIntValue("bullet.frequency.of.movement.in.timeslots");

    private int timeSlotsAwaitingToMove;

    public EnemyBullet(Enemy enemy, GameField gameField) {
        super(enemy.x - 1, enemy.y + ((enemy.getHeight() - 1) / 2), BULLET_WIDTH, BULLET_HEIGHT, gameField);
        this.timeSlotsAwaitingToMove = 0;
    }

    public void move() {

        if (this.timeSlotsAwaitingToMove == FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS) {
            this.timeSlotsAwaitingToMove = 0;
            x--;
        } else {
            this.timeSlotsAwaitingToMove++;
        }

        if (x == 0) {
            this.destroy();
        }
    }
}
