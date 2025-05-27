package tumtumtumsahur.Projectiles;

import tumtumtumsahur.Projectile;

public class ChonkyFireball extends Projectile {

    public ChonkyFireball(String id, double x_pos, double y_pos, double dir, String pl_id) {
        super(id, x_pos, y_pos, 50.0*Math.cos(dir), 50.0*Math.sin(dir), 50.0, pl_id);
        this.time = 50;
        this.radius = 20.0;
        this.damage = 50.0;
        this.type = "chonkyfireball";
    }

}
