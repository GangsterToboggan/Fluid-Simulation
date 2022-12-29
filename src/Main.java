import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Color;

public class Main {

    // the amount of times (per second) that the program updates all of the
    // particles
    public static final int CLOCK_RATE = 60;

    // the amount of times (per second) that the program updates the screen
    public static final int FRAME_RATE = 200;

    // the acceleration from gravity
    public static final int g = 1;

    // gravity vector
    public static final Vec2D ACCELERATION_FROM_GRAVITY = new Vec2D(0, -10);

    public static ArrayList<Particle> geneParticles(int numParticles, double maxX, double minX, double maxY,
            double minY,
            double minVel, double maxVel, double maxMass, double minMass) {
        ArrayList<Particle> particles = new ArrayList<Particle>();
        for (int i = 0; i < numParticles; i++) {
            particles.add(
                    new Particle(new Vec2D(minX + (Math.random() * maxX), minY + (Math.random() * maxY)),
                            new Vec2D(minVel + (Math.random() * maxVel), minVel + (Math.random() * maxVel)),
                            minMass + (Math.random() * maxMass), Color.red));
        }
        return particles;
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setSize(1000, 1000);
        frame.setName("Fluid");
        frame.setTitle("Fluid");
        // ParticleManager mainParticleManager = new ParticleManager(geneParticles(1500,
        // 90, 10, 90, 10, -30, 30, 2, 1), frame);
        ParticleManager mainParticleManager = new ParticleManager(frame);
        mainParticleManager.add(new Particle(new Vec2D(50, 50), new Vec2D(0, 5), 5,
                null));
        // '' mainParticleManager.add(new Particle(new Vec2D(15, 50), new Vec2D(200, 0),
        // 10, null));
        ZackDraw particleBox = new ZackDraw(new Vec2D(frame.getWidth(), frame.getHeight()),
                new Vec2D(frame.getWidth(), frame.getHeight()), frame, mainParticleManager);
        frame.add(particleBox);
        frame.repaint();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        long prevtime = System.currentTimeMillis();
        while (true) {
            Thread.sleep(1000 / FRAME_RATE);
            long ctime = System.currentTimeMillis();
            mainParticleManager.update((ctime - prevtime) / 1000.0);
            prevtime = ctime;
            frame.repaint();

        }

    }
}
