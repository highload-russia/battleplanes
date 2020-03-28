package com.company.home.entities;

public class Opponent extends MovableEntity {

    private final static int DEFAULT_OPPONENT_HEIGHT = 3;
    private final static int DEFAULT_OPPONENT_WIDTH = 5;

    private final int speed;
    private int energy;

    public Opponent(int row, int column, int speed, GameField gameField) {
        super(row, column, DEFAULT_OPPONENT_HEIGHT, DEFAULT_OPPONENT_WIDTH, gameField);
        this.speed = speed;
        this.energy = 0;
    }

    public void move() {
        if (this.energy == this.speed){
            this.energy = 0;
            this.setColumn(this.getColumn() - 1);
        } else {
            this.energy++;
        }
    }

    public void markToRemove() {
        if (this.getColumn() == 0) {
            this.setMarkedToRemove();
        }
    }
}