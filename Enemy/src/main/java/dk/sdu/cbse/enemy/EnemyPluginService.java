package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePlugin;

public class EnemyPluginService implements IGamePlugin {

    @Override
    public void start(GameData gd, World w) {
    }

    @Override
    public void stop(GameData gd, World w) {
        for (Enemy enemy : w.getEntities(Enemy.class)) {
            w.removeEntity(enemy);
        }
    }

}