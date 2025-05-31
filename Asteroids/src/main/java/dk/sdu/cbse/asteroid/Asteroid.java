package dk.sdu.cbse.asteroid;

import java.util.Random;
import java.util.ServiceLoader;

import dk.sdu.cbse.common.data.Config;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.services.IAsteroidSPI;

public class Asteroid extends Entity {
    public static final double ASTEROID_SIZING = 5;

    private double sizing = ASTEROID_SIZING;
    private int sides = 3;

    public static IAsteroidSPI testSPIOverride = null;

    public Asteroid(double sizing) {
        super();
        this.sizing = sizing;

        this.sides = new Random().nextInt(3, 12);

        createPolygon();

        this.setRadius((int) sizing);
    }

    public Asteroid() {
        this(ASTEROID_SIZING * Config.SIZING);
    }

    private void createPolygon() {
        double[] points = new double[this.sides * 2];
        for (int i = 0; i < this.sides; i++) {
            double angle = Math.toRadians(i * (360.0 / this.sides));
            Random random = new Random();

            double from = -((sizing / 10) * this.sides / 10);
            double to = sizing / 10 * this.sides / 10;

            points[i * 2] = Math.cos(angle) * sizing + random.nextDouble(from, to);
            points[i * 2 + 1] = Math.sin(angle) * sizing + random.nextDouble(from, to);
        }
        this.setPolygon(points);
    }

    @Override
    public void setRadius(int radius) {
        super.setRadius(radius);
        this.sizing = radius;
        this.createPolygon();
    }

    @Override
    public void kill(GameData gameData) {
        super.kill(gameData);

        gameData.addScore((int) (this.getRadius() * 0.1));

        if (this.getRadius() >= ASTEROID_SIZING * Config.SIZING * 1.3) {
            // Use test hook if there, else use ServiceLoader
            IAsteroidSPI spi = testSPIOverride != null
                    ? testSPIOverride
                    : ServiceLoader.load(IAsteroidSPI.class).findFirst().orElse(null);

            if (spi != null) {
                spi.spawnEntity(this);
            }
        }
    }

}