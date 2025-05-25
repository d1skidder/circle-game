package tumtumtumsahur;

public class Circle {    
    String id;
    double last_x;
    double last_y;
    double x;
    double y;
    double x_vel;
    double y_vel;
    double max_vel;
    static double radius;
    

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
        if ((ref.x-x)*(ref.x-x)+(ref.y-y)*(ref.y-y) <= radius*radius) {
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

    public void updatePosition() {
        last_x = x;
        last_y = y;
        x += x_vel;
        y += y_vel;
    }
}