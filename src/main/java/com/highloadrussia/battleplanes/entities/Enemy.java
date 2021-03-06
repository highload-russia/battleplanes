package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

import java.util.Random;

import static com.highloadrussia.battleplanes.entities.MovingDirection.LEFT;

public class Enemy extends MovableEntity {

    public final static int ENEMY_HEIGHT = PropertiesProvider.getIntValue("enemy.height");
    public final static int ENEMY_WIDTH = PropertiesProvider.getIntValue("enemy.width");
    public final static int FREQUENCY_OF_ENEMY_APPEARANCE_IN_TIMESLOTS = PropertiesProvider.getIntValue("enemy.frequency.of.appearance.in.timeslots");

    private final static int FREQUENCY_OF_FIRE = PropertiesProvider.getIntValue("enemy.frequency.of.fire");
    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = PropertiesProvider.getIntValue("enemy.frequency.of.movement.in.timeslots");
    private final static int FREQUENCY_OF_CHANGE_DIRECTION_IN_TIMESLOTS = PropertiesProvider.getIntValue("enemy.frequency.of.change.direction.in.timeslots");

    private int timeSlotsAwaitingToChangeDirection;
    private int timeSlotsAwaitingToShoot;

    private final Player player;

    public Enemy(int y, GameField gameField, Player player) {
        super(gameField.getWidth(),
                y,
                ENEMY_WIDTH,
                ENEMY_HEIGHT,
                FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS,
                LEFT,
                gameField);

        Random random = new Random();

        this.timeSlotsAwaitingToChangeDirection = random.nextInt(FREQUENCY_OF_CHANGE_DIRECTION_IN_TIMESLOTS);
        this.timeSlotsAwaitingToShoot = random.nextInt(FREQUENCY_OF_FIRE);
        this.player = player;
    }

    public EnemyBullet tryShoot() {
        if (timeSlotsAwaitingToShoot == FREQUENCY_OF_FIRE) {
            timeSlotsAwaitingToShoot = 0;
            return new EnemyBullet(this, gameField);
        } else {
            timeSlotsAwaitingToShoot++;
            return null;
        }
    }

    @Override
    public boolean move() {
        boolean moved = super.move();

        if (timeSlotsAwaitingToChangeDirection == FREQUENCY_OF_CHANGE_DIRECTION_IN_TIMESLOTS) {
            timeSlotsAwaitingToChangeDirection = 0;
            if (player.y > y) {
                y++;
            } else if (player.y < y) {
                y--;
            }
        } else {
            timeSlotsAwaitingToChangeDirection++;
        }

        return moved;
    }
}