package dk.sdu.cbse.player;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.VectorRotation;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.GameData.Keys;
import dk.sdu.cbse.common.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService{

    @Override
    public void process(World world, GameData gameData) {

        for (Player player : world.getEntities(Player.class)) {
            if (player.isAlive()) {

                player.getLocation().add(player.getVelocity());

                if (player.getLocation().getX() < 0) {
                    player.getLocation().setX(gameData.width - 1);
                } else if (player.getLocation().getX() > gameData.width) {
                    player.getLocation().setX(1);
                }
                if (player.getLocation().getY() < 0) {
                    player.getLocation().setY(gameData.height - 1);
                } else if (player.getLocation().getY() > gameData.height) {
                    player.getLocation().setY(1);
                }

                if (gameData.isDown(Keys.UP)) {
                    player.accelerate();
                }
                if (gameData.isDown(Keys.RIGHT)) {
                    player.rotateRight();
                }
                if (gameData.isDown(Keys.LEFT)) {
                    player.rotateLeft();
                }
                if (gameData.isPressed(Keys.SPACE)) {
                    player.shoot();
                }

                player.getVelocity().lerp(new VectorRotation(0,0), 0.02f);

            }
        }

    }

}