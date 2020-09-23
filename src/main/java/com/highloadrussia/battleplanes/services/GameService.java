package com.highloadrussia.battleplanes.services;

import com.highloadrussia.battleplanes.entities.Boom;
import com.highloadrussia.battleplanes.entities.EnemyBullet;
import com.highloadrussia.battleplanes.entities.GameField;
import com.highloadrussia.battleplanes.entities.MenuAction;
import com.highloadrussia.battleplanes.entities.MovableEntity;
import com.highloadrussia.battleplanes.entities.Opponent;
import com.highloadrussia.battleplanes.entities.Player;
import com.highloadrussia.battleplanes.entities.PlayerAction;
import com.highloadrussia.battleplanes.entities.PlayerBullet;
import com.highloadrussia.battleplanes.gui.Gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.highloadrussia.battleplanes.entities.Opponent.FREQUENCY_OF_OPPONENT_APPEARANCE_IN_TIMESLOTS;
import static com.highloadrussia.battleplanes.entities.Opponent.OPPONENT_HEIGHT;

public class GameService {

    private static final int REFRESH_TIME_IN_MILLISECONDS = 10;

    private static int timeSlot = 0;

    public static MenuAction pullMenuAction(Gui gui) throws IOException, InterruptedException {
        MenuAction menuAction = MenuAction.NONE;
        while (menuAction == MenuAction.NONE) {
            menuAction = gui.pullMenuAction();
            Thread.sleep(REFRESH_TIME_IN_MILLISECONDS);
        }
        return menuAction;
    }

    public static void playGame(Gui gui) throws IOException, InterruptedException {
        GameField gameField = new GameField(gui.getWidthInColumns(), gui.getHeightInRows());

        Player player = new Player(gameField);

        List<MovableEntity> bullets = new ArrayList<>();
        List<Opponent> opponents = new ArrayList<>();
        List<Boom> booms = new ArrayList<>();

        while (player.getLife() > 0) {

            processPlayerAction(player, bullets, gui.pullUserAction(), gameField);
            generateOpponents(opponents, gameField, player);
            processOpponentsAction(opponents, bullets, gameField);
            processInteractions(opponents, bullets, booms, player, gameField);
            removeObsolete(Arrays.asList(bullets, opponents, booms));
            move(Arrays.asList(bullets, opponents, booms));

            gui.redraw(player, opponents, bullets, booms);

            //noinspection BusyWait
            Thread.sleep(REFRESH_TIME_IN_MILLISECONDS);
        }

        gui.drawGameOver(player);
    }

    public static void generateOpponents(List<Opponent> opponents, GameField gameField, Player player) {
        if (++timeSlot % FREQUENCY_OF_OPPONENT_APPEARANCE_IN_TIMESLOTS == 0) {
            opponents.add(new Opponent(new Random().nextInt(gameField.getHeight() - (OPPONENT_HEIGHT - 1)), gameField, player));
            timeSlot = 0;
        }
    }

    public static void processOpponentsAction(List<Opponent> opponents, List<MovableEntity> bullets, GameField gameField) {
        for (Opponent opponent : opponents) {
            if (opponent.isReadyToShoot()) {
                bullets.add(new EnemyBullet(opponent, gameField));
                opponent.setReadyToShoot(false);
            }
        }
    }

    public static void processPlayerAction(Player player, List<MovableEntity> bullets, PlayerAction playerAction, GameField gameField) {
        if (playerAction == PlayerAction.MOVE_UP) {
            player.moveUp();
        } else if (playerAction == PlayerAction.MOVE_DOWN) {
            player.moveDown();
        } else if (playerAction == PlayerAction.SHOOT) {
            bullets.add(new PlayerBullet(player, gameField));
        } else if (playerAction == PlayerAction.EXIT) {
            player.setLife(0);
        }
        player.move();
    }

    public static void move(List<List<? extends MovableEntity>> allEntities) {
        for (List<? extends MovableEntity> entities : allEntities) {
            for (MovableEntity entity : entities) {
                entity.move();
            }
        }
    }

    public static void processInteractions(List<Opponent> opponents,
                                           List<MovableEntity> bullets,
                                           List<Boom> booms,
                                           Player player,
                                           GameField gameField) {
        for (MovableEntity bullet : bullets) {
            for (MovableEntity opponent : opponents) {
                if (opponent.isIntersect(bullet) && (bullet instanceof PlayerBullet)) {
                    bullet.setMarkedToRemove();
                    opponent.setMarkedToRemove();
                    booms.add(new Boom(bullet.getX(), bullet.getY(), gameField));
                }
            }

            if (player.isIntersect(bullet) && (bullet instanceof EnemyBullet)) {
                bullet.setMarkedToRemove();
                booms.add(new Boom(bullet.getX(), bullet.getY(), gameField));
                player.setLife(player.getLife() - 1);
            }
        }

        for (MovableEntity opponent : opponents) {
            if (opponent.isIntersect(player)) {
                player.setLife(player.getLife() - 1);
                opponent.setMarkedToRemove();
                booms.add(new Boom(opponent.getX(), opponent.getY(), gameField));
            }
        }
    }

    public static void removeObsolete(List<List<? extends MovableEntity>> allEntities) {
        for (List<? extends MovableEntity> entities : allEntities) {
            entities.removeIf(MovableEntity::isMarkedToRemove);
        }
    }

}
