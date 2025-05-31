import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePlugin;
import dk.sdu.cbse.enemy.EnemyControlSystem;
import dk.sdu.cbse.enemy.EnemyPluginService;

module Enemy {
    requires Common;
    requires CommonSpaceShip;

    provides IGamePlugin with EnemyPluginService;
    provides IEntityProcessingService with EnemyControlSystem;
}