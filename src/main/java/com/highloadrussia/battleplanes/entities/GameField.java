package com.highloadrussia.battleplanes.entities;

public class GameField {

    private final int width;
    private final int height;

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
