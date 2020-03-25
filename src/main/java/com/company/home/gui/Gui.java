package com.company.home.gui;

import com.company.home.entities.*;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class Gui {

    private final TextGraphics tg;
    private final Terminal terminal;
    private final Player player;
    private final List<Opponent> opponents;
    private final List<Bullet> bullets;
    private final List<Boom> booms;

    public Gui(TextGraphics tg,
               Terminal terminal,
               Screen screen,
               Player player,
               List<Opponent> opponents,
               List<Bullet> bullets,
               List<Boom> booms) {
        this.player = player;
        this.tg = tg;
        this.terminal = terminal;
        this.opponents = opponents;
        this.bullets = bullets;
        this.booms = booms;
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
        for (Opponent opponent : opponents) {
            tg.drawRectangle(
                    new TerminalPosition(opponent.getColumn(), opponent.getRow()),
                    new TerminalSize(opponent.getWidth(), opponent.getHeight()),
                    '<');
        }
    }

    public void drawBullets() {
        for (Bullet bullet : bullets) {
            tg.putString(new TerminalPosition(bullet.getColumn(), bullet.getRow()), "*");
        }
    }

    public void drawBooms() {
        for (Boom boom : booms) {
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
            tg.putString(new TerminalPosition(terminal.getTerminalSize().getColumns() / 2, terminal.getTerminalSize().getRows() / 2), "GAME OVER !!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redraw(Screen screen) {
        screen.clear();
        this.drawPlayer();
        this.drawBullets();
        this.drawOpponents();
        this.drawBooms();
        this.drawLife();
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
