package dk.sdu.cbse.player;

import dk.sdu.cbse.commonspaceship.CommonSpaceShip;

public class Player extends CommonSpaceShip {

    public Player() {
        super();
        this.getPolygon().setFill(javafx.scene.paint.Color.BLUE);
    }
}