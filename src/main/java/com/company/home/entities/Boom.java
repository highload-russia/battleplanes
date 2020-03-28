package com.company.home.entities;

public class Boom extends MovableEntity {

    private final static int DEFAULT_FLYING_DISTANCE = 3;
    private final static int DEFAULT_BOOM_HEIGHT = 1;
    private final static int DEFAULT_BOOM_WIDTH = 1;

    private final int speed;

    private int distanceOfFlying;
    private int energy;

    public Boom(int row, int column, int speed, GameField gameField) {
        super(row, column, DEFAULT_BOOM_HEIGHT, DEFAULT_BOOM_WIDTH, gameField);
        this.distanceOfFlying = DEFAULT_FLYING_DISTANCE;
        this.speed = speed;
    }

    public int getDistanceOfFlying() {
        return distanceOfFlying;
    }

    public void move() {
        if (this.energy == this.speed) {
            this.energy = 0;
            this.setColumn(this.getColumn() - 1);
            this.distanceOfFlying--;
        } else {
            this.energy++;
        }
    }

    public void markToRemove() {
        if (this.getColumn() == 0 || this.getDistanceOfFlying() == 0) {
            this.setMarkedToRemove();
        }
    }
}
