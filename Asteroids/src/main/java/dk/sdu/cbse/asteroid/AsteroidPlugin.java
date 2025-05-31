package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePlugin;

public class AsteroidPlugin implements IGamePlugin {

    @Override
    public void start(GameData gd, World w) {
        System.out.println("AsteroidFX");
    }

    @Override
    public void stop(GameData gd, World w) {
        w.getEntities().removeIf(e -> e instanceof Asteroid);
    }

}