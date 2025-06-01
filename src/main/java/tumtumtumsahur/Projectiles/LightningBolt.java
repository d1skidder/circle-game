package tumtumtumsahur.Projectiles;

import tumtumtumsahur.*;


public class LightningBolt extends Projectile {
    public LightningBolt(String id, double x_pos, double y_pos, double dir, Player pl) {
        super(id, x_pos, y_pos, 150.0*Math.cos(dir), 150.0*Math.sin(dir), 150.0, pl);
        this.time = 8;
        this.radius = 10.0;
        this.damage = 50.0;
        this.type = "lightningbolt";
    }

    public void update() {
        this.damage += 5;
        super.update();
    }
}
