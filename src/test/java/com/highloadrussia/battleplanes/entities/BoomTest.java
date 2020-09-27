package com.highloadrussia.battleplanes.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BoomTest {
    @Test
    public void testMove() {
        Boom boom = new Boom(0, 3, new GameField(1, 1));
        boom.move();
        assertTrue(boom.isDestroyed());
    }
}

