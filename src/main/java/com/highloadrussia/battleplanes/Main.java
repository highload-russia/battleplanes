package com.highloadrussia.battleplanes;

import java.io.IOException;

import com.highloadrussia.battleplanes.gui.Gui;
import com.highloadrussia.battleplanes.gui.LanternaGui;
import com.highloadrussia.battleplanes.entities.MenuAction;
import com.highloadrussia.battleplanes.services.GameService;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Gui gui = new LanternaGui();
        gui.init();
        gui.drawMenu();

        while (GameService.pullMenuAction(gui) != MenuAction.EXIT) {
            GameService.playGame(gui);
        }

        gui.exit();
    }
}
