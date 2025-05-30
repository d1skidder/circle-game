package tumtumtumsahur.Projectiles;

import tumtumtumsahur.*;

public class ClusterFireball extends Projectile{

    public ClusterFireball(String id, double x_pos, double y_pos, double dir, Player pl) {
        super(id, x_pos, y_pos, 30.0*Math.cos(dir), 30.0*Math.sin(dir), 30.0, pl);
        this.time = 10;
        this.radius = 30.0;
        this.damage = 50.0;
        this.stun_time = 10;
        this.type = "clusterfireball";
    }
}
