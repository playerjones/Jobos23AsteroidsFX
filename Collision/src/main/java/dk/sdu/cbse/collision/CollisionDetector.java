package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.Entity;

public class CollisionDetector {

    /**
     * Checks if two entities are colliding using the Pythagorean theorem (distance between centers).
     * Assumes each entity has a radius.
     */
    public static boolean isColliding(Entity a, Entity b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (a.getRadius() + b.getRadius());
    }
    
    public void checkCollision(Entity entity1, Entity entity2, dk.sdu.cbse.common.data.World world) {
        if (isColliding(entity1, entity2)) {
            world.removeEntity(entity1);
            world.removeEntity(entity2);
        }
    }
}
