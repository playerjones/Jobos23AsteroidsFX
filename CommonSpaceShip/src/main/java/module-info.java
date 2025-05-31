import dk.sdu.cbse.common.services.IBulletSPI;

module CommonSpaceShip {
    requires Common;

    exports dk.sdu.cbse.commonspaceship;

    uses IBulletSPI;

}
