package tumtumtumsahur.Classes;

import java.util.*;

import tumtumtumsahur.*;
import tumtumtumsahur.Projectiles.*;

public class Ice extends Player {

    public Ice(String id, String name, double x, double y) {
        super(id,name, x, y);
        mana_regen = 2.0;
        health_regen = 0.5;
        this.gameClass = "ice";
    }

    //triple icicle shot
    public Set<Projectile> skill_1 (double dir) {
        double manacost = 30.0;
        if (mana <= manacost || skill1cd > 0) {
            return null;
        }
        skill1cd += 10;
        mana -= manacost;
        Set<Projectile> res = new HashSet<Projectile> ();
        for (int i = -1; i <= 1; i++) {
            res.add(new Icicle(UUID.randomUUID().toString(),x,y,dir+i*Math.PI/12, this.id)); 
        }
        return res;
    }

    //spinning blade
    public Set<Projectile>  skill_2 (double dir) {
        double manacost = 50.0;
        if (mana <= manacost || skill2cd > 0) {
            return null;
        }
        skill2cd += 50;
        mana -= manacost;
        String projectileId = UUID.randomUUID().toString();
        return new HashSet<Projectile> (Arrays.asList(new IceBlade(projectileId, this.x, this.y, dir, this.id)));
    }

    //snowstorm
    public Set<Projectile>  skill_3 (double dir) {
        double manacost = 50.0;
        if (mana <= manacost || skill3cd > 0) {
            return null;
        }
        skill3cd += 70;
        mana -= manacost;
        String projectileId = UUID.randomUUID().toString();
        return new HashSet<Projectile> (Arrays.asList(new SnowStorm(projectileId, this.x, this.y, dir, this.id)));
    }

}