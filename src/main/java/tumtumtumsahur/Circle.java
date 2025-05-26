package tumtumtumsahur;

public class Circle {    
    public String id;
    double last_x;
    double last_y;
    public double x;
    public double y;
    public double x_vel;
    public double y_vel;
    public double max_vel;
    public double radius;
    

    // woah weird ass constructor methods
    Circle(String id, double x, double y) {
        this.id = id;
        this.last_x = x;
        this.last_y = y;
        this.x = x;
        this.y = y;
        this.x_vel = 0;
        this.y_vel = 0;
    }

    public boolean collision (Circle ref) {
        if (Math.hypot(ref.x-x,ref.y-y) <= radius+ref.radius) {
            return true;
        }
        return false;
    }

    public void updateVelocity (double x, double y) {
        double magnitude = Math.hypot(x, y);
        if (magnitude != 0) {
            x /= magnitude; y /= magnitude;
        }
        x_vel = x*max_vel; y_vel = y*max_vel;
    }

    public void update() {
        last_x = x;
        last_y = y;
        x += x_vel;
        y += y_vel;
    }
}