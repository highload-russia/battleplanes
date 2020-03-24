package com.company.home.entities;

public class Boom extends MovableEntity {

    private final static int DEFAULT_FLYING_DISTANCE = 3;

    private final int distanceOfFlying;

    public Boom(int row, int column, int height, int width) {
        super(row, column,height,width);
        this.getDistanceOfFlying() = DEFAULT_FLYING_DISTANCE;
    }

    public Boom(int row, int column, int height, int width, int distanceOfFlying) {
        super(row, column, height, width);
        this.getDistanceOfFlying() = distanceOfFlying;
    }

    public int getDistanceOfFlying() {
        return distanceOfFlying;
    }
}
