package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

public class BulletProcessingService implements IEntityProcessingService {

    @Override
    public void process(World world, GameData gameData) {
        for (Bullet bullet : world.getEntities(Bullet.class)) {
            if (bullet.isAlive()) {
                bullet.getLocation().add(bullet.getVelocity());
            } else {
                world.removeEntity(bullet);
            }
            if (bullet.getLocation().getX() < 0 || bullet.getLocation().getX() > gameData.width
                    || bullet.getLocation().getY() < 0 || bullet.getLocation().getY() > gameData.height) {
                bullet.kill(gameData);
            }

            bullet.getLocation().add(bullet.getVelocity());
        }
    }

}