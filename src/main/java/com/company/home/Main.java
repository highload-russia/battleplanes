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
import one.util.streamex.StreamEx;

import static com.company.home.entities.Player.DEFAULT_PLAYER_LIFES;
import static com.googlecode.lanterna.input.KeyType.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        int terminalHeight = terminal.getTerminalSize().getRows();
        int terminalWidth = terminal.getTerminalSize().getColumns();
        Screen screen = new TerminalScreen(terminal);
        TextGraphics tg = screen.newTextGraphics();

        screen.startScreen();

        Player player = new Player((terminalHeight / 2), 1, DEFAULT_PLAYER_LIFES);

        List<Bullet> bullets = new ArrayList<>();
        List<Bullet> bulletsToRemove;
        List<Opponent> opponents = new ArrayList<>();
        List<Opponent> opponentsToRemove;
        List<Boom> booms = new ArrayList<Boom>();
        List<Boom> boomsToRemove = new ArrayList<>();
        List<Bullet> tmpBullets = new ArrayList<>();
        List<Opponent> tmpOpponents = new ArrayList<>();
        long wave = 1L;

        Gui gui = new Gui(tg, terminal, screen, player, opponents, bullets, booms);

        mainLoop:
        while (true) {

            // process user action
            KeyStroke key = terminal.pollInput();
            if (key != null && key.getKeyType() == ArrowUp) {
                player.moveUp();
            } else if (key != null && key.getKeyType() == ArrowDown) {
                player.moveDown(terminalHeight);
            } else if (key != null && key.getKeyType() == Tab) {
                bullets.add(new Bullet(player));
            }

            // fixme: array copying
      //      tmpOpponents = new ArrayList<Opponent>(opponents);

            bulletsToRemove = StreamEx.of(bullets).filterBy(Bullet::getColumn, terminalWidth).toList();
            bullets.removeAll(bulletsToRemove);
            StreamEx.of(bullets)
                    .forEach(bullet -> {
                        bullet.setColumn(bullet.getColumn() + 1);
                    });

            System.out.println(bullets.size() + "-" + (bullets.size() == 0 ? -1 : bullets.get(0).getColumn()) + "-" + terminalWidth);

            wave++;
            if (wave % 100 == 0) {
                opponents.add(new Opponent(new Random().nextInt(terminalHeight - 2), terminalWidth, 5));
            }

            opponentsToRemove = StreamEx.of(opponents).filterBy(Opponent::getColumn, 0).toList();
            opponents.removeAll(opponentsToRemove);
            StreamEx.of(opponents).forEach(Opponent::processMovement);

           tmpBullets = new ArrayList<Bullet>(bullets);
            tmpOpponents = new ArrayList<Opponent>(opponents);

/*            opponentsToRemove = StreamEx.of(opponents).filter(opponent -> (StreamEx.of(bullets).findFirst(bullet -> bullet.isIntersect(opponent)).isPresent())).toList();
            bulletsToRemove = StreamEx.of(bullets).filter(bullet -> (StreamEx.of(opponentsToRemove).findFirst(opponent -> opponent.isIntersect(bullet)).isPresent())).toList();*/

            for (Opponent opponent : tmpOpponents) {
                for (Bullet bullet : tmpBullets) {
                    if (opponent.isIntersect(bullet)) {
                        bullets.remove(bullet);
                        opponents.remove(opponent);
                        booms.add(new Boom(bullet.getRow(), bullet.getColumn(), 10));
                    }
                }
                if (opponent.isIntersect(player)) {
                    player.setLifes(player.getLifes() - 1);
                    if (player.getLifes() == 0) {
                        break mainLoop;
                    }
                    opponents.remove(opponent);
                    booms.add(new Boom(opponent.getRow(), opponent.getColumn(), 10));
                }
            }

            boomsToRemove = StreamEx.of(booms)
                    .filter(boom -> (boom.getColumn() == 0 || boom.getDistanceOfFlying() == 0))
                    .toList();
            booms.removeAll(boomsToRemove);
            StreamEx.of(booms).forEach(Boom::processMovement);

            gui.redraw(screen);

            Thread.sleep(10);
        }

        screen.clear();
        gui.drawGameOver();
        screen.refresh();

    }
}
