package dk.sdu.cbse.bullet;
import dk.sdu.cbse.common.data.Config;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.VectorRotation;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IBulletSPI;
import dk.sdu.cbse.common.services.IWorldProvider;

public class BulletSP implements IBulletSPI, IWorldProvider {

    private static World world;

    @Override
    public void provideWorld(World world) {
        BulletSP.world = world;
    }

    @Override
    public void spawnEntity(Entity parent) {
        Bullet bullet = new Bullet();

        double changeX = Math.cos(Math.toRadians(parent.getRotation() -90));
        double changeY = Math.sin(Math.toRadians(parent.getRotation() -90));

        VectorRotation direction = new VectorRotation(0, -1).rotate(parent.getRotation());

        bullet.setLocation(new VectorRotation(parent.getLocation()).add(new VectorRotation(direction).multiply(parent.getRadius()*2)));
        bullet.getLocation().subtract((Bullet.BULLET_SIZING * Config.SIZING) / 2);

        bullet.getVelocity().add(new VectorRotation(changeX, changeY)).multiply(5).max(5);

        BulletSP.world.addEntity(bullet);
    }

}