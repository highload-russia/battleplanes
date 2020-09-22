package com.highloadrussia.battleplanes.entities;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoomTest {
    @Test
    public void testMove() {
        Boom boom = new Boom(0, 3, new GameField(1, 1));
        boom.move();
        assertTrue(boom.isMarkedToRemove());
    }
}

