package com.company.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.company.home.entities.Boom;
import com.company.home.entities.Bullet;
import com.company.home.entities.Opponent;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
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

        int planeHeight = 4;
        int lifes = 5;

        int startRow = (screen.getTerminalSize().getRows() / 2) - (planeHeight / 2);

        tg.drawRectangle(TerminalPosition.TOP_LEFT_CORNER, terminal.getTerminalSize(), TextCharacter.DEFAULT_CHARACTER);

        tg.drawTriangle(new TerminalPosition(1, startRow), new TerminalPosition(1, startRow + 4), new TerminalPosition(4, startRow + 2), '*');

        screen.refresh();

        KeyStroke ks = new KeyStroke(ArrowUp);

        List<Bullet> bullets = new ArrayList<Bullet>();
        List<Opponent> opponents = new ArrayList<Opponent>();
        List<Boom> booms = new ArrayList<Boom>();
        long wave = 1L;

        mainLoop:
        while (true) {

            KeyStroke key = terminal.pollInput();

            screen.clear();

            if (key != null && key.getKeyType() == ArrowUp) {
                if (startRow > 0) {
                    startRow--;
                }
            } else if (key != null && key.getKeyType() == ArrowDown) {
                if (startRow + 5 < terminal.getTerminalSize().getRows()) {
                    startRow++;
                }
            } else if (key != null && key.getKeyType() == Tab) {
                bullets.add(new Bullet(startRow + (planeHeight / 2), 6));
            }

            tg.drawTriangle(new TerminalPosition(1, startRow), new TerminalPosition(1, startRow + 4), new TerminalPosition(4, startRow + 2), '*');

            List<Bullet> tmpBullets = new ArrayList<Bullet>(bullets);
            List<Opponent> tmpOpponents = new ArrayList<Opponent>(opponents);

            for (Bullet bullet : tmpBullets) {
                if (bullet.getColumn() == terminal.getTerminalSize().getColumns()) {
                    bullets.remove(bullet);
                } else {
                    bullet.getColumn()++;
                    tg.putString(new TerminalPosition(bullet.getColumn(), bullet.getRow()), "*");
                }
            }

            wave++;
            if (wave % 100 == 0) {
                opponents.add(new Opponent(new Random().nextInt(terminal.getTerminalSize().getRows() - 2), terminal.getTerminalSize().getColumns()));
            }

            for (Opponent opponent : tmpOpponents) {
                if (opponent.getColumn() == 0) {
                    opponents.remove(opponent);
                } else {
                    if (wave % 5 == 0) {
                        opponent.getColumn()--;
                    }
                    tg.drawRectangle(new TerminalPosition(opponent.getColumn(), opponent.getRow()), new TerminalSize(5, 3), '<');
                }
            }

            tmpBullets = new ArrayList<Bullet>(bullets);
            tmpOpponents = new ArrayList<Opponent>(opponents);

            for (Opponent opponent : tmpOpponents) {
                for (Bullet bullet : tmpBullets) {
                    if (bullet.getRow() >= opponent.getRow() && bullet.getRow() <= opponent.getRow() + 2 && bullet.getColumn() == opponent.getColumn()) {
                        bullets.remove(bullet);
                        opponents.remove(opponent);
                        booms.add(new Boom(bullet.getRow(), bullet.getColumn()));
                    }
                }
                if (opponent.getRow() + 2 >= startRow && opponent.getRow() <= startRow + 4 && opponent.getColumn() <= 3) {
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
                        boom.getColumn()--;
                        boom.getDistanceOfFlying()--;
                    }
                    tg.putString(new TerminalPosition(boom.getColumn(), boom.getRow()), "BOOM!!!");
                }
            }

            tg.putString(new TerminalPosition(terminal.getTerminalSize().getColumns() / 2, 1), "Life: " + lifes);

            screen.refresh();

            Thread.sleep(10);
        }

        screen.clear();

        tg.putString(new TerminalPosition(terminal.getTerminalSize().getColumns() / 2, terminal.getTerminalSize().getRows() / 2), "GAME OVER !!!");

        screen.refresh();

    }
}
