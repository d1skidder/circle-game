package tumtumtumsahur;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tumtumtumsahur.Classes.*;
import tumtumtumsahur.Projectiles.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer extends WebSocketServer {
    private final ObjectMapper objectMapper;
    private final Map<WebSocket, Player> players;
    private final Set<Projectile> projectiles;
    private final Timer gameLoopInterval;


    public GameServer() {
        super(new InetSocketAddress("0.0.0.0", getEnvPort()));
        //super(new InetSocketAddress("localhost", 8080));
        this.objectMapper = new ObjectMapper();
        this.players = new HashMap<>();
        this.projectiles = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.gameLoopInterval = new Timer(true);
    }

    private static int getEnvPort() {
        String portEnv = System.getenv("PORT");
        return portEnv != null ? Integer.parseInt(portEnv) : 8887;
    }

    @Override
    public void onStart() {
        setConnectionLostTimeout(100);

        // set game update loop
        gameLoopInterval.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //System.out.println("UPDATE RUN — NO MOVEMENT");
                gameLoop();
            }
        }, 0, 100);
    }

    @Override
    public void onOpen(WebSocket ws, ClientHandshake hnsk) {
        String playerID = UUID.randomUUID().toString();
        System.out.println("Player: " + playerID + " has joined the game");
        players.put(ws, new Ice(playerID,"hi", 0, 0));

        // tell res tof clients new player spawned
        ObjectNode response = objectMapper.createObjectNode();
        response.put("type", "init");
        response.put("id", playerID);
        ws.send(response.toString());
    }

    private void handleMovement(WebSocket ws, JsonNode jsonNode) {
        if (jsonNode.get("x") == null || jsonNode.get("x").isNull()) return;
        if (jsonNode.get("y") == null || jsonNode.get("y").isNull()) return;
        if (jsonNode.get("dir") == null || jsonNode.get("dir").isNull()) return;
        double x = jsonNode.get("x").asDouble(); // x component
        double y = jsonNode.get("y").asDouble(); // y component
        double dir = jsonNode.get("dir").asDouble(); // mouse direction

        Player player = players.get(ws);
        if (player != null) {
            player.updateVelocity(x, y);
            player.last_dir = player.dir;
            player.dir = dir;
        }
    }  
    
    private void createProjectile(Set<Projectile> newproj) {
        if (newproj == null) {
            return;
        }
        for (Projectile proj : newproj) {
            projectiles.add(proj);
        }
    }

    private void meleeAttack(Sweep swp, Player pl, double time) {
        if(pl.isHitting == false) {
            pl.isHitting = true;
            pl.timeFromLastHit = time;
        }
        if (swp != null) {
            for (WebSocket oppws : players.keySet()) {
                Player opp = players.get(oppws);
                if (opp.id != pl.id && swp.collision(opp)) {
                    opp.health -= swp.damage;
                    if (opp.health <= 0.0) {
                        players.remove(oppws);
                        return;
                    }
                }
            }
        }
    }

    private void handleAttack(WebSocket ws, JsonNode jsonNode) {
        if (jsonNode.get("move") == null || jsonNode.get("move").isNull()) return;
        if (jsonNode.get("dir") == null || jsonNode.get("dir").isNull()) return;

        String move = jsonNode.get("move").asText(); //which attack is being used
        double dir = jsonNode.get("dir").asDouble(); // mouse direction
        double time = 0; //get time
        if (jsonNode.get("time") != null) {
            time = jsonNode.get("time").asDouble();
        }

        Player pl = players.get(ws);
        System.out.println(pl.skill_1_type);
        if (pl == null) return;
        switch (move) {
            case "basicMelee":
                meleeAttack(pl.basicMelee(dir), pl, time);
                break;
            case "skill1":
                if (pl.skill_1_type.equals("projectile")) {
                    createProjectile((Set<Projectile>) pl.skill_1(dir));
                } 
                else if (pl.skill_1_type.equals("melee")) {
                    meleeAttack((Sweep)pl.skill_1(dir), pl, time);
                }
                break;
            case "skill2":
                if (pl.skill_2_type.equals("projectile")) {
                    createProjectile((Set<Projectile>) pl.skill_2(dir));
                } 
                else if (pl.skill_2_type.equals("melee")) {
                    meleeAttack((Sweep)pl.skill_2(dir), pl, time);
                }
                break;
            case "skill3":
                if (pl.skill_2_type.equals("projectile")) {
                    createProjectile((Set<Projectile>) pl.skill_3(dir));
                } 
                else if (pl.skill_2_type.equals("melee")) {
                    meleeAttack((Sweep)pl.skill_3(dir), pl, time);
                }
                break;
            default:
                System.out.println("unknown move " + move);
                break;
        }   
    }

    private void projectileCollisions(WebSocket ws) {
        Player pl = players.get(ws);
        if (pl == null) return;
        for (Projectile proj : projectiles) {
            if (!proj.hitPlayers.contains(pl.id) && pl.collision(proj)) {
                pl.health -= proj.damage;
                proj.hitPlayers.add(pl.id);
                if (pl.health <= 0.0) {
                    players.remove(ws);
                    return;
                }
                //check projectile effects
                pl.slow = proj.slow;
                pl.slow_time = proj.slow_time;
            }
        }
    }


    @Override    
    public void onMessage(WebSocket ws, String msg) {
        try {
            JsonNode jsonNode = objectMapper.readTree(msg); // what da hell is this
            String type = jsonNode.get("type").asText();

            // wtf this is just js on steroids
            switch (type) {
                case "ping":
                    handlePingMessage(ws, jsonNode);
                    break;
                case "move":
                    handleMovement(ws, jsonNode);
                    break;
                case "attack":
                    handleAttack(ws, jsonNode);
                    break;
                default:
                    System.out.println("what the fuck is this message " + type);
                    break;
            }
        } catch (Exception e) {
            System.out.println("wwaaaaaa " + e);
        }
    }

    @Override
    public void onClose(WebSocket ws, int code, String reason, boolean remote) { // why the fuck do we need to include
                                                                                 // all the args if we arent gonna use
                                                                                 // them
        players.remove(ws); // what the hell this is way beter then finding the index and slicing it out
    }

    @Override
    public void onError(WebSocket ws, Exception eeee) {
        System.out.println("error ok ::: " + eeee);
    }

    private void handlePingMessage(WebSocket ws, JsonNode jsonNode) {
        // so uh apparently we have to craft json stuff like this
        // shouldnt be too big of a deal
        String msg = "{\"type\": \"ping\"}";
        ws.send(msg);
    }

    // juicy update logic ahead!!!
    private void gameLoop() {
        if (players.isEmpty())
            return;

        try {
            update();
            broadcastPlayerData();
            broadcastProjectileData();
        } catch (Exception e) {
            System.out.println("booo err " + e);
        }
    }

    private void update() {
        for (Projectile p : projectiles) {
            p.update();
            if (p.time == 0) {
                //cluster shot case
                if (p.type.equals("clusterfireball")) {
                    for (int i = 0; i < 8; i++) {
                        projectiles.add(new Fireball(UUID.randomUUID().toString(), p.x, p.y, i*Math.PI/4, p.playerID));
                    }
                }
                projectiles.remove(p);
            }
        }
        for (WebSocket ws : players.keySet()) {
            players.get(ws).update();
            projectileCollisions(ws);
        }
    }

    private void broadcastPlayerData() {
        ObjectNode resp = objectMapper.createObjectNode();
        resp.put("type", "players");
        resp.set("players", objectMapper.valueToTree(
            players.values().stream().map(pl ->
                Map.ofEntries(
                    Map.entry("id", pl.id),
                    Map.entry("x", pl.x),
                    Map.entry("y", pl.y),
                    Map.entry("name", pl.name),
                    Map.entry("last_x", pl.last_x),
                    Map.entry("last_y", pl.last_y),
                    Map.entry("dir", pl.dir),
                    Map.entry("last_dir", pl.last_dir),
                    Map.entry("health", pl.health),
                    Map.entry("mana", pl.mana),
                    Map.entry("isHitting", pl.isHitting),
                    Map.entry("timeFromLastHit", pl.timeFromLastHit)
                )
            ).toList()
        ));
        String msg = resp.toString();
        broadcast(msg);
    }

    private void broadcastProjectileData() {
        ObjectNode resp = objectMapper.createObjectNode();
        resp.put("type", "projectiles");
        resp.set("projectiles", objectMapper.valueToTree(
            projectiles.stream().map(pr -> 
                Map.of(
                    "id", pr.id, 
                    "x", pr.x, 
                    "y", pr.y,
                    "last_x", pr.last_x, 
                    "last_y",pr.last_y,
                    "radius", pr.radius,
                    "type", pr.type)).toList()));

        String msg = resp.toString();
        broadcast(msg);
    }

    // classic java...
    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();

        System.out.println("ws server running on ws://localhost:{port}");
        System.out.println("working");
    }
}
