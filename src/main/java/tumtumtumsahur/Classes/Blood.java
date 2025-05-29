package tumtumtumsahur.Classes;

import java.util.*;

import tumtumtumsahur.*;
import tumtumtumsahur.Projectiles.*;

public class Blood extends Player {

    public Blood(String id, String name, double x, double y) {
        super(id,name, x, y);
        mana_regen = 2.0;
        health_regen = 1.0;
        this.gameClass = "blood";
    }

    //enhanced melee attack
    public Sweep basicMelee(double dir) {
        if (basicMeleeCD > 0) return null;
        this.basicMeleeCD += 5;
        if (this.frenzy_time == 0) {
            return new Sweep(this.x, this.y, dir, 100.0, Math.PI*1.2, 20.0);
        } else {
            return new Sweep(this.x, this.y, dir, 100.0, Math.PI*1.2, 35.0);
        }
    }

    //shoot bloodblades all around
    public Set<Projectile> skill_1 (double dir) {
        double manacost =20.0;
        if (mana <= manacost || skill1cd > 0) {
            return null;
        }
        skill1cd = 10;
        mana -= manacost;
        Set<Projectile> res = new HashSet<Projectile>();
        for (int i = 0; i < 13; i++) {
            res.add(new BloodBlade(UUID.randomUUID().toString(), x, y, i*2*Math.PI/13, id));
        }
        return res;
    }

    //dash
    public Set<Projectile>  skill_2 (double dir) {
        double manacost =30.0;
        if (mana <= manacost || skill2cd > 0) {
            return null;
        }
        skill2cd = 5;
        mana -= manacost;
        this.x += 50*Math.cos(dir);
        this.y += 50*Math.sin(dir);
        return null;
    }

    //frenzy mode, mode dps/lifesteal but take more damage
    public Set<Projectile>  skill_3 (double dir) {
        double manacost = 40.0;
        if (mana <= manacost || skill3cd > 0) {
            return null;
        }
        skill3cd = 70;
        frenzy_time = 50;
        mana -= manacost;
        return null;
    }

}
