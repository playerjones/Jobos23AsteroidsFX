package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.commonspaceship.CommonSpaceShip;

public class Enemy extends CommonSpaceShip {

    public Enemy() {
        super();
        this.getPolygon().setFill(javafx.scene.paint.Color.RED);
        this.maxSpeed = 2;
    }

    @Override
    public void kill(GameData gameData) {
        super.kill(gameData);
        // 5 points for killing an enemy
        gameData.addScore(5);
    }

}