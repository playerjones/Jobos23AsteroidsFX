package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.World;

public interface IWorldProvider {

    /**
     * This method is called to provide the world so the implementation can
     * spawn entities.
     *
     * @param world
     */
    void provideWorld(World world);

}
