package tumtumtumsahur;

public class Player extends Circle {    
    String name;
    public double health = 100.0;

    // woah weird ass constructor methods
    Player(String id, String name, double x, double y) {
        super(id, x, y);
        this.name = name;
        this.max_vel = 30.0;
        radius = 20.0;
    }
}