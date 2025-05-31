package dk.sdu.cbse.player;

import dk.sdu.cbse.commonspaceship.CommonSpaceShip;
import javafx.scene.paint.Color;

public class Player extends CommonSpaceShip {

    public Player() {
        super();
        this.getPolygon().setFill(Color.LIGHTGOLDENRODYELLOW);
    }
}