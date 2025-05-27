package tumtumtumsahur.Projectiles;

import java.util.HashSet;

import tumtumtumsahur.Projectile;

public class Fireball extends Projectile {

    public Fireball(String id, double x_pos, double y_pos, double dir, String pl_id) {
        super(id, x_pos, y_pos, 40.0*Math.cos(dir), 40.0*Math.sin(dir), 40.0, pl_id);
        this.time = 50;
        this.radius = 5.0;
        this.damage = 20.0;
        this.type = "fireball";
    }

}
