package dk.sdu.cbse.asteroid;

import java.util.Random;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;


public class AsteroidPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        int size = rnd.nextInt(10) + 20; 
        double[] coords = new double[16];
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45);
            double radius = size + rnd.nextInt(6) - 3; 
            coords[i * 2] = Math.cos(angle) * radius;
            coords[i * 2 + 1] = Math.sin(angle) * radius;
        }
        asteroid.setPolygonCoordinates(coords);
        asteroid.setX(0);
        asteroid.setY(0);
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(90));
        return asteroid;
    }
}
