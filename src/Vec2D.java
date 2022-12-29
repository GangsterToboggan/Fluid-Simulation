import javafx.stage.Stage;

public class Vec2D {
    // fields
    private double x;
    private double y;

    // constructors
    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2D clone() {
        return new Vec2D(x, y);
    }

    // non-static methods

    /**
     * @return the x value of this vector
     */
    public double x() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    /**
     * a getter method for the x value of this vector however this one adjusts for
     * javafx such that x=0 is on the right side
     * 
     * @param stage the screen so that we can get its width
     * @return x value adjusted for javafx
     */
    public double x(Stage stage) {
        return 0;
    }

    /**
     * @return the y value of this vector
     */
    public double y() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * a getter method for the y valye of this vector however this one adjusts for
     * javafx such that y=0 is on the bottom
     * 
     * @param stage the screen so that we can get its height
     * @return y value adjusted for javafx
     */
    public double y(Vec2D offset, Vec2D dimensions) {
        return 0;
    }

    /**
     * @param vec2D the other vector to do the dot product with
     * @return the dot product of both vectors
     */
    public double dot(Vec2D vec2) {
        return this.x() * vec2.x() + this.y() * vec2.y();
    }

    /**
     * @return magnitude of this vector
     */
    public double magnitude() {
        return Math.sqrt(this.x() * this.x() + this.y() * this.y());
    }

    public Vec2D add(Vec2D other) {
        this.x += other.x;
        this.y += other.y;
        return new Vec2D(x, y);
    }

    public Vec2D subtract(Vec2D other) {
        this.x -= other.x;
        this.y -= other.y;
        return new Vec2D(x, y);
    }

    public void sum(Vec2D... others) {
        for (Vec2D other : others) {
            this.add(other);
        }
    }

    public Vec2D multiply(double coefficient) {
        this.x *= coefficient;
        this.y *= coefficient;
        return new Vec2D(x, y);
    }

    public Vec2D divide(double denominator) {
        this.x /= denominator;
        this.y /= denominator;
        return new Vec2D(x, y);
    }

    public Vec2D unit() {
        this.setX(this.x() / this.magnitude());
        this.setY(this.y() / this.magnitude());
        return new Vec2D(x(), y());
    }

    public Vec2D sqrt() {
        this.x = Math.sqrt(x);
        this.y = Math.sqrt(y);
        return new Vec2D(x, y);
    }

    // static methods
}