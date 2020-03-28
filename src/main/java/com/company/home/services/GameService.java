package com.company.home.services;

import com.company.home.entities.*;

import java.util.List;
import java.util.Random;

import static com.company.home.entities.Opponent.FREQUENCY_OF_OPPONENT_APPEARENCE_IN_TIMESLOTS;
import static com.company.home.entities.Opponent.OPPONENT_HEIGHT;

public class GameService {

    private static int timeSlot = 0;

    public static void generateOpponents(List<MovableEntity> opponents, GameField gameField) {
        if (++timeSlot % FREQUENCY_OF_OPPONENT_APPEARENCE_IN_TIMESLOTS == 0) {
            opponents.add(new Opponent(new Random().nextInt(gameField.getHeight() - (OPPONENT_HEIGHT - 1)), gameField));
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
        for (MovableEntity opponent : opponents) {
            for (MovableEntity bullet : bullets) {
                if (opponent.isIntersect(bullet)) {
                    bullet.setMarkedToRemove();
                    opponent.setMarkedToRemove();
                    booms.add(new Boom(bullet.getX(), bullet.getY(), gameField));
                }
            }
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
