package com.highloadrussia.battleplanes.entities;

public class EnemyBullet extends MovableEntity {

    private final static int BULLET_HEIGHT = 1;
    private final static int BULLET_WIDTH = 1;
    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = 2;

    private int timeSlotsAwaitingToMove;

    public EnemyBullet(Opponent opponent, GameField gameField) {
        super(opponent.getX() - 1, opponent.getY() + ((opponent.getHeight() - 1) / 2), BULLET_WIDTH, BULLET_HEIGHT, gameField);
        this.timeSlotsAwaitingToMove = 0;
    }

    public void move() {

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
