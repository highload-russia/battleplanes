package com.company.home.gui;

import com.company.home.entities.*;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class Gui {

    private final TextGraphics tg;
    private final Terminal terminal;
    private final Screen screen;
    private final Player player;
    private final List<MovableEntity> opponents;
    private final List<MovableEntity> bullets;
    private final List<MovableEntity> booms;

    public Gui(Player player,
               List<MovableEntity> opponents,
               List<MovableEntity> bullets,
               List<MovableEntity> booms) throws IOException {
        this.terminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(terminal);
        this.tg = screen.newTextGraphics();
        this.player = player;
        this.opponents = opponents;
        this.bullets = bullets;
        this.booms = booms;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void init() {
        try {
            screen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawPlayer() {
        int playerRow = player.getRow();
        tg.drawTriangle(
                new TerminalPosition(1, playerRow),
                new TerminalPosition(1, playerRow + player.getHeight() - 1),
                new TerminalPosition(player.getWidth(), playerRow + (player.getHeight() / 2)),
                '*');
    }

    public void drawOpponents() {
        for (MovableEntity opponent : opponents) {
            tg.drawRectangle(
                    new TerminalPosition(opponent.getColumn(), opponent.getRow()),
                    new TerminalSize(opponent.getWidth(), opponent.getHeight()),
                    '<');
        }
    }

    public void drawBullets() {
        for (MovableEntity bullet : bullets) {
            tg.putString(new TerminalPosition(bullet.getColumn(), bullet.getRow()), "*");
        }
    }

    public void drawBooms() {
        for (MovableEntity boom : booms) {
            tg.putString(new TerminalPosition(boom.getColumn(), boom.getRow()), "BOOM!!!");
        }
    }

    public void drawLife() {
        try {
            tg.putString(new TerminalPosition(terminal.getTerminalSize().getColumns() / 2, 1), "Life: " + player.getLifes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawGameOver() {
        try {
            screen.clear();
            tg.putString(new TerminalPosition(terminal.getTerminalSize().getColumns() / 2, terminal.getTerminalSize().getRows() / 2), "GAME OVER !!!");
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redraw() {
        this.screen.clear();
        this.drawPlayer();
        this.drawBullets();
        this.drawOpponents();
        this.drawBooms();
        this.drawLife();
        try {
            this.screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
