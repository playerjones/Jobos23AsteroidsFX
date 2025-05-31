import dk.sdu.cbse.bullet.BulletProcessingService;
import dk.sdu.cbse.bullet.BulletSP;
import dk.sdu.cbse.common.services.IBulletSPI;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IWorldProvider;

module bullet {
    requires Common;
    provides IEntityProcessingService with BulletProcessingService;
    provides IBulletSPI with BulletSP;
    provides IWorldProvider with BulletSP;
}