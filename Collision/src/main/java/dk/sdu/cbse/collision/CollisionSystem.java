package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;

public class CollisionSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        CollisionDetector detector = new CollisionDetector();

        // Bullet vs Asteroid
        for (Entity bullet : world.getEntities(Bullet.class)) {
            for (Entity asteroid : world.getEntities(Asteroid.class)) {
                detector.checkCollision(bullet, asteroid, world);
            }
        }

        // Player vs Asteroid (module-agnostic: look for Player class by name)
        for (Entity entity : world.getEntities()) {
            if (entity.getClass().getSimpleName().equals("Player")) {
                for (Entity asteroid : world.getEntities(Asteroid.class)) {
                    detector.checkCollision(entity, asteroid, world);
                }
            }
        }

        // Asteroid vs Asteroid (avoid self-collision and duplicate checks)
        java.util.List<Entity> asteroids = world.getEntities(Asteroid.class);
        for (int i = 0; i < asteroids.size(); i++) {
            Entity a1 = asteroids.get(i);
            for (int j = i + 1; j < asteroids.size(); j++) {
                Entity a2 = asteroids.get(j);
                detector.checkCollision(a1, a2, world);
            }
        }
    }
}
