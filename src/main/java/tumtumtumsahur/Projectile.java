package tumtumtumsahur;

public class Projectile extends Circle {
    public int time = 50;
    Projectile(String id, double x, double y, double x_vel, double y_vel) {
        super(id, x, y);
        this.max_vel = 40.0;
        radius = 2.0;
        updateVelocity(x_vel, y_vel);
    }
}
