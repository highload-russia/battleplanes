package com.company.home;

import java.io.IOException;

import com.company.home.entities.*;
import com.company.home.gui.Gui;

import static com.company.home.services.GameService.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Gui gui = new Gui();
        gui.init();
        gui.drawMenu();

        while (pullMenuAction(gui) != MenuAction.EXIT) {
            playGame(gui);
        }

        gui.exit();
    }
}
