package tumtumtumsahur;

public class Sweep {
    private double radius;
    private double x;
    private double y;
    private double dir;
    private double sweep;
    public double damage;

    public Sweep(double x, double y, double dir, double radius, double sweep, double damage) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dir = dir;
        this.sweep = sweep;
        this.damage = damage;
    }

    public boolean collision(Circle c) {
        if (this.radius + c.radius >= Math.hypot(c.x-this.x, c.y-this.y)) {
            double angle = Math.atan2(c.y-this.y, c.x-this.x);
            if ((angle >= (dir-sweep/2) && angle <= (dir+sweep/2)) || (angle-2*Math.PI >= (dir-sweep/2) && angle-2*Math.PI <= (dir+sweep/2)) ) {
                return true;
            }
        }
        return false;
    }

}
