package tumtumtumsahur.Projectiles;

import java.util.HashSet;

import tumtumtumsahur.Projectile;

public class Fireball extends Projectile {

    public Fireball(String id, double x_pos, double y_pos, double x_vel, double y_vel, String pl_id) {
        super(id, x_pos, y_pos, x_vel, y_vel, 40.0, pl_id);
        this.time = 50;
        this.radius = 5.0;
        this.damage = 20.0;
    }

    public void update() {
        super.update();
        time--;
    }
}
