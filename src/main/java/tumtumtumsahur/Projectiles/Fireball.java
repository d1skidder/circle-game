package tumtumtumsahur.Projectiles;

/**
 * small fireball
 */
import tumtumtumsahur.*;

public class Fireball extends Projectile {

    public Fireball(String id, double x_pos, double y_pos, double dir, Player pl) {
        super(id, x_pos, y_pos, 80.0*Math.cos(dir), 80.0*Math.sin(dir), 80.0, pl);
        this.time = 50;
        this.radius = 5.0;
        this.damage = 20.0;
        this.type = "fireball";
    }

}
