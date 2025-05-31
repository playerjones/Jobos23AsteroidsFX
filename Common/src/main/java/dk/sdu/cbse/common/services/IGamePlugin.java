package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

public interface IGamePlugin {
    /**
     * This method is called when the plugin is loaded.
     * @param gd
     * @param w
     */
    void start(GameData gd, World w);

    /**
     * This method is called when the plugin is unloaded.
     * @param gd
     * @param w
     */
    void stop(GameData gd, World w);
}
