package dk.sdu.cbse.player;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePlugin;

public class PlayerPlugin implements IGamePlugin {
    @Override
    public void start(GameData gd, World w) {
        Player player = new Player();
        player.setHealth(5);
        player.setLocation(gd.width / 2, gd.height / 2);

        w.addEntity(player);
    }

    @Override
    public void stop(GameData gd, World w) {
        for (Player player : w.getEntities(Player.class)) {
            w.removeEntity(player);
        }
    }
}