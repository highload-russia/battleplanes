package com.highloadrussia.battleplanes.entities;

import com.highloadrussia.battleplanes.util.PropertiesProvider;

public class Player extends MovableEntity {

    private final static int PLAYER_HEIGHT = PropertiesProvider.getIntValue("player.height");
    private final static int PLAYER_WIDTH = PropertiesProvider.getIntValue("player.width");

    private final static int PLAYER_LIFE = PropertiesProvider.getIntValue("player.life");
    private final static int PLAYER_X_POSITION = PropertiesProvider.getIntValue("player.x.position");
    private final static int FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS = PropertiesProvider.getIntValue("player.frequency.of.movement.in.timeslots");

    private int life;
    private long distanceInTimeSlots;
    private long distance;

    public Player(GameField gameField) {
        super(PLAYER_X_POSITION, (gameField.getHeight() / 2) - (PLAYER_HEIGHT / 2), PLAYER_WIDTH, PLAYER_HEIGHT, gameField);
        this.life = PLAYER_LIFE;
        this.distance = 0;
    }

    @Override
    public void destroy() {
        life = 0;
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

    public int getLife() {
        return life;
    }

    public void decreaseLife() {
        life--;
    }

    public long getDistance() {
        return distance;
    }

    public void move() {
        if (++distanceInTimeSlots % FREQUENCY_OF_MOVEMENT_IN_TIMESLOTS == 0) {
            this.distance++;
        }
    }

    private void moveUp() {
        if (this.getY() > 0) {
            this.setY(this.getY() - 1);
        }
    }

    private void moveDown() {
        if (this.getY() + this.getHeight() < this.getGameField().getHeight()) {
            this.setY(this.getY() + 1);
        }
    }
}