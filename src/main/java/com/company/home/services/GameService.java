package com.company.home.services;

import com.company.home.entities.*;
import com.company.home.gui.Gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.company.home.entities.Opponent.FREQUENCY_OF_OPPONENT_APPEARENCE_IN_TIMESLOTS;
import static com.company.home.entities.Opponent.OPPONENT_HEIGHT;

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
        GameField gameField = new GameField(gui.getTerminal().getTerminalSize().getColumns(), gui.getTerminal().getTerminalSize().getRows());

        Player player = new Player(gameField);
        List<MovableEntity> bullets = new ArrayList<>();
        List<MovableEntity> opponents = new ArrayList<>();
        List<MovableEntity> booms = new ArrayList<>();

        while (player.getLife() > 0) {

            processPlayerAction(player, bullets, gui.pullUserAction(), gameField);
            generateOpponents(opponents, gameField, player);
            processOpponentsAction(opponents, bullets, gameField);
            processInteractions(opponents, bullets, booms, player, gameField);
            removeObsolete(Arrays.asList(bullets, opponents, booms));
            move(Arrays.asList(bullets, opponents, booms));

            gui.redraw(player, opponents, bullets, booms);

            Thread.sleep(REFRESH_TIME_IN_MILLISECONDS);
        }

        gui.drawGameOver(player);
    }

    public static void generateOpponents(List<MovableEntity> opponents, GameField gameField, Player player) {
        if (++timeSlot % FREQUENCY_OF_OPPONENT_APPEARENCE_IN_TIMESLOTS == 0) {
            opponents.add(new Opponent(new Random().nextInt(gameField.getHeight() - (OPPONENT_HEIGHT - 1)), gameField, player));
            timeSlot = 0;
        }
    }

    public static void processOpponentsAction(List<MovableEntity> opponents, List<MovableEntity> bullets, GameField gameField) {
        for (MovableEntity opponent : opponents) {
            Opponent tmpOpponent = (Opponent) opponent;
            if (tmpOpponent.isReadyToShoot()) {
                bullets.add(new EnemyBullet(tmpOpponent, gameField));
                tmpOpponent.setReadyToShoot(false);
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
        } else if (playerAction == PlayerAction.EXIT){
            player.setLife(0);
        }
        player.move();
    }

    public static void move(List<List<MovableEntity>> allEntities) {
        for (List<MovableEntity> entities : allEntities) {
            for (MovableEntity entity : entities) {
                entity.move();
            }
        }
    }

    public static void processInteractions(List<MovableEntity> opponents,
                                           List<MovableEntity> bullets,
                                           List<MovableEntity> booms,
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

    public static void removeObsolete(List<List<MovableEntity>> allEntities) {
        for (List<MovableEntity> entities : allEntities) {
            entities.removeIf(MovableEntity::isMarkedToRemove);
        }
    }

}
