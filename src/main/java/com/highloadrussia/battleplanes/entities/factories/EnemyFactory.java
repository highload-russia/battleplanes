package com.highloadrussia.battleplanes.entities.factories;

import com.highloadrussia.battleplanes.entities.Enemy;
import com.highloadrussia.battleplanes.entities.Game;

import java.util.Random;

import static com.highloadrussia.battleplanes.entities.Enemy.FREQUENCY_OF_ENEMY_APPEARANCE_IN_TIMESLOTS;
import static com.highloadrussia.battleplanes.entities.Enemy.ENEMY_HEIGHT;

public class EnemyFactory {

    private static final Random random = new Random();

    public static Enemy createEnemy(Game game) {
        if (game.getTimeSlot() % FREQUENCY_OF_ENEMY_APPEARANCE_IN_TIMESLOTS == 0) {

            game.setTimeSlot(0);

            return new Enemy(
                    random.nextInt(game.getGameField().getHeight() - (ENEMY_HEIGHT - 1)),
                    game.getGameField(),
                    game.getPlayer());
        }

        return null;
    }

}
