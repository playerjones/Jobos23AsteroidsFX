package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.Config;
import javafx.scene.shape.Polygon;

public class Bullet extends Entity {
    public static final double BULLET_SIZING = 1.5;
    private static Polygon polygon;
    public Bullet() {
        super();
        // Square
        if (polygon == null) {
            double sizing = BULLET_SIZING * Config.SIZING;
            int sides = 6;

            // Create a polygon with random sides
            double[] points = new double[sides * 2];
            for (int i = 0; i < sides; i++) {
                double angle = Math.toRadians(i * (360.0 / sides));
                points[i * 2] = Math.cos(angle) * sizing;
                points[i * 2 + 1] = Math.sin(angle) * sizing;
            }
            Bullet.polygon = new Polygon(points);
        }
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(Bullet.polygon.getPoints());
        this.setPolygon(polygon);
        double sizing = BULLET_SIZING * Config.SIZING;
        this.setRadius((int) sizing);

    }
}
