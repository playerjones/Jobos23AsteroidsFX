package dk.sdu.cbse.asteroidsplitter;

import java.util.Random;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.VectorRotation;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IAsteroidSPI;
import dk.sdu.cbse.common.services.IWorldProvider;

public class AsteroidSplitter implements IAsteroidSPI, IWorldProvider {

    private static World world;

    @Override
    public void provideWorld(World world) {
        AsteroidSplitter.world = world;
    }

    @Override
    public void spawnEntity(Entity entity) {
        int parentRadius = entity.getRadius();

        int numChildren = new Random().nextInt(2, Math.max(4, Math.ceilDiv(parentRadius, 3)));


        VectorRotation location = new VectorRotation(entity.getLocation());
        VectorRotation direction = new VectorRotation(0, 1);
        direction.rotate(Math.random() * 360);


        for (int i = 0; i < numChildren; i++) {

            int childRadius = parentRadius / 2;
            try {
                Entity newAsteroid = entity.getClass().getConstructor().newInstance();
                newAsteroid.setRadius(childRadius);
                newAsteroid.setHealth(Math.ceilDiv(childRadius, parentRadius));

                VectorRotation newLocation = new VectorRotation(location).add(new VectorRotation(direction).multiply(parentRadius));

                newAsteroid.setLocation(newLocation);

                newAsteroid.setVelocity(new VectorRotation(direction).rotate(Math.random() * 20 - 10).multiply(Math.max(0.2, entity.getVelocity().length() / 2)).add(new VectorRotation(entity.getVelocity()).divide(numChildren)));
                newAsteroid.setRotation(Math.random() * 360);

                AsteroidSplitter.world.addEntity(newAsteroid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            direction.rotate(360.0 / numChildren);
        }
    }

}