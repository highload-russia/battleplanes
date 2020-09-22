package com.highloadrussia.battleplanes.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EnemyBulletTest {
    @Test
    public void testConstructor() {
        GameField gameField = new GameField(1, 1);
        Opponent opponent = new Opponent(3, gameField, new Player(new GameField(1, 1)));
        GameField gameField1 = new GameField(1, 1);
        EnemyBullet actualEnemyBullet = new EnemyBullet(opponent, gameField1);
        assertEquals(1, actualEnemyBullet.getWidth());
        assertEquals(4, actualEnemyBullet.getY());
        assertEquals(0, actualEnemyBullet.getX());
        assertEquals(1, actualEnemyBullet.getHeight());
        assertSame(gameField1, actualEnemyBullet.getGameField());
        assertFalse(actualEnemyBullet.isMarkedToRemove());
    }

    @Test
    public void testMove() {
        GameField gameField = new GameField(1, 1);
        Opponent opponent = new Opponent(3, gameField, new Player(new GameField(1, 1)));
        EnemyBullet enemyBullet = new EnemyBullet(opponent, new GameField(1, 1));
        enemyBullet.move();
        assertTrue(enemyBullet.isMarkedToRemove());
    }
}

