package dk.sdu.cbse.collision;

import java.util.List;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityPostProcessingService;

public class CollisionSystem implements IEntityPostProcessingService {

    @Override
    public void process(World world, GameData gameData) {
        List<Entity> entities = world.getEntities();

        for (int i = 0; i < entities.size(); i++) {
            Entity entityA = entities.get(i);
            if (!entityA.isAlive()) continue;
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entityB = entities.get(j);
                if (entityA.equals(entityB)) continue;
                if (!entityB.isAlive()) continue;
                if (!entityA.isAlive()) continue;
                // Check if locations and radius collide
                if (entityA.getLocation().distance(entityB.getLocation()) < entityA.getRadius() + entityB.getRadius()) {
                    entityA.setHealth(entityA.getHealth() - 1);
                    entityB.setHealth(entityB.getHealth() - 1);

                    if (entityA.getHealth() <= 0) {
                        entityA.kill(gameData);
                    }
                    if (entityB.getHealth() <= 0) {
                        entityB.kill(gameData);
                    }
                }

            }
        }
    }

}