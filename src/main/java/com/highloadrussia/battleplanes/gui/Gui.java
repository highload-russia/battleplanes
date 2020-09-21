package com.highloadrussia.battleplanes.gui;

import com.highloadrussia.battleplanes.entities.MenuAction;
import com.highloadrussia.battleplanes.entities.MovableEntity;
import com.highloadrussia.battleplanes.entities.Player;
import com.highloadrussia.battleplanes.entities.PlayerAction;

import java.io.IOException;
import java.util.List;

public interface Gui {
    int getHeightInRows();

    int getWidthInColumns();

    void init() throws IOException;

    void drawPlayer(Player player);

    void drawOpponents(List<MovableEntity> opponents);

    void drawBullets(List<MovableEntity> bullets);

    void drawBooms(List<MovableEntity> booms);

    void drawLife(Player player) throws IOException;

    void drawDistance(Player player) throws IOException;

    void drawMenu() throws IOException;

    void drawGameOver(Player player) throws IOException;

    void redraw(Player player,
                List<MovableEntity> opponents,
                List<MovableEntity> bullets,
                List<MovableEntity> booms) throws IOException;

    void exit() throws IOException;

    PlayerAction pullUserAction() throws IOException;

    MenuAction pullMenuAction() throws IOException;
}
