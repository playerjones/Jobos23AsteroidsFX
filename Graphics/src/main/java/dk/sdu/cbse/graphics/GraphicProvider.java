package dk.sdu.cbse.graphics;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.services.IGraphicProvider;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import dk.sdu.cbse.commonspaceship.CommonSpaceShip;

public class GraphicProvider implements IGraphicProvider {

    @Override
    public Background getBackground() {
        Image bgImage = new Image(getClass().getResource("/images/gamebackground.png").toExternalForm());
        BackgroundSize size = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                size
        );
        return new Background(backgroundImage);
    }

    @Override
    public Node createSpriteFor(Entity entity) {
        if (entity.getClass().getSimpleName().equals("Player")) {
            System.out.println(getClass().getResource("/images/playerspaceship.png"));
            Image img = new Image(getClass().getResource("/images/playerspaceship.png").toExternalForm());
            ImageView view = new ImageView(img);
            view.setFitWidth(40);
            view.setFitHeight(40);
            view.setPreserveRatio(true);
            return view;
        }

        return null;
    }

}
