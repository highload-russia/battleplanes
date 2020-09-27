package com.highloadrussia.battleplanes.gui;

import com.highloadrussia.battleplanes.entities.*;

import java.io.IOException;
import java.util.List;

public interface Gui {
    int getHeightInRows();

    int getWidthInColumns();

    void init() throws IOException;

    void drawPlayer(Player player);

    void drawOpponents(List<Opponent> opponents);

    void drawBullets(List<MovableEntity> bullets);

    void drawBooms(List<Boom> booms);

    void drawLife(Player player) throws IOException;

    void drawDistance(Player player) throws IOException;

    void drawMenu() throws IOException;

    void drawGameOver(long distance) throws IOException;

    void redraw(Game game) throws IOException;

    void exit() throws IOException;

    PlayerAction pullPlayerAction() throws IOException;

    MenuAction pullMenuAction() throws IOException;
}
