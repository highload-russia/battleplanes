package com.company.home.gui;

import com.company.home.entities.*;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;

import static com.googlecode.lanterna.input.KeyType.*;

public class Gui {

    private final TextGraphics tg;
    private final Terminal terminal;
    private final Screen screen;

    public Gui() throws IOException {
        this.terminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(terminal);
        this.tg = screen.newTextGraphics();
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

    public void drawPlayer(Player player) {
        int playerRow = player.getRow();
        tg.drawTriangle(
                new TerminalPosition(1, playerRow),
                new TerminalPosition(1, playerRow + player.getHeight() - 1),
                new TerminalPosition(player.getWidth(), playerRow + (player.getHeight() / 2)),
                '*');
    }

    public void drawOpponents(List<MovableEntity> opponents) {
        for (MovableEntity opponent : opponents) {
            tg.drawRectangle(
                    new TerminalPosition(opponent.getColumn(), opponent.getRow()),
                    new TerminalSize(opponent.getWidth(), opponent.getHeight()),
                    '<');
        }
    }

    public void drawBullets(List<MovableEntity> bullets) {
        for (MovableEntity bullet : bullets) {
            tg.putString(new TerminalPosition(bullet.getColumn(), bullet.getRow()), "*");
        }
    }

    public void drawBooms(List<MovableEntity> booms) {
        for (MovableEntity boom : booms) {
            tg.putString(new TerminalPosition(boom.getColumn(), boom.getRow()), "BOOM!!!");
        }
    }

    public void drawLife(Player player) {
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

    public PlayerAction pullUserAction() throws IOException {
        KeyStroke key = terminal.pollInput();
        if (key != null && key.getKeyType() == ArrowUp) {
            return PlayerAction.MOVE_UP;
        } else if (key != null && key.getKeyType() == ArrowDown) {
            return PlayerAction.MOVE_DOWN;
        } else if (key != null && key.getKeyType() == Tab) {
            return PlayerAction.SHOOT;
        }
        return PlayerAction.NONE;
    }

    public void redraw(Player player,
                       List<MovableEntity> opponents,
                       List<MovableEntity> bullets,
                       List<MovableEntity> booms) {
        this.screen.clear();
        this.drawPlayer(player);
        this.drawBullets(bullets);
        this.drawOpponents(opponents);
        this.drawBooms(booms);
        this.drawLife(player);
        try {
            this.screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
