package tumtumtumsahur.Projectiles;

import tumtumtumsahur.Projectile;

public class ChonkyFireball extends Projectile {

    public ChonkyFireball(String id, double x_pos, double y_pos, double dir, String pl_id) {
        super(id, x_pos, y_pos, 40.0*Math.cos(dir), 40.0*Math.sin(dir), 40.0, pl_id);
        this.time = 50;
        this.radius = 20.0;
        this.damage = 35.0;
        this.type = "chonkyfireball";
    }

}
