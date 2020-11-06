package com.highloadrussia.battleplanes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class PlayerBulletTest {
    @Test
    public void testConstructor() {
        Player player = new Player(new GameField(1, 1));
        GameField gameField = new GameField(1, 1);
        PlayerBullet actualPlayerBullet = new PlayerBullet(player, gameField);
        assertEquals(1, actualPlayerBullet.getWidth());
        assertEquals(0, actualPlayerBullet.getY());
        assertEquals(2, actualPlayerBullet.getX());
        assertEquals(1, actualPlayerBullet.getHeight());
        assertSame(gameField, actualPlayerBullet.gameField);
        assertFalse(actualPlayerBullet.isDestroyed());
    }

    @Test
    public void testMove() {
        Player player = new Player(new GameField(1, 1));
        PlayerBullet playerBullet = new PlayerBullet(player, new GameField(1, 1));
        playerBullet.move();
        assertEquals(3, playerBullet.getX());
    }

}

