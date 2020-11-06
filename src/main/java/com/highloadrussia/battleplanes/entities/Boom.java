package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

import static com.highloadrussia.battleplanes.entities.MovingDirection.LEFT;

public class Boom extends MovableEntity {

    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = PropertiesProvider.getIntValue("boom.frequency.of.movement.in.timeslots");
    private final static int NUMBER_OF_MOVEMENTS = PropertiesProvider.getIntValue("number.of.movements");
    private final static int BOOM_HEIGHT = PropertiesProvider.getIntValue("boom.height");
    private final static int BOOM_WIDTH = PropertiesProvider.getIntValue("boom.width");

    private int numberOfAvailableMovements;

    public Boom(int x, int y, GameField gameField) {
        super(x, y, BOOM_WIDTH, BOOM_HEIGHT, FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS, LEFT, gameField);
        this.numberOfAvailableMovements = NUMBER_OF_MOVEMENTS;
    }

    public int getNumberOfAvailableMovements() {
        return numberOfAvailableMovements;
    }

    @Override
    public boolean move() {
        boolean moved = super.move();

        if (numberOfAvailableMovements > 0 && moved) {
            numberOfAvailableMovements--;
        }

        return moved;
    }
}
