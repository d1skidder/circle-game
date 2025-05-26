package tumtumtumsahur.Classes;

import java.util.UUID;

import tumtumtumsahur.*;
import tumtumtumsahur.Projectiles.*;

public class Fire extends Player {
    public Fire(String id, String name, double x, double y) {
        super(id,name, x, y);
        mana_regen = 2.0;
        health_regen = 1.0;
    }

    public Projectile skill_1 (double dir) {
        double manacost = 10.0;
        if (mana <= manacost || skill1cd > 0) {
            return null;
        }
        skill1cd += 2;
        mana -= manacost;
        String projectileId = UUID.randomUUID().toString();
        return new Fireball(projectileId, this.x, this.y, dir, this.id);
    }

    public Projectile skill_2 (double dir) {
        double manacost = 40.0;
        if (mana <= manacost || skill1cd > 0) {
            return null;
        }
        skill1cd += 5;
        mana -= manacost;
        String projectileId = UUID.randomUUID().toString();
        return new ChonkyFireball(projectileId, this.x, this.y, dir, this.id);
    }

}
