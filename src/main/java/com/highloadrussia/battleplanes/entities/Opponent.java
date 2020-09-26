package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

import java.util.Random;

public class Opponent extends MovableEntity {

    public final static int OPPONENT_HEIGHT = PropertiesProvider.getIntValue("opponent.height");
    public final static int OPPONENT_WIDTH = PropertiesProvider.getIntValue("opponent.width");

    public final static int FREQUENCY_OF_OPPONENT_APPEARANCE_IN_TIMESLOTS = PropertiesProvider.getIntValue("opponent.frequency.of.appearance.in.timeslots");
    public final static int FREQUENCY_OF_FIRE = PropertiesProvider.getIntValue("opponent.frequency.of.fire");

    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = PropertiesProvider.getIntValue("opponent.frequency.of.movement.in.timeslots");
    private final static int FREQUENCY_OF_CHANGE_DIRECTION_IN_TIMESLOTS = PropertiesProvider.getIntValue("opponent.frequency.of.change.direction.in.timeslots");

    private int timeSlotsAwaitingToMove;
    private int timeSlotsAwaitingToChangeDirection;
    private int timeSlotsAwaitingToShoot;
    private final Player player;
    private boolean readyToShoot;

    public Opponent(int y, GameField gameField, Player player) {
        super(gameField.getWidth(), y, OPPONENT_WIDTH, OPPONENT_HEIGHT, gameField);

        Random random = new Random();

        this.timeSlotsAwaitingToMove = 0;
        this.timeSlotsAwaitingToChangeDirection = random.nextInt(FREQUENCY_OF_CHANGE_DIRECTION_IN_TIMESLOTS);
        this.timeSlotsAwaitingToShoot = random.nextInt(FREQUENCY_OF_FIRE);
        this.readyToShoot = false;
        this.player = player;
    }

    public boolean isReadyToShoot() {
        return readyToShoot;
    }

    public void setReadyToShoot(boolean readyToShoot) {
        this.readyToShoot = readyToShoot;
    }

    public void move() {

        if (this.timeSlotsAwaitingToShoot == FREQUENCY_OF_FIRE) {
            this.readyToShoot = true;
            this.timeSlotsAwaitingToShoot = 0;
        } else {
            this.timeSlotsAwaitingToShoot++;
        }

        if (this.timeSlotsAwaitingToMove == FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS) {
            this.timeSlotsAwaitingToMove = 0;
            this.setX(this.getX() - 1);
            if (this.timeSlotsAwaitingToChangeDirection == FREQUENCY_OF_CHANGE_DIRECTION_IN_TIMESLOTS) {
                this.timeSlotsAwaitingToChangeDirection = 0;
                if (player.getY() > this.getY()) {
                    this.setY(this.getY() + 1);
                } else if (player.getY() < this.getY()) {
                    this.setY(this.getY() - 1);
                }
            } else {
                this.timeSlotsAwaitingToChangeDirection++;
            }
        } else {
            this.timeSlotsAwaitingToMove++;
        }

        if (this.getX() == 0) {
            this.setMarkedToRemove();
        }
    }
}