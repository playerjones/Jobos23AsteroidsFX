import dk.sdu.cbse.asteroid.AsteroidPlugin;
import dk.sdu.cbse.asteroid.AsteroidProcessor;
import dk.sdu.cbse.common.services.IAsteroidSPI;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePlugin;

module Asteroids {
    requires transitive Common;

    provides IGamePlugin with AsteroidPlugin;
    exports dk.sdu.cbse.asteroid;
    uses IAsteroidSPI;
    provides IEntityProcessingService with AsteroidProcessor;
}