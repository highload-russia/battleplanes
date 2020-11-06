package com.highloadrussia.battleplanes.gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.highloadrussia.battleplanes.entities.*;
import com.highloadrussia.battleplanes.entities.Enemy;
import com.highloadrussia.battleplanes.util.PropertiesProvider;

import java.io.IOException;
import java.util.List;

import static com.googlecode.lanterna.input.KeyType.ArrowDown;
import static com.googlecode.lanterna.input.KeyType.ArrowUp;
import static com.googlecode.lanterna.input.KeyType.Escape;
import static com.googlecode.lanterna.input.KeyType.Tab;

public class LanternaGui implements Gui {

    private static final char PLAYER_DRAWING_CHARACTER = PropertiesProvider.getCharValue("player.drawing.character");
    private static final char ENEMY_DRAWING_CHARACTER = PropertiesProvider.getCharValue("enemy.drawing.character");

    private static final String BULLET_DRAWING_STRING = PropertiesProvider.getStringValue("bullet.drawing.string");
    private static final String BOOM_EVENT_LABEL = PropertiesProvider.getStringValue("boom.event.label");
    private static final String GAME_OVER_EVENT_LABEL = PropertiesProvider.getStringValue("game.over.event.label");
    private static final String WELCOME_LABEL = PropertiesProvider.getStringValue("welcome.label");
    private static final String MENU_LABEL = PropertiesProvider.getStringValue("menu.label");

    private final Terminal terminal;
    private final Screen screen;
    private final TextGraphics tg;

    private final int heightInRows;
    private final int widthInColumns;

    public LanternaGui() throws IOException {
        this.terminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(terminal);
        this.tg = screen.newTextGraphics();
        this.heightInRows = this.terminal.getTerminalSize().getRows();
        this.widthInColumns = this.terminal.getTerminalSize().getColumns();
    }

    @Override
    public int getHeightInRows() {
        return heightInRows;
    }

    @Override
    public int getWidthInColumns() {
        return widthInColumns;
    }

    @Override
    public void init() throws IOException {
        screen.startScreen();
    }

    @Override
    public void drawPlayer(Player player) {
        int playerRow = player.getY();
        tg.drawTriangle(
                new TerminalPosition(player.getX(), playerRow),
                new TerminalPosition(player.getX(), playerRow + player.getHeight() - 1),
                new TerminalPosition(player.getWidth(), playerRow + (player.getHeight() / 2)),
                PLAYER_DRAWING_CHARACTER);
    }

    @Override
    public void drawEnemies(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            tg.drawRectangle(
                    new TerminalPosition(enemy.getX(), enemy.getY()),
                    new TerminalSize(enemy.getWidth(), enemy.getHeight()),
                    ENEMY_DRAWING_CHARACTER);
        }
    }

    @Override
    public void drawBullets(List<MovableEntity> bullets) {
        for (MovableEntity bullet : bullets) {
            tg.putString(new TerminalPosition(bullet.getX(), bullet.getY()), BULLET_DRAWING_STRING);
        }
    }

    @Override
    public void drawBooms(List<Boom> booms) {
        for (MovableEntity boom : booms) {
            tg.putString(new TerminalPosition(boom.getX(), boom.getY()), BOOM_EVENT_LABEL);
        }
    }

    @Override
    public void drawLife(Player player) throws IOException {
        tg.putString(new TerminalPosition(terminal.getTerminalSize().getColumns() / 2 - 10, 1), "Life: " + player.getLife());
    }

    @Override
    public void drawDistance(Player player) throws IOException {
        tg.putString(new TerminalPosition(terminal.getTerminalSize().getColumns() / 2, 1), "Distance: " + player.getDistance());
    }

    @Override
    public void drawMenu() throws IOException {
        int firstLabelRow = terminal.getTerminalSize().getRows() / 2 - 3;
        screen.clear();
        tg.putString(new TerminalPosition(getColumnByLabel(WELCOME_LABEL), firstLabelRow), WELCOME_LABEL);
        tg.putString(new TerminalPosition(getColumnByLabel(MENU_LABEL), firstLabelRow + 2), MENU_LABEL);
        screen.refresh();
    }

    @Override
    public void drawGameOver(long distance) throws IOException {
        String distanceLabel = "Distance: " + distance;
        int firstLabelRow = terminal.getTerminalSize().getRows() / 2 - 3;
        screen.clear();
        tg.putString(new TerminalPosition(getColumnByLabel(GAME_OVER_EVENT_LABEL), firstLabelRow), GAME_OVER_EVENT_LABEL);
        tg.putString(new TerminalPosition(getColumnByLabel(distanceLabel), firstLabelRow + 2), distanceLabel);
        tg.putString(new TerminalPosition(getColumnByLabel(MENU_LABEL), firstLabelRow + 4), MENU_LABEL);
        screen.refresh();
    }

    private int getColumnByLabel(String label) throws IOException {
        return terminal.getTerminalSize().getColumns() / 2 - label.length() / 2;
    }

    @Override
    public PlayerAction pullPlayerAction() throws IOException {
        KeyStroke key = terminal.pollInput();
        if (key != null && key.getKeyType() == ArrowUp) {
            return PlayerAction.MOVE_UP;
        } else if (key != null && key.getKeyType() == ArrowDown) {
            return PlayerAction.MOVE_DOWN;
        } else if (key != null && key.getKeyType() == Tab) {
            return PlayerAction.SHOOT;
        } else if (key != null && key.getKeyType() == Escape) {
            return PlayerAction.EXIT;
        }
        return PlayerAction.NONE;
    }

    @Override
    public MenuAction pullMenuAction() throws IOException {
        KeyStroke key = terminal.pollInput();
        if (key != null && key.getKeyType() == Tab) {
            return MenuAction.START;
        } else if (key != null && key.getKeyType() == Escape) {
            return MenuAction.EXIT;
        }
        return MenuAction.NONE;
    }

    @Override
    public void redraw(Game game) throws IOException {
        screen.clear();
        drawPlayer(game.getPlayer());
        drawBullets(game.getPlayerBullets());
        drawBullets(game.getEnemyBullets());
        drawEnemies(game.getEnemies());
        drawBooms(game.getBooms());
        drawLife(game.getPlayer());
        drawDistance(game.getPlayer());
        screen.refresh();
    }

    @Override
    public void exit() throws IOException {
        screen.close();
    }
}
