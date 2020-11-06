package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.entities.factories.EnemyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Game {

    private int timeSlot = 0;

    private final GameField gameField;
    private final Player player;

    private final List<Enemy> enemies;
    private final List<MovableEntity> playerBullets;
    private final List<MovableEntity> enemyBullets;
    private final List<Boom> booms;

    public Game(int gameFieldWidthInColumns, int gameFieldHeightInColumns) {
        this.gameField = new GameField(gameFieldWidthInColumns, gameFieldHeightInColumns);
        this.player = new Player(gameField);
        this.enemies = new ArrayList<>();
        this.playerBullets = new ArrayList<>();
        this.enemyBullets = new ArrayList<>();
        this.booms = new ArrayList<>();
    }

    public GameField getGameField() {
        return gameField;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<MovableEntity> getEnemyBullets() {
        return enemyBullets;
    }

    public List<MovableEntity> getPlayerBullets() {
        return playerBullets;
    }

    public List<Boom> getBooms() {
        return booms;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void move(PlayerAction playerAction) {

        switch (playerAction) {
            case MOVE_UP:
                player.moveUp();
                break;
            case MOVE_DOWN:
                player.moveDown();
                break;
            case SHOOT:
                PlayerBullet playerBullet = player.tryShoot();

                if (playerBullet != null) {
                    playerBullets.add(playerBullet);
                }
                break;
            case EXIT:
                player.destroy();
                return;
        }

        createEnemies();
        doEnemiesAction();
        processInteractions();
        removeDestroyed();
        moveUnits();

        timeSlot++;
    }

    private void moveUnits() {
        player.move();
        playerBullets.forEach(MovableEntity::move);
        enemyBullets.forEach(MovableEntity::move);
        enemies.forEach(MovableEntity::move);
        booms.forEach(MovableEntity::move);
    }

    private void createEnemies() {
        Enemy enemy = EnemyFactory.createEnemy(this);

        if (enemy != null) {
            enemies.add(enemy);
        }
    }

    private void doEnemiesAction() {
        enemies.stream()
                .map(Enemy::tryShoot)
                .filter(Objects::nonNull)
                .forEach(enemyBullets::add);
    }

    private void processInteractions() {
        playerBullets.forEach(
                bullet -> enemies.stream()
                        .filter(enemy -> enemy.isIntersect(bullet))
                        .forEach(enemy -> {
                                    bullet.destroy();
                                    enemy.destroy();
                                    booms.add(new Boom(bullet.x, bullet.y, gameField));
                                }
                        )
        );

        enemyBullets.forEach(
                bullet ->
                {
                    if (player.isIntersect(bullet)) {
                        bullet.destroy();
                        booms.add(new Boom(bullet.x, bullet.y, gameField));
                        player.decreaseLife();
                    }
                }
        );

        enemies.stream()
                .filter(enemy -> enemy.isIntersect(player))
                .forEach(enemy -> {
                    player.decreaseLife();
                    enemy.destroy();
                    booms.add(new Boom(enemy.x, enemy.y, gameField));
                });
    }

    private void removeDestroyed() {
        Predicate<MovableEntity> movableEntityDestroyingPredicate =
                e -> e.isDestroyed() || e.x <= 0 || e.x > gameField.getWidth();
        Predicate<Boom> boomDestroyingPredicate =
                e -> e.isDestroyed() || e.x <= 0 || e.x > gameField.getWidth() || e.getNumberOfAvailableMovements() <= 0;

        playerBullets.removeIf(movableEntityDestroyingPredicate);
        enemyBullets.removeIf(movableEntityDestroyingPredicate);
        enemies.removeIf(movableEntityDestroyingPredicate);
        booms.removeIf(boomDestroyingPredicate);
    }
}