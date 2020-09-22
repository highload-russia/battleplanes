package com.highloadrussia.battleplanes.services;

import static org.junit.Assert.assertEquals;

import com.highloadrussia.battleplanes.entities.GameField;
import com.highloadrussia.battleplanes.entities.MovableEntity;
import com.highloadrussia.battleplanes.entities.Player;

import java.util.ArrayList;

import org.junit.Test;

public class GameServiceTest {
    @Test
    public void testProcessInteractions() {
        Player e = new Player(new GameField(1, 1));
        ArrayList<MovableEntity> movableEntityList = new ArrayList<>();
        movableEntityList.add(e);
        Player player = new Player(new GameField(1, 1));
        GameField gameField = new GameField(1, 1);
        ArrayList<MovableEntity> bullets = new ArrayList<>();
        GameService.processInteractions(movableEntityList, bullets, new ArrayList<>(), player, gameField);
        assertEquals(4, player.getLife());
    }
}

