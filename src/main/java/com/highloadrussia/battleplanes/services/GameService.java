package com.highloadrussia.battleplanes.services;

import com.highloadrussia.battleplanes.entities.Game;
import com.highloadrussia.battleplanes.entities.MenuAction;
import com.highloadrussia.battleplanes.gui.Gui;
import com.highloadrussia.battleplanes.util.PropertiesProvider;

import java.io.IOException;

public class GameService {

    private static final int REFRESH_TIME_IN_MILLISECONDS = PropertiesProvider.getIntValue("refresh.time.in.milliseconds");

    public static MenuAction pullMenuAction(Gui gui) throws IOException, InterruptedException {
        MenuAction menuAction = MenuAction.NONE;
        while (menuAction == MenuAction.NONE) {
            menuAction = gui.pullMenuAction();
            Thread.sleep(REFRESH_TIME_IN_MILLISECONDS);
        }
        return menuAction;
    }

    public static void playGame(Gui gui) throws IOException, InterruptedException {

        Game game = new Game(gui.getWidthInColumns(), gui.getHeightInRows());

        while (game.getPlayer().getLife() > 0) {
            game.move(gui.pullPlayerAction());
            gui.redraw(game);
            //noinspection BusyWait
            Thread.sleep(REFRESH_TIME_IN_MILLISECONDS);
        }

        gui.drawGameOver(game.getPlayer().getDistance());
    }
}
