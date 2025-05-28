package tumtumtumsahur.Projectiles;

import tumtumtumsahur.Projectile;

public class SnowStorm extends Projectile {
    public SnowStorm(String id, double x_pos, double y_pos, double dir, String pl_id) {
        super(id, x_pos, y_pos, 0, 0, 0, pl_id);
        this.time = 50;
        this.radius = 150.0;
        this.damage = 10.0;
        this.slow = 0.5;
        this.slow_time = 10;
        this.type = "snowstorm";
    }

    public void update() {
        if (this.time % 10 == 0) {
            this.hitPlayers.clear();
            this.hitPlayers.add(this.playerID);
        }
        super.update();
    }
}
