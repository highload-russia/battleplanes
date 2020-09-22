package com.highloadrussia.battleplanes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test
    public void testConstructor() {
        GameField gameField = new GameField(1, 1);
        Player actualPlayer = new Player(gameField);
        assertEquals(0L, actualPlayer.getDistance());
        assertEquals(-2, actualPlayer.getY());
        assertEquals(1, actualPlayer.getX());
        assertEquals(5, actualPlayer.getHeight());
        assertSame(gameField, actualPlayer.getGameField());
        assertFalse(actualPlayer.isMarkedToRemove());
        assertEquals(5, actualPlayer.getLife());
        assertEquals(4, actualPlayer.getWidth());
    }

    @Test
    public void testMoveUp() {
        Player player = new Player(new GameField(1, 1));
        player.setY(3);
        player.moveUp();
        assertEquals(2, player.getY());
    }

    @Test
    public void testMoveDown() {
        Player player = new Player(new GameField(1, 1));
        player.setY(-2147483648);
        player.moveDown();
        assertEquals(-2147483647, player.getY());
    }

    @Test
    public void testSetLife() {
        Player player = new Player(new GameField(1, 1));
        player.setLife(1);
        assertEquals(1, player.getLife());
    }
}

