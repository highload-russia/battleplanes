package com.company.home.entities;

public class Opponent extends MovableEntity {

    private final static int DEFAULT_OPPONENT_HEIGHT = 3;
    private final static int DEFAULT_OPPONENT_WIDTH = 5;

    public Opponent(int row, int column, int height, int width) {
        super(row, column, height, width);
    }

    public Opponent(int row, int column) {
        super(row, column, DEFAULT_OPPONENT_HEIGHT, DEFAULT_OPPONENT_WIDTH);
    }
}