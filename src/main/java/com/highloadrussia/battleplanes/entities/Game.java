package com.highloadrussia.battleplanes.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.highloadrussia.battleplanes.entities.Opponent.FREQUENCY_OF_OPPONENT_APPEARANCE_IN_TIMESLOTS;
import static com.highloadrussia.battleplanes.entities.Opponent.OPPONENT_HEIGHT;

public class Game {

    private int timeSlot = 0;

    private final GameField gameField;
    private final Player player;
    private final List<Opponent> opponents;
    private final List<MovableEntity> bullets;
    private final List<Boom> booms;

    public Game(int gameFieldWidthInColumns, int gameFieldHeightInColumns) {
        this.gameField = new GameField(gameFieldWidthInColumns, gameFieldHeightInColumns);
        this.player = new Player(gameField);
        this.opponents = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.booms = new ArrayList<>();
    }

    public GameField getGameField() {
        return gameField;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public List<MovableEntity> getBullets() {
        return bullets;
    }

    public List<Boom> getBooms() {
        return booms;
    }

    public void move(PlayerAction playerAction) {
        processPlayerAction(playerAction);
        generateOpponents();
        processOpponentsAction();
        processInteractions();
        removeObsolete(Arrays.asList(bullets, opponents, booms));
        move(Arrays.asList(bullets, opponents, booms));
    }

    private void generateOpponents() {
        if (++timeSlot % FREQUENCY_OF_OPPONENT_APPEARANCE_IN_TIMESLOTS == 0) {
            opponents.add(new Opponent(new Random().nextInt(gameField.getHeight() - (OPPONENT_HEIGHT - 1)), gameField, player));
            timeSlot = 0;
        }
    }

    private void processOpponentsAction() {
        for (Opponent opponent : opponents) {
            if (opponent.isReadyToShoot()) {
                bullets.add(new EnemyBullet(opponent, gameField));
                opponent.setReadyToShoot(false);
            }
        }
    }

    private void processPlayerAction(PlayerAction playerAction) {
        switch (playerAction) {
            case MOVE_UP:
                player.moveUp();
                break;
            case MOVE_DOWN:
                player.moveDown();
                break;
            case SHOOT:
                bullets.add(new PlayerBullet(player, gameField));
                break;
            case EXIT:
                player.setLife(0);
                break;
        }
        player.move();
    }

    private void processInteractions() {
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

    private static void removeObsolete(List<List<? extends MovableEntity>> allEntities) {
        for (List<? extends MovableEntity> entities : allEntities) {
            entities.removeIf(MovableEntity::isMarkedToRemove);
        }
    }

    private static void move(List<List<? extends MovableEntity>> allEntities) {
        for (List<? extends MovableEntity> entities : allEntities) {
            for (MovableEntity entity : entities) {
                entity.move();
            }
        }
    }
}