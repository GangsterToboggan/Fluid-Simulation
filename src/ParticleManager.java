import java.util.ArrayList;

import javax.swing.JFrame;

public class ParticleManager extends ArrayList<Particle> {
    // fields
    JFrame frame;
    double maxMomentum;

    // constructors
    public ParticleManager(JFrame frame) {
        this.frame = frame;
    }

    public ParticleManager(Particle[] particles, JFrame frame) {
        for (Particle particle : particles) {
            this.add(particle);
        }
    }

    public ParticleManager(ArrayList<Particle> particles, JFrame frame) {
        this.frame = frame;
        for (Particle particle : particles) {
            this.add(particle);
            maxMomentum += particle.getMass() * (particle.getVelocity().magnitude()
                    + Math.sqrt(2 * Main.ACCELERATION_FROM_GRAVITY.magnitude() * particle.getPos().y()));
        }
    }

    // non-static methods
    public void update(double deltaT) {
        System.out.println("Max momentum:" + this.maxMomentum + " sysMomentum:" + this.calculateSystemMomentum());
        // removeAddedMomentum();
        updatePositions(deltaT);
        updateForces();
    }

    public void updatePositions(double deltaT) {
        for (Particle particle : this) {
            particle.changePosWithTime(deltaT);
        }
    }

    public void removeAddedMomentum() {
        double currentMomentum = calculateSystemMomentum();
        if (currentMomentum > this.maxMomentum) {
            double percentExcess = (currentMomentum - maxMomentum) / currentMomentum;
            double percentCorrect = 1 - percentExcess;
            for (Particle particle : this) {
                particle.getVelocity().multiply(percentCorrect);
            }
        }
    }

    public double calculateSystemMomentum() {
        double sysMomentum = 0;
        for (Particle particle : this) {
            sysMomentum += particle.getMass() * particle.getVelocity().magnitude();
        }
        return sysMomentum;
    }

    public void updateForces() {
        updateCollisions();
        updateInterMolecularForces();
        // updateGravity();
    }

    public void updateCollisions() {
        updateParticleCollions();
        updateBorderCollisions();
    }

    public void updateParticleCollions() {

        for (int i = 0; i < this.size(); i++) {
            for (int j = i + 1; j < this.size(); j++) {
                Particle particle1 = this.get(i);
                Particle particle2 = this.get(j);
                if (particle1.isCollidingWith(particle2)) {
                    particle1.collide(particle2);
                }
            }
        }
    }

    public void updateBorderCollisions() {
        for (Particle particle : this) {
            if ((((particle.getPos().x() - particle.getMass() / 2 < 0)
                    && (particle.getVelocity().dot(new Vec2D(1, 0)) < 0)))
                    || ((particle.getPos().x() + particle.getMass() / 2 > 100)
                            && (particle.getVelocity().dot(new Vec2D(-1, 0)) < 0)))
                particle.getVelocity().setX(particle.getVelocity().x() * -1);

            if ((((particle.getPos().y() - particle.getMass() / 2 < 0)
                    && (particle.getVelocity().dot(new Vec2D(0, 1)) < 0)))
                    || ((particle.getPos().y() + particle.getMass() / 2 > 100)
                            && (particle.getVelocity().dot(new Vec2D(0, -1)) < 0)))
                particle.getVelocity().setY(particle.getVelocity().y() * -1);
        }
    }

    public void updateInterMolecularForces() {

    }

    public void updateGravity() {
        for (Particle particle : this) {
            particle.getVelocity().add(Main.ACCELERATION_FROM_GRAVITY);
        }
    }

    // static methods
}
