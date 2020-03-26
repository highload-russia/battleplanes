package com.company.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.company.home.entities.*;
import com.company.home.gui.Gui;
import com.googlecode.lanterna.input.KeyStroke;
import one.util.streamex.StreamEx;

import static com.company.home.entities.Player.DEFAULT_PLAYER_LIFES;
import static com.company.home.services.GameService.*;
import static com.googlecode.lanterna.input.KeyType.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Player player = new Player(10, 1, DEFAULT_PLAYER_LIFES);

        List<MovableEntity> bullets = new ArrayList<>();
        List<MovableEntity> opponents = new ArrayList<>();
        List<MovableEntity> booms = new ArrayList<>();

        long wave = 1L;

        Gui gui = new Gui(player, opponents, bullets, booms);
        int terminalHeight = gui.getTerminal().getTerminalSize().getRows();
        int terminalWidth = gui.getTerminal().getTerminalSize().getColumns();
        gui.init();

        mainLoop:
        while (true) {

            // process user action
            KeyStroke key = gui.getTerminal().pollInput();
            if (key != null && key.getKeyType() == ArrowUp) {
                player.moveUp();
            } else if (key != null && key.getKeyType() == ArrowDown) {
                player.moveDown(terminalHeight);
            } else if (key != null && key.getKeyType() == Tab) {
                bullets.add(new Bullet(player));
            }

            // process bullets
            StreamEx.of(bullets).filterBy(MovableEntity::getColumn, terminalWidth).forEach(MovableEntity::markToRemove);

            // process opponents
            wave++;
            if (wave % 100 == 0) {
                opponents.add(new Opponent(new Random().nextInt(terminalHeight - 2), terminalWidth, 5));
                wave = 0;
            }

            StreamEx.of(opponents).filterBy(MovableEntity::getColumn, 0).forEach(MovableEntity::markToRemove);

            for (MovableEntity opponent : opponents) {
                for (MovableEntity bullet : bullets) {
                    if (opponent.isIntersect(bullet)) {
                        bullet.markToRemove();
                        opponent.markToRemove();
                        booms.add(new Boom(bullet.getRow(), bullet.getColumn(), 10));
                    }
                }
                if (opponent.isIntersect(player)) {
                    player.setLifes(player.getLifes() - 1);
                    if (player.getLifes() == 0) {
                        break mainLoop;
                    }
                    opponent.markToRemove();
                    booms.add(new Boom(opponent.getRow(), opponent.getColumn(), 10));
                }
            }

            // process booms
            StreamEx.of(booms)
                    .filter(boom -> (boom.getColumn() == 0 || ((Boom) boom).getDistanceOfFlying() == 0))
                    .forEach(MovableEntity::markToRemove);

            removeObsolete(bullets, opponents, booms);
            move(bullets, opponents, booms);

            gui.redraw();

            Thread.sleep(10);
        }

        gui.drawGameOver();

    }
}
