import dk.sdu.cbse.common.services.*;
import dk.sdu.cbse.player.*;

module player {
    requires Common;
    requires CommonSpaceShip;

    provides IGamePlugin with PlayerPlugin;
    provides IEntityProcessingService with PlayerControlSystem;
    uses IBulletSPI;
}