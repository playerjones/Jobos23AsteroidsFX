import dk.sdu.cbse.common.services.IEntityPostProcessingService;

module Collision {
    requires Common;

    provides IEntityPostProcessingService with dk.sdu.cbse.collision.CollisionSystem;
}