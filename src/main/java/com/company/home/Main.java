package com.company.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.company.home.entities.Boom;
import com.company.home.entities.Bullet;
import com.company.home.entities.Opponent;
import com.company.home.entities.Player;
import com.company.home.gui.Gui;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import static com.googlecode.lanterna.input.KeyType.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Terminal terminal = new DefaultTerminalFactory().createTerminal();

        Screen screen = new TerminalScreen(terminal);
        TextGraphics tg = screen.newTextGraphics();

        screen.startScreen();

        int lifes = 5;

        Player player = new Player((screen.getTerminalSize().getRows() / 2), 1);
        KeyStroke ks = new KeyStroke(ArrowUp);

        List<Bullet> bullets = new ArrayList<Bullet>();
        List<Opponent> opponents = new ArrayList<Opponent>();
        List<Boom> booms = new ArrayList<Boom>();
        List<Bullet> tmpBullets;
        List<Opponent> tmpOpponents;
        long wave = 1L;

        Gui gui = new Gui(tg, terminal, player, opponents, bullets, booms);

        mainLoop:
        while (true) {

            KeyStroke key = terminal.pollInput();

            screen.clear();

            // user actions
            if (key != null && key.getKeyType() == ArrowUp) {
                player.moveUp();
            } else if (key != null && key.getKeyType() == ArrowDown) {
                player.moveDown(terminal.getTerminalSize().getRows());
            } else if (key != null && key.getKeyType() == Tab) {
                bullets.add(new Bullet(player));
            }

            gui.drawPlayer();

            // fixme: array copying
            tmpBullets = new ArrayList<Bullet>(bullets);
            tmpOpponents = new ArrayList<Opponent>(opponents);

            for (Bullet bullet : tmpBullets) {
                if (bullet.getColumn() == terminal.getTerminalSize().getColumns()) {
                    bullets.remove(bullet);
                } else {
                    bullet.setColumn(bullet.getColumn() + 1);
                }
            }
            gui.drawBullets();

            wave++;
            if (wave % 100 == 0) {
                opponents.add(new Opponent(new Random().nextInt(terminal.getTerminalSize().getRows() - 2), terminal.getTerminalSize().getColumns()));
            }

            for (Opponent opponent : tmpOpponents) {
                if (opponent.getColumn() == 0) {
                    opponents.remove(opponent);
                } else {
                    if (wave % 5 == 0) {
                        opponent.setColumn(opponent.getColumn() - 1);
                    }
                }
            }
            gui.drawOpponents();

            tmpBullets = new ArrayList<Bullet>(bullets);
            tmpOpponents = new ArrayList<Opponent>(opponents);

            for (Opponent opponent : tmpOpponents) {
                for (Bullet bullet : tmpBullets) {
                    if (opponent.isIntersect(bullet)) {
                        bullets.remove(bullet);
                        opponents.remove(opponent);
                        booms.add(new Boom(bullet.getRow(), bullet.getColumn()));
                    }
                }
                if (opponent.isIntersect(player)) {
                    lifes--;
                    if (lifes == 0) {
                        break mainLoop;
                    }
                    opponents.remove(opponent);
                    booms.add(new Boom(opponent.getRow(), opponent.getColumn()));
                }
            }

            List<Boom> tmpBooms = new ArrayList<Boom>(booms);

            for (Boom boom : tmpBooms) {
                if (boom.getColumn() == 0 || boom.getDistanceOfFlying() == 0) {
                    booms.remove(boom);
                } else {
                    if (wave % 10 == 0) {
                        boom.setColumn(boom.getColumn() - 1);
                        boom.setDistanceOfFlying(boom.getDistanceOfFlying() - 1);
                    }
                }
            }
            gui.drawBooms();

            gui.drawLife(lifes);

            screen.refresh();

            Thread.sleep(10);
        }

        screen.clear();

        gui.drawGameOver();

        screen.refresh();

    }
}
