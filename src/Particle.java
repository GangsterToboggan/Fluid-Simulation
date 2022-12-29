import java.awt.Color;

public class Particle {
    // fields
    private Vec2D velocity;
    private Vec2D position;
    private double mass;
    private java.awt.Color color;
    private Vec2D sumOfForces = new Vec2D(0, 0);

    // constructors
    public Particle(Vec2D position, double mass, Color color) {
        this.position = position;
        this.velocity = new Vec2D(0, 0);
        this.mass = mass;
        this.color = color;
    }

    public Particle(Vec2D position, Vec2D velocity, double mass, Color color) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.color = color;
    }

    // non-static methods
    public Vec2D getPos() {
        return this.position;
    }

    public void setPos(Vec2D pos) {
        this.position = pos;
    }

    public double getMass() {
        return this.mass;
    }

    public void setSize(double mass) {
        this.mass = mass;
    }

    public void setVelocity(Vec2D velocity) {
        this.velocity = velocity;
    }

    public Vec2D getVelocity() {
        return this.velocity;
    }

    public void setForceSum(Vec2D forceSum) {
        this.sumOfForces = forceSum;
    }

    public Vec2D getForceSum() {
        return this.sumOfForces;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public Color calculateColor() {
        double v = this.getVelocity().magnitude();
        int blue = (int) (-(255 / 100) * v + 255);
        int green = 0;
        int red = (int) ((255 / 100) * v);
        if (v < 100) {
            return new Color(red, green, blue);
        } else {
            return new Color(255, 0, 0);
        }

    }

    public Vec2D changePosWithTime(double deltaT) {
        Vec2D newPos = new Vec2D(this.getPos().x() + deltaT * this.getVelocity().x(),
                this.getPos().y() + deltaT * this.getVelocity().y());
        this.setPos(newPos);
        return newPos;
    }

    public Vec2D changeVelocityWithTime(double deltaT) {
        Vec2D newVel = new Vec2D(this.getVelocity().x() + deltaT * this.getForceSum().x(),
                this.getVelocity().y() + deltaT * this.getForceSum().y());
        this.setVelocity(newVel);
        return newVel;
    }

    public boolean isCollidingWith(Particle particle2) {
        return (Math
                .abs(this.getPos().clone().subtract(particle2.getPos())
                        .magnitude()) < (this.getMass() + particle2.getMass()) / 2.0);
    }

    public void collide(Particle particle2) {
        Vec2D vel1 = this.getVelocity();
        Vec2D vel2 = particle2.getVelocity();
        double m1 = this.getMass();
        double m2 = particle2.getMass();
        Vec2D differenceOfPositions1 = this.getPos().clone().subtract(particle2.getPos());
        Vec2D differenceOfVelocities1 = vel1.clone().subtract(vel2);
        double massRatioCoefficient1 = (2 * m2) / (m1 + m2);
        Vec2D finalVelocity = vel1.clone()
                .subtract(differenceOfPositions1.clone()
                        .multiply((differenceOfVelocities1.clone().dot(differenceOfPositions1) * massRatioCoefficient1)
                                / (differenceOfPositions1.magnitude() * differenceOfPositions1.magnitude())));
        if (finalVelocity.dot(differenceOfPositions1) > finalVelocity.clone().multiply(-1)
                .dot(differenceOfPositions1)) {
            this.setVelocity(finalVelocity);
            particle2.setVelocity(finalVelocity.clone().multiply(-1).add(vel1).multiply(m1 / m2).add(vel2));
        } else {
            this.setVelocity(finalVelocity.multiply(-1));
            particle2.setVelocity(finalVelocity.clone().multiply(-1).add(vel1).multiply(m1 / m2).add(vel2));
        }
        this.extrapolateOutOfCollision(particle2);
    }

    public void extrapolateOutOfCollision(Particle particle2) {
        double distanceBetween = this.getPos().clone().subtract(particle2.getPos()).magnitude();
        double neededDistanceBetween = this.getMass() / 2 + particle2.getMass() / 2;
        double sumOfVelocities = this.getVelocity().magnitude() + particle2.getVelocity().magnitude();
        double neededMovement = neededDistanceBetween - distanceBetween;
        double percentOfVelocities = neededMovement / sumOfVelocities;
        this.changePosWithTime(percentOfVelocities);
        particle2.changePosWithTime(percentOfVelocities);
    }

    // static methods
}