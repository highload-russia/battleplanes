package com.company.home.entities;

public class Opponent extends MovableEntity {

    public final static int FREQUENCY_OF_OPPONENT_APPEARENCE_IN_TIMESLOTS = 100;
    public final static int OPPONENT_HEIGHT = 3;
    public final static int OPPONENT_WIDTH = 5;

    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = 5;

    private int timeSlotsAwaiting;

    public Opponent(int y, GameField gameField) {
        super(gameField.getWidth(), y, OPPONENT_WIDTH, OPPONENT_HEIGHT, gameField);
        this.timeSlotsAwaiting = 0;
    }

    public void move() {
        if (this.timeSlotsAwaiting == FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS) {
            this.timeSlotsAwaiting = 0;
            this.setX(this.getX() - 1);
        } else {
            this.timeSlotsAwaiting++;
        }

        if (this.getX() == 0) {
            this.setMarkedToRemove();
        }
    }
}