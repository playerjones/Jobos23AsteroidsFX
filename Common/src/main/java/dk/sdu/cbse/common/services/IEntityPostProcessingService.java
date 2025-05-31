package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

public interface IEntityPostProcessingService {
    /**
     * This method is called to process the entity.
     *
     * @param world
     * @param gameData
     */
    void process(World world, GameData gameData);
}
