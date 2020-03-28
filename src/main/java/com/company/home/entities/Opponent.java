package com.company.home.entities;

public class Opponent extends MovableEntity {

    public final static int FREQUENCY_OF_OPPONENT_APPEARENCE_IN_TIMESLOTS = 130;
    public final static int FREQUENCY_OF_FIRE = 200;
    public final static int OPPONENT_HEIGHT = 3;
    public final static int OPPONENT_WIDTH = 5;

    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = 5;

    private int timeSlotsAwaitingToMove;
    private int timeSlotsAwaitingToShoot;
    private boolean readyToShoot;

    public Opponent(int y, GameField gameField) {
        super(gameField.getWidth(), y, OPPONENT_WIDTH, OPPONENT_HEIGHT, gameField);
        this.timeSlotsAwaitingToMove = 0;
        this.timeSlotsAwaitingToShoot = 0;
        this.readyToShoot = false;
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
        } else {
            this.timeSlotsAwaitingToMove++;
        }

        if (this.getX() == 0) {
            this.setMarkedToRemove();
        }
    }
}