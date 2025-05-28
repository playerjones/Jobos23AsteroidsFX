package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.data.Entity;

public class BulletPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        // Nothing for now
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities().removeIf(e -> e instanceof Bullet);
    }
}