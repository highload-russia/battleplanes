package com.highloadrussia.battleplanes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BoomTest {
    @Test
    public void testMove() {
        Boom boom = new Boom(3, 3, new GameField(1, 1));
        int boomX = boom.getX();
        //noinspection StatementWithEmptyBody
        do {
        } while (!boom.move());
        assertEquals(--boomX, boom.getX());
    }
}

