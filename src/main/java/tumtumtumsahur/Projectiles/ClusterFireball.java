package tumtumtumsahur.Projectiles;

import tumtumtumsahur.Projectile;

public class ClusterFireball extends Projectile{

    public ClusterFireball(String id, double x_pos, double y_pos, double dir, String pl_id) {
        super(id, x_pos, y_pos, 20.0*Math.cos(dir), 20.0*Math.sin(dir), 20.0, pl_id);
        this.time = 15;
        this.radius = 30.0;
        this.damage = 60.0;
        this.type = "clusterfireball";
    }
}
