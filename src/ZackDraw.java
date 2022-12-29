import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

public class ZackDraw extends JPanel {
    public ZackDraw(Vec2D offset, Vec2D dimensions, JFrame frame, ParticleManager particles) {
        this.loc = offset;
        this.particles = particles;
        this.frame = frame;
    }

    public ZackDraw(Vec2D offset, Vec2D dimensions) {
        this.loc = offset;
    }

    Vec2D loc;
    ParticleManager particles;
    JFrame frame;

    public Vec2D getLoc() {
        return this.loc;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Particle particle : particles) {
            g2d.setColor(particle.calculateColor());

            g2d.fillOval((int) (this.getWidth() * ((particle.getPos().x() - particle.getMass() / 2.0) / 100.0)),
                    (int) (this.getHeight() * ((100.0 - particle.getPos().y() - particle.getMass() / 2.0) / 100.0)),
                    (int) (this.getWidth() * (particle.getMass() / 100.0)),
                    (int) (this.getHeight() * (particle.getMass() / 100.0)));
        }
    }

}
