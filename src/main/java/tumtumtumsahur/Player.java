package tumtumtumsahur;

public class Player extends Circle {    
    String name;
    double health;

    // woah weird ass constructor methods
    Player(String id, String name, double x, double y) {
        super(id, x, y);
        this.name = name;
        this.max_vel = 30.0;
        radius = 20.0;
    }

    public void updatePosition() {
        last_x = x;
        last_y = y;
        x += x_vel;
        y += y_vel;
        x_vel = 0;
        y_vel = 0;
    }
}