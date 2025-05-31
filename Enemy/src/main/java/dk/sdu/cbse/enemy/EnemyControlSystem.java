package dk.sdu.cbse.enemy;

import java.util.List;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.VectorRotation;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

public class EnemyControlSystem implements IEntityProcessingService {

    @Override
    public void process(World world, GameData gameData) {
        List<Enemy> enemies = world.getEntities(Enemy.class);

        if (enemies.stream().count() < 5) {
            spawnEnemy(world, gameData);
        }

        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                world.removeEntity(enemy);
                continue;
            }
            if (enemy.getLocation().getX() < 0 || enemy.getLocation().getX() > gameData.width
                    || enemy.getLocation().getY() < 0 || enemy.getLocation().getY() > gameData.height) {
                enemy.setAlive(false);
                continue;
            }

            enemy.accelerate();

            if (Math.random() < 0.5) {
                enemy.rotateLeft();
            } else {
                enemy.rotateRight();
            }

            // Low chance for enemy to fire
            if (Math.random() < 0.01) {
                enemy.shoot();
            }

            enemy.getLocation().add(enemy.getVelocity());

            enemy.getVelocity().lerp(new VectorRotation(0,0), 0.02f);
        }

    }
    private void spawnEnemy(World world, GameData gameData) {
        Enemy enemy = new Enemy();

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


        VectorRotation direction = new VectorRotation(gameData.width / 2, gameData.height / 2).subtract(location);
        direction = direction.normalize();
        direction.rotate(Math.random() * 40 - 20);
        enemy.setVelocity(direction.multiply(Math.random() + 1));

        enemy.setLocation(location);
        enemy.setRotation(new VectorRotation(direction).normalize().rotate(90));
        enemy.setHealth(2);
        enemy.setAlive(true);

        world.addEntity(enemy);
    }
}