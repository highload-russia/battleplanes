package com.highloadrussia.battleplanes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class EnemyTest {

    @Test
    public void testConstructor() {
        GameField gameField = new GameField(1, 1);
        Enemy actualEnemy = new Enemy(3, gameField, new Player(new GameField(1, 1)));
        assertEquals(5, actualEnemy.getWidth());
        assertEquals(3, actualEnemy.getY());
        assertEquals(1, actualEnemy.getX());
        assertEquals(3, actualEnemy.getHeight());
        assertFalse(actualEnemy.isDestroyed());
    }

    @Test
    public void testMove() {
        GameField gameField = new GameField(5, 1);
        Enemy enemy = new Enemy(3, gameField, new Player(new GameField(1, 1)));
        //noinspection StatementWithEmptyBody
        do {
        } while (!enemy.move());
        assertEquals(4, enemy.getX());
    }
}

