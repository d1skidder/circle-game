package arcane;


public abstract class Player extends Circle {    
    String name;
    public double health = 100.0;
    public double mana = 100.0;
    public double mana_regen;
    public double health_regen;

    public int skill1cd = 0;

    // woah weird ass constructor methods
    public Player(String id, String name, double x, double y) {
        super(id, x, y);
        this.name = name;
        this.max_vel = 30.0;
        radius = 20.0;
    }

    //Basic projectile skill
    public abstract Projectile skill_1(double x, double y);

    //add update all stats by time
    public void update() {
        super.update();
        if (mana < 100.0) {
            mana = Math.min(100.0, mana+mana_regen);
        }
        if (health < 100.0) {
            health = Math.min(100.0, health+health_regen);
        }
        if (skill1cd > 0) {
            skill1cd--;
        }
    }

}