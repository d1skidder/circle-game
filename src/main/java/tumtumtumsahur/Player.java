package tumtumtumsahur;

import java.util.*;


public abstract class Player extends Circle {    
    String name;
    public double health = 100.0;
    public double mana = 100.0;
    public double mana_regen;
    public double health_regen;
    public double last_dir;
    public double dir;
    public double timeFromLastHit;
    public boolean isHitting = false;
    public double slow;

    //timers
    public int skill1cd = 0;
    public int skill2cd = 0;
    public int skill3cd = 0;
    public int basicMeleeCD = 0;
    public int slow_time = 0;

    // woah weird ass constructor methods
    public Player(String id, String name, double x, double y) {
        super(id, x, y);
        this.name = name;
        this.max_vel = 30.0;
        radius = 20.0;
    }

    //if type is projectile return projectile set, if melee return sweep
    //1st skill
    public String skill_1_type;
    public abstract Object skill_1(double dir);

    //2nd skill
    public String skill_2_type;
    public abstract Object skill_2(double dir);

    //3rd skill
    public String skill_3_type;
    public abstract Object skill_3(double dir);

    //Basic melee attack
    public Sweep basicMelee(double dir) {
        if (basicMeleeCD > 0) return null;
        this.basicMeleeCD += 8;
        return new Sweep(this.x, this.y, dir, 100.0, Math.PI*1.2, 20.0);
    }


    //add update all stats by time
    public void update() {
        if (this.slow_time > 0) {
            this.x_vel *= slow;
            this.y_vel *= slow;
        }
        super.update();
        if (mana < 100.0) {
            mana = Math.min(100.0, mana+mana_regen);
        }
        if (isHitting && System.currentTimeMillis() - timeFromLastHit >= 200) {
            isHitting = false;
        }  
        if (health < 100.0) {
            health = Math.min(100.0, health+health_regen);
        }
        if (skill1cd > 0) {
            skill1cd--;
        }
        if (skill2cd > 0) {
            skill1cd--;
        }
        if (skill3cd > 0) {
            skill1cd--;
        }
        if (basicMeleeCD > 0) {
            basicMeleeCD--;
        }
        if (slow_time > 0) {
            slow_time--;
        }
    }

}
