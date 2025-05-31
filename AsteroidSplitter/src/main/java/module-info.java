import dk.sdu.cbse.asteroidsplitter.AsteroidSplitter;
import dk.sdu.cbse.common.services.IAsteroidSPI;
import dk.sdu.cbse.common.services.IWorldProvider;

module AsteroidSplitter {
    requires Common;

    provides IAsteroidSPI with AsteroidSplitter;
    provides IWorldProvider with AsteroidSplitter;
}