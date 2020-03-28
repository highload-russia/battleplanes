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

    private static final char PLAYER_DRAWING_CHARACTER = '*';
    private static final char OPPONENT_DRAWING_CHARACTER = '<';
    private static final String BULLET_DRAWING_STRING = "*";
    private static final String BOOM_EVENT_LABEL = "BOOM!!!";
    private static final String GAME_OVER_EVENT_LABEL = "GAME OVER !!!";

    private final Terminal terminal;
    private final Screen screen;
    private final TextGraphics tg;

    public Gui() throws IOException {
        this.terminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(terminal);
        this.tg = screen.newTextGraphics();
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void init() throws IOException {
        screen.startScreen();
    }

    public void drawPlayer(Player player) {
        int playerRow = player.getY();
        tg.drawTriangle(
                new TerminalPosition(player.getX(), playerRow),
                new TerminalPosition(player.getX(), playerRow + player.getHeight() - 1),
                new TerminalPosition(player.getWidth(), playerRow + (player.getHeight() / 2)),
                PLAYER_DRAWING_CHARACTER);
    }

    public void drawOpponents(List<MovableEntity> opponents) {
        for (MovableEntity opponent : opponents) {
            tg.drawRectangle(
                    new TerminalPosition(opponent.getX(), opponent.getY()),
                    new TerminalSize(opponent.getWidth(), opponent.getHeight()),
                    OPPONENT_DRAWING_CHARACTER);
        }
    }

    public void drawBullets(List<MovableEntity> bullets) {
        for (MovableEntity bullet : bullets) {
            tg.putString(new TerminalPosition(bullet.getX(), bullet.getY()), BULLET_DRAWING_STRING);
        }
    }

    public void drawBooms(List<MovableEntity> booms) {
        for (MovableEntity boom : booms) {
            tg.putString(new TerminalPosition(boom.getX(), boom.getY()), BOOM_EVENT_LABEL);
        }
    }

    public void drawLife(Player player) throws IOException {
        tg.putString(new TerminalPosition(terminal.getTerminalSize().getColumns() / 2 - 6, 1), "Life: " + player.getLife());
    }

    public void drawGameOver() throws IOException {
        screen.clear();
        tg.putString(new TerminalPosition(terminal.getTerminalSize().getColumns() / 2, terminal.getTerminalSize().getRows() / 2), GAME_OVER_EVENT_LABEL);
        screen.refresh();
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
                       List<MovableEntity> booms) throws IOException {
        this.screen.clear();
        this.drawPlayer(player);
        this.drawBullets(bullets);
        this.drawOpponents(opponents);
        this.drawBooms(booms);
        this.drawLife(player);
        this.screen.refresh();
    }

}
