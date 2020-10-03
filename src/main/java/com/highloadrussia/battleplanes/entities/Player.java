package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

import static com.highloadrussia.battleplanes.entities.MovingDirection.NONE;

public class Player extends MovableEntity {

    private final static int PLAYER_HEIGHT = PropertiesProvider.getIntValue("player.height");
    private final static int PLAYER_WIDTH = PropertiesProvider.getIntValue("player.width");

    private final static int PLAYER_LIFE = PropertiesProvider.getIntValue("player.life");
    private final static int PLAYER_X_POSITION = PropertiesProvider.getIntValue("player.x.position");
    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = PropertiesProvider.getIntValue("player.frequency.of.movement.in.timeslots");

    private int life;
    private long distance;

    public Player(GameField gameField) {
        super(PLAYER_X_POSITION,
                (gameField.getHeight() / 2) - (PLAYER_HEIGHT / 2),
                PLAYER_WIDTH,
                PLAYER_HEIGHT,
                FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS,
                NONE,
                gameField);
        this.life = PLAYER_LIFE;
        this.distance = 0;
    }

    @Override
    public void destroy() {
        life = 0;
    }

    @Override
    public boolean move() {
        boolean moved = super.move();

        if (moved) {
            this.distance++;
        }

        return moved;
    }

    public int getLife() {
        return life;
    }

    public void decreaseLife() {
        life--;
    }

    public long getDistance() {
        return distance;
    }

    public PlayerBullet doAction(PlayerAction playerAction) {
        switch (playerAction) {
            case MOVE_UP:
                moveUp();
                break;
            case MOVE_DOWN:
                moveDown();
                break;
            case SHOOT:
                return new PlayerBullet(this, gameField);
            case EXIT:
                destroy();
                break;
        }

        return null;
    }

    private void moveUp() {
        if (y > 0) {
            y--;
        }
    }

    private void moveDown() {
        if (y + height < gameField.getHeight()) {
            y++;
        }
    }
}