package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.Entity;
import javafx.scene.Node;
import javafx.scene.layout.Background;

public interface IGraphicProvider {
    Background getBackground();
    Node createSpriteFor(Entity entity);
}
