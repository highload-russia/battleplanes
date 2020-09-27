package com.highloadrussia.battleplanes.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EnemyBulletTest {
    @Test
    public void testConstructor() {
        GameField gameField = new GameField(1, 1);
        Enemy enemy = new Enemy(3, gameField, new Player(new GameField(1, 1)));
        GameField gameField1 = new GameField(1, 1);
        EnemyBullet actualEnemyBullet = new EnemyBullet(enemy, gameField1);
        assertEquals(1, actualEnemyBullet.getWidth());
        assertEquals(4, actualEnemyBullet.getY());
        assertEquals(0, actualEnemyBullet.getX());
        assertEquals(1, actualEnemyBullet.getHeight());
        assertSame(gameField1, actualEnemyBullet.getGameField());
        assertFalse(actualEnemyBullet.isDestroyed());
    }

    @Test
    public void testMove() {
        GameField gameField = new GameField(1, 1);
        Enemy enemy = new Enemy(3, gameField, new Player(new GameField(1, 1)));
        EnemyBullet enemyBullet = new EnemyBullet(enemy, new GameField(1, 1));
        enemyBullet.move();
        assertTrue(enemyBullet.isDestroyed());
    }
}

