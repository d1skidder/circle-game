package tumtumtumsahur.Projectiles;

import tumtumtumsahur.Projectile;

public class IceBlade extends Projectile {
    public IceBlade(String id, double x_pos, double y_pos, double dir, String pl_id) {
        super(id, x_pos, y_pos, 50.0*Math.cos(dir), 50.0*Math.sin(dir), 50.0, pl_id);
        this.time = 50;
        this.radius = 30.0;
        this.damage = 30.0;
        this.slow = 0.2;
        this.slow_time = 10;
        this.type = "iceblade";
    }

    public void update() {
        if (this.time <= 45) {
            this.x_vel = 0.0; this.y_vel = 0.0;
            if (this.time % 10 == 0) {
                this.hitPlayers.clear();
                this.hitPlayers.add(this.playerID);
            }
        }
        super.update();
    }
}
