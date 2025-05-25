package tumtumtumsahur;
import java.util.*;

public class Projectile extends Circle {
    public int time = 50;
    public double damage = 20.0;
    public Set<String> hitPlayers;
    private String playerID;
    Projectile(String id, double x, double y, double x_vel, double y_vel, String pl_id) {
        super(id, x, y);
        hitPlayers = new HashSet<String>();
        playerID = pl_id;
        hitPlayers.add(pl_id);
        this.max_vel = 40.0;
        radius = 2.0;
        updateVelocity(x_vel, y_vel);
    }
}
