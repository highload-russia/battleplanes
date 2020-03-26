package com.company.home.services;

import com.company.home.entities.MovableEntity;
import one.util.streamex.StreamEx;

import java.util.List;

public class GameService {

    public static void move(List<MovableEntity>... allEntities) {
        for (List<MovableEntity> entities : allEntities) {
            StreamEx.of(entities).forEach(MovableEntity::move);
        }
    }

    public static void removeObsolete(List<MovableEntity>... allEntities) {
        for (List<MovableEntity> entities : allEntities) {
            entities.removeIf(MovableEntity::isMarkedToRemove);
        }
    }


}
