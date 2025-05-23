import dk.sdu.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;
    exports dk.sdu.cbse.collision;
    provides IPostEntityProcessingService with dk.sdu.cbse.collision.CollisionSystem;
}