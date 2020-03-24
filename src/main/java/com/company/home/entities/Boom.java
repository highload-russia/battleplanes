package com.company.home.entities;

public class Boom extends MovableEntity {

    private final static int DEFAULT_FLYING_DISTANCE = 3;
    private final static int DEFAULT_BOOM_HEIGHT = 1;
    private final static int DEFAULT_BOOM_WIDTH = 1;

    private int distanceOfFlying;

    public Boom(int row, int column) {
        super(row, column, DEFAULT_BOOM_HEIGHT, DEFAULT_BOOM_WIDTH);
        this.distanceOfFlying = DEFAULT_FLYING_DISTANCE;
    }

    public int getDistanceOfFlying() {
        return distanceOfFlying;
    }

    public void setDistanceOfFlying(int distanceOfFlying) {
        this.distanceOfFlying = distanceOfFlying;
    }
}
