package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

public class Boom extends MovableEntity {

    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = PropertiesProvider.getIntValue("boom.frequency.of.movement.in.timeslots");
    private final static int NUMBER_OF_MOVEMENTS = PropertiesProvider.getIntValue("number.of.movements");
    private final static int BOOM_HEIGHT = PropertiesProvider.getIntValue("boom.height");
    private final static int BOOM_WIDTH = PropertiesProvider.getIntValue("boom.width");

    private int numberOfAvailableMovements;
    private int timeSlotsAwaiting;

    public Boom(int x, int y, GameField gameField) {
        super(x, y, BOOM_WIDTH, BOOM_HEIGHT, gameField);
        this.numberOfAvailableMovements = NUMBER_OF_MOVEMENTS;
    }

    public int getNumberOfAvailableMovements() {
        return numberOfAvailableMovements;
    }

    public void move() {
        if (this.timeSlotsAwaiting == FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS) {
            this.timeSlotsAwaiting = 0;
            this.setX(this.getX() - 1);
            this.numberOfAvailableMovements--;
        } else {
            this.timeSlotsAwaiting++;
        }

        if (this.getX() == 0 || this.getNumberOfAvailableMovements() == 0) {
            this.destroy();
        }
    }
}
