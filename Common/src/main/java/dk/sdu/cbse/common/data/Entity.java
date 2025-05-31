package dk.sdu.cbse.common.data;

import java.util.UUID;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class Entity {

    UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    private boolean isAlive = true;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void kill(GameData gameData) {
        this.isAlive = false;
    }

    private int health;
    private int radius;
    private VectorRotation location = new VectorRotation();
    private VectorRotation velocity = new VectorRotation(0, 0);
    private double rotation;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public VectorRotation getLocation() {
        return location;
    }

    public void setLocation(VectorRotation location) {
        this.location = location;
    }

    public void setLocation(double x, double y) {
        this.location.setX(x);
        this.location.setY(y);
    }

    public VectorRotation getVelocity() {
        return velocity;
    }

    public void setVelocity(VectorRotation velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double x, double y) {
        this.velocity.setX(x);
        this.velocity.setY(y);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setRotation(VectorRotation direction) {
        this.rotation = Math.toDegrees(direction.angle(new VectorRotation(1, 0)));
    }

    private Polygon polygon;

    public Polygon getPolygon() {
        return this.polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
        this.polygon.setFill(Paint.valueOf("white"));
    }

    public void setPolygon(double... points) {
        this.setPolygon(new Polygon(points));
    }

    public void Render(GraphicsContext gc) {
        this.polygon.setTranslateX(this.location.getX());
        this.polygon.setTranslateY(this.location.getY());
        this.polygon.setRotate(this.getRotation());

        if (Config.DEBUG) {
            gc.save();
            gc.setStroke(Paint.valueOf("red"));
            gc.translate(this.location.getX(), this.location.getY());
            gc.rotate(this.getRotation());
            gc.strokeOval(-this.radius, -this.radius, this.radius * 2, this.radius * 2);
            gc.restore();
        }
    }

    @Override
    public String toString() {

        return String.format(
                "%s:%s:%s:%s:%s:%s:%s:%s:%s",
                this.getClass().getSimpleName(),
                this.id.toString(),
                String.valueOf(this.isAlive),
                String.valueOf(this.health),
                String.valueOf(this.radius),
                this.location.toString(),
                this.velocity.toString(),
                String.valueOf(this.rotation),
                this.polygon.getPoints().toString()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Entity other = (Entity) obj;
        return this.id.equals(other.id);
    }
}