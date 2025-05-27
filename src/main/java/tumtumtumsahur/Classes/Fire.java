package tumtumtumsahur.Classes;

import java.util.*;

import tumtumtumsahur.*;
import tumtumtumsahur.Projectiles.*;

public class Fire extends Player {

    public Fire(String id, String name, double x, double y) {
        super(id,name, x, y);
        mana_regen = 2.0;
        health_regen = 0.5;
    }

    //small fireball
    public Set<Projectile> skill_1 (double dir) {
        double manacost = 10.0;
        if (mana <= manacost || skill1cd > 0) {
            return null;
        }
        skill1cd += 2;
        mana -= manacost;
        String projectileId = UUID.randomUUID().toString();
        return new HashSet<Projectile> (Arrays.asList(new Fireball(projectileId, this.x, this.y, dir, this.id)));
    }

    //larger fireball
    public Set<Projectile>  skill_2 (double dir) {
        double manacost = 40.0;
        if (mana <= manacost || skill2cd > 0) {
            return null;
        }
        skill2cd += 5;
        mana -= manacost;
        String projectileId = UUID.randomUUID().toString();
        return new HashSet<Projectile> (Arrays.asList(new ChonkyFireball(projectileId, this.x, this.y, dir, this.id)));
    }

    //big slow cluster fireball
    public Set<Projectile>  skill_3 (double dir) {
        double manacost = 80.0;
        if (mana <= manacost || skill3cd > 0) {
            return null;
        }
        skill3cd += 5;
        mana -= manacost;
        String projectileId = UUID.randomUUID().toString();
        return new HashSet<Projectile> (Arrays.asList(new ClusterFireball(projectileId, this.x, this.y, dir, this.id)));
    }

}
