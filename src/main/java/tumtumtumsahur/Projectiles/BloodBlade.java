package tumtumtumsahur.Projectiles;

import java.util.HashSet;

import tumtumtumsahur.Projectile;

public class BloodBlade extends Projectile {

    public BloodBlade(String id, double x_pos, double y_pos, double dir, String pl_id) {
        super(id, x_pos, y_pos, 60.0*Math.cos(dir), 60.0*Math.sin(dir), 60.0, pl_id);
        this.time = 5;
        this.radius = 5.0;
        this.damage = 5.0;
        this.type = "bloodblade";
    }

}
