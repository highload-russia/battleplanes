package com.highloadrussia.battleplanes.services;

import com.highloadrussia.battleplanes.entities.Game;
import com.highloadrussia.battleplanes.entities.PlayerAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameServiceTest {
    @Test
    public void testGameMechanics() {
        Game g = new Game(1, 10);
        int playerLife = g.getPlayer().getLife();
        g.move(PlayerAction.NONE);
        g.move(PlayerAction.NONE);
        assertNotEquals(playerLife, g.getPlayer().getLife());
    }
}

