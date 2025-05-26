package tumtumtumsahur.Classes;

import java.util.UUID;

import tumtumtumsahur.*;
import tumtumtumsahur.Projectiles.*;

public class Fire1 extends Player {
    public Fire1(String id, String name, double x, double y) {
        super(id,name, x, y);
        mana_regen = 2.0;
        health_regen = 1.0;
    }

    public Projectile skill_1 (double ptr_x, double ptr_y) {
        if (mana <= 20.0 || skill1cd > 0) {
            return null;
        }
        skill1cd += 2;
        ptr_x -= this.x;
        ptr_y -= this.y;
        mana -= 20.0;
        String projectileId = UUID.randomUUID().toString();
        return new Fireball(projectileId, this.x, this.y, ptr_x, ptr_y, this.id);
    }

}
