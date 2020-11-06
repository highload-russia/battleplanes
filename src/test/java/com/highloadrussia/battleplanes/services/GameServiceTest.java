package com.highloadrussia.battleplanes.services;

import com.highloadrussia.battleplanes.entities.Game;
import com.highloadrussia.battleplanes.entities.PlayerAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameServiceTest {
    @Test
    public void testProcessInteractions() {
        Game g = new Game(1, 10);
        g.move(PlayerAction.NONE);
        assertEquals(4, g.getPlayer().getLife());
    }
}

