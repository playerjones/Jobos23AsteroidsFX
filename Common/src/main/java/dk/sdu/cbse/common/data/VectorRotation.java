package dk.sdu.cbse.common.data;

public class VectorRotation {
    double x, y;

    public VectorRotation() {
        this.x = 0;
        this.y = 0;
    }
    public VectorRotation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public VectorRotation(VectorRotation vectorRotation) {
        this.x = vectorRotation.x;
        this.y = vectorRotation.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public VectorRotation add(VectorRotation vectorRotation) {
        this.x += vectorRotation.x;
        this.y += vectorRotation.y;
        return this;
    }

    public VectorRotation add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public void addX(double x) {
        this.x += x;
    }
    public void addY(double y) {
        this.y += y;
    }

    public VectorRotation subtract(VectorRotation vectorRotation) {
        this.x -= vectorRotation.x;
        this.y -= vectorRotation.y;
        return this;
    }

    public VectorRotation subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public VectorRotation subtract(double x) {
        this.x -= x;
        this.y -= x;
        return this;
    }

    public void subtractX(double x) {
        this.x -= x;
    }
    public void subtractY(double y) {
        this.y -= y;
    }

    public void multiplySelf(double x) {
        this.x *= x;
        this.y *= x;
    }
    public VectorRotation multiply(double x) {
        this.x *= x;
        this.y *= x;
        return this;
    }

    public VectorRotation round() {
        return new VectorRotation(Math.round(x), Math.round(y));
    }



    public VectorRotation difference(VectorRotation v) {
        return new VectorRotation(this.x - v.x, this.y - v.y);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public VectorRotation normalize() {
        double length = length();
        if (length != 0) {
            x = x / length;
            y = y / length;
        }
        return this;
    }

    public double dot(VectorRotation v) {
        return x * v.x + y * v.y;
    }

    /**
     * Returns the angle in radians between this vector and the given vector.
     * @param v
     * @return
     */
    public double angle(VectorRotation v) {
        return Math.acos(dot(v) / (length() * v.length()));
    }

    public VectorRotation rotate(double degrees) {
        double radians = Math.toRadians(degrees);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double newX = x * cos - y * sin;
        double newY = x * sin + y * cos;
        this.x = newX;
        this.y = newY;
        return this;
    }

    public double cross(VectorRotation v) {
        return x * v.y - y * v.x;
    }
    public void lerp(VectorRotation vector2, float f) {
        this.x = this.x + (vector2.x - this.x) * f;
        this.y = this.y + (vector2.y - this.y) * f;
    }

    public void max(double length) {
        if (this.length() > length) {
            // Can't use normalize() because it will change the vector
            double l = this.length();
            this.x = this.x / l * length;
            this.y = this.y / l * length;
        }
    }



    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public VectorRotation divide(double value) {
        this.x /= value;
        this.y /= value;
        return this;
    }
    public double distance(VectorRotation location) {
        return Math.sqrt(Math.pow(location.x - this.x, 2) + Math.pow(location.y - this.y, 2));
    }
}