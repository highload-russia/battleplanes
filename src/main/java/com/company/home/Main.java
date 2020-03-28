package com.company.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.company.home.entities.*;
import com.company.home.gui.Gui;

import static com.company.home.services.GameService.*;

public class Main {

    private static final int REFRESH_TIME_IN_MILLISECONDS = 10;

    public static void main(String[] args) throws IOException, InterruptedException {

        List<MovableEntity> bullets = new ArrayList<>();
        List<MovableEntity> opponents = new ArrayList<>();
        List<MovableEntity> booms = new ArrayList<>();

        Gui gui = new Gui();
        gui.init();
        GameField gameField = new GameField(gui.getTerminal().getTerminalSize().getColumns(), gui.getTerminal().getTerminalSize().getRows());

        Player player = new Player(gameField);

        while (player.getLife() > 0) {

            processPlayerAction(player, bullets, gui.pullUserAction(), gameField);
            generateOpponents(opponents, gameField);
            processOpponentsAction(opponents, bullets, gameField);
            processInteractions(opponents, bullets, booms, player, gameField);
            removeObsolete(Arrays.asList(bullets, opponents, booms));
            move(Arrays.asList(bullets, opponents, booms));

            gui.redraw(player, opponents, bullets, booms);

            Thread.sleep(REFRESH_TIME_IN_MILLISECONDS);
        }

        gui.drawGameOver(player);
    }
}
