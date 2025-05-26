package tumtumtumsahur;
import java.util.*;

public abstract class Projectile extends Circle {
    public int time;
    public double damage;
    public Set<String> hitPlayers;
    private String playerID;

    public Projectile(String id, double x, double y, double x_vel, double y_vel, double vel, String pl_id) {
        super(id, x, y);
        hitPlayers = new HashSet<String>();
        playerID = pl_id;
        max_vel = vel;
        hitPlayers.add(pl_id);
        updateVelocity(x_vel, y_vel);
    }


}
