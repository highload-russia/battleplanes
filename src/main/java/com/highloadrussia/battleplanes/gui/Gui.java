package com.highloadrussia.battleplanes.gui;

import com.highloadrussia.battleplanes.entities.*;

import java.io.IOException;

public interface Gui {
    int getHeightInRows();

    int getWidthInColumns();

    void init() throws IOException;

    void drawMenu() throws IOException;

    void drawGameOver(long distance) throws IOException;

    void redraw(Game game) throws IOException;

    void exit() throws IOException;

    PlayerAction pullPlayerAction() throws IOException;

    MenuAction pullMenuAction() throws IOException;
}
