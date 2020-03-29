package com.company.home;

import java.io.IOException;

import com.company.home.gui.Gui;

import static com.company.home.services.GameService.pullMenuAction;
import static com.company.home.services.GameService.playGame;
import static com.company.home.entities.MenuAction.EXIT;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Gui gui = new Gui();
        gui.init();
        gui.drawMenu();

        while (pullMenuAction(gui) != EXIT) {
            playGame(gui);
        }

        gui.exit();
    }
}
