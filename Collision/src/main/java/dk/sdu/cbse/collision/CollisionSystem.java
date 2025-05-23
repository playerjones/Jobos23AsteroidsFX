package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.player.Player;

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

        // Player vs Asteroid
        for (Entity player : world.getEntities(Player.class)) {
            for (Entity asteroid : world.getEntities(Asteroid.class)) {
                detector.checkCollision(player, asteroid, world);
            }
        }

        // Asteroid vs Asteroid (avoid self-collision)
        var asteroids = world.getEntities(Asteroid.class);
        for (int i = 0; i < asteroids.size(); i++) {
            for (int j = i + 1; j < asteroids.size(); j++) {
                detector.checkCollision(asteroids.get(i), asteroids.get(j), world);
            }
        }
    }
}
