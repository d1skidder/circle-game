package tumtumtumsahur.Projectiles;

import tumtumtumsahur.Projectile;

public class Icicle extends Projectile {
    public Icicle(String id, double x_pos, double y_pos, double dir, String pl_id) {
        super(id, x_pos, y_pos, 40.0*Math.cos(dir), 40.0*Math.sin(dir), 40.0, pl_id);
        this.time = 50;
        this.radius = 5.0;
        this.damage = 15.0;
        this.slow = 0.5;
        this.slow_time = 20;
        this.type = "icicle";
    }
}
