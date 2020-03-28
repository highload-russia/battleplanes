package com.company.home.services;

import com.company.home.entities.*;
import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Random;

public class GameService {

    private static int timeSlot = 0;

    public static void processOpponents(List<MovableEntity> opponents, GameField gameField) {
        timeSlot++;
        if (timeSlot % 100 == 0) {
            opponents.add(new Opponent(new Random().nextInt(gameField.getHeight() - 2), gameField.getWidth(), 5, gameField));
            timeSlot = 0;
        }
    }

    public static void processPlayerAction(Player player, List<MovableEntity> bullets, PlayerAction playerAction, GameField gameField) {
        if (playerAction == PlayerAction.MOVE_UP) {
            player.moveUp();
        } else if (playerAction == PlayerAction.MOVE_DOWN) {
            player.moveDown();
        } else if (playerAction == PlayerAction.SHOOT) {
            bullets.add(new Bullet(player, gameField));
        }
    }

    public static void move(List<List<MovableEntity>> allEntities) {
        for (List<MovableEntity> entities : allEntities) {
            StreamEx.of(entities).forEach(MovableEntity::move);
        }
    }

    public static void markToRemove(List<List<MovableEntity>> allEntities) {
        for (List<MovableEntity> entities : allEntities) {
            StreamEx.of(entities).forEach(MovableEntity::markToRemove);
        }
    }

    public static void processInteractions(List<MovableEntity> opponents,
                                           List<MovableEntity> bullets,
                                           List<MovableEntity> booms,
                                           Player player,
                                           GameField gameField) {
        for (MovableEntity opponent : opponents) {
            for (MovableEntity bullet : bullets) {
                if (opponent.isIntersect(bullet)) {
                    bullet.setMarkedToRemove();
                    opponent.setMarkedToRemove();
                    booms.add(new Boom(bullet.getRow(), bullet.getColumn(), 10, gameField));
                }
            }
            if (opponent.isIntersect(player)) {
                player.setLifes(player.getLifes() - 1);
                opponent.setMarkedToRemove();
                booms.add(new Boom(opponent.getRow(), opponent.getColumn(), 10, gameField));
            }
        }
    }

    public static void removeObsolete(List<List<MovableEntity>> allEntities) {
        markToRemove(allEntities);

        for (List<MovableEntity> entities : allEntities) {
            entities.removeIf(MovableEntity::isMarkedToRemove);
        }
    }

}
