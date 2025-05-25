package tumtumtumsahur;

public class Projectile extends Circle {
    Projectile(String id, double x, double y) {
        super(id, x, y);
        this.max_vel = 50.0;
        radius = 2.0;
    }
}
