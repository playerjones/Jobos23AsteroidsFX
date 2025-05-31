package dk.sdu.cbse.asteroid;

import java.util.List;
import java.util.Random;

import dk.sdu.cbse.common.data.Config;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.VectorRotation;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

public class AsteroidProcessor implements IEntityProcessingService {

    @Override
    public void process(World world, GameData gameData) {
        List<Asteroid> asteroids = world.getEntities(Asteroid.class);

        if (asteroids.stream().filter(e -> e.getRadius() > dk.sdu.cbse.asteroid.Asteroid.ASTEROID_SIZING * Config.SIZING).count() < 5) {
            spawnAsteroid(world, gameData);
        }

        for (Asteroid asteroid : asteroids) {
            if (asteroid.isAlive()) {
                asteroid.getLocation().add(asteroid.getVelocity());
                // Random based on id
                Random random = new Random(asteroid.getId().hashCode());
                float angularVelocity = random.nextInt(-1, 2);
                // the bigger the asteroid, the slower it rotates
                angularVelocity = (float) (angularVelocity * Math.pow(asteroid.getRadius(), -0.5));
                // The faster the asteroid, the faster it rotates
                angularVelocity = (float) (angularVelocity * Math.pow(asteroid.getVelocity().length(), 0.5));
                asteroid.setRotation(asteroid.getRotation() + angularVelocity);
            } else {
                world.removeEntity(asteroid);
            }
            if (asteroid.getLocation().getX() < 0 || asteroid.getLocation().getX() > gameData.width
                    || asteroid.getLocation().getY() < 0 || asteroid.getLocation().getY() > gameData.height) {
                asteroid.setAlive(false);
            }
        }
    }

    private void spawnAsteroid(World world, GameData gameData) {
        Asteroid asteroid = new Asteroid();
        double sizing = dk.sdu.cbse.asteroid.Asteroid.ASTEROID_SIZING * Config.SIZING;
        asteroid.setRadius((int) (Math.random() * (sizing * 2) + sizing));

        VectorRotation location = new VectorRotation(1, 1);

        if (Math.random() < 0.5) {
            location.setX(Math.random() * gameData.width);
            if (Math.random() < 0.5) {
                location.setY(0);
            } else {
                location.setY(gameData.height);
            }
        } else {
            location.setY(Math.random() * gameData.height);
            if (Math.random() < 0.5) {
                location.setX(0);
            } else {
                location.setX(gameData.width);
            }
        }

        // Point towards middle of the screen with variation
        VectorRotation direction = new VectorRotation(gameData.width / 2, gameData.height / 2).subtract(location);
        direction = direction.normalize();
        direction.rotate(Math.random() * 40 - 20); // Random rotation between -20 and 20 degrees
        asteroid.setVelocity(direction.multiply(Math.random() + 1)); // Random speed between 1 and 2

        asteroid.setLocation(location);
        asteroid.setRotation(Math.random() * 360);
        asteroid.setHealth(Math.ceilDiv(asteroid.getRadius(), (int) sizing));
        asteroid.setAlive(true);

        world.addEntity(asteroid);
    }

}