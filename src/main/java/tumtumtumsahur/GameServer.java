    package tumtumtumsahur;
    
    import java.net.InetSocketAddress;
    import java.util.Map;
    import java.util.HashMap;
    import java.util.Timer;
    import java.util.TimerTask;
    import java.util.UUID;
    import org.java_websocket.WebSocket;
    import org.java_websocket.handshake.ClientHandshake;
    import org.java_websocket.server.WebSocketServer;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.databind.JsonNode;
    import com.fasterxml.jackson.databind.node.ObjectNode;
    
    public class GameServer extends WebSocketServer {
        private final ObjectMapper objectMapper;
        private final Map<WebSocket, Player> players;
        private final Timer gameLoopInterval;
    
        private static class Player {    
            String name;
            String id;
            double last_x;
            double last_y;
            double x;
            double y;
            double x_vel;
            double y_vel;
            double x_accel;
            double y_accel;
            
    
            // woah weird ass constructor methods
            Player(String id, String name, double x, double y) {
                this.name = name;
                this.id = id;
                this.last_x = x;
                this.last_y = y;
                this.x = x;
                this.y = y;
                this.x_vel = 0;
                this.y_vel = 0;
                this.x_accel = 0;
                this.y_accel = 0;
            }
        }
    
        public GameServer() {
            super(new InetSocketAddress("0.0.0.0", getEnvPort()));
            this.objectMapper = new ObjectMapper();
            this.players = new HashMap<>();
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
    // Just wait for "join" packet from client
            System.out.println("Connection opened. Awaiting join info...");
        }
        private void handleJoin(WebSocket ws, JsonNode jsonNode) {
            String name = jsonNode.has("name") ? jsonNode.get("name").asText() : "unnamed";
            String playerID = UUID.randomUUID().toString();
            Player player = new Player(playerID, name, 0, 0);
            players.put(ws, player);

            ObjectNode response = objectMapper.createObjectNode();
            response.put("type", "init");
            response.put("id", playerID);
            ws.send(response.toString());

            System.out.println("Player joined: " + name + " (" + playerID + ")");
        }
        private void handleMovement(WebSocket ws, JsonNode jsonNode) {
            if (jsonNode.get("dir") == null || jsonNode.get("dir").isNull()) return;
            double dir = jsonNode.get("dir").asDouble(); // direction in radians
            Player player = players.get(ws);
            
            if (player != null) {
                double accel = 1; 
                player.x_accel += accel * Math.cos(dir);
                player.y_accel += accel * Math.sin(dir);
            }
        }    
        private void updatePosition(Player p) {
            p.last_x = p.x;
            p.last_y = p.y;
            double friction = 0.9;
            double maxSpeed = 6.0;
            p.x_vel += p.x_accel;
            p.y_vel += p.y_accel;
            double speed = Math.hypot(p.x_vel, p.y_vel);
            if (speed > maxSpeed) {
                double scale = maxSpeed / speed;
                p.x_vel *= scale;
                p.y_vel *= scale;
            }
            p.x += p.x_vel;
            p.y += p.y_vel;
            p.x_vel *= friction;
            p.y_vel *= friction;
            p.x_accel = 0;
            p.y_accel = 0;
        }
    
    
        @Override    
        public void onMessage(WebSocket ws, String msg) {
            try {
                JsonNode jsonNode = objectMapper.readTree(msg); // what da hell is this
                String type = jsonNode.get("type").asText();
    
                // wtf this is just js on steroids
                switch (type) {
                    case "join":
                        handleJoin(ws, jsonNode);
                        break;
                    case "ping":
                        handlePingMessage(ws, jsonNode);
                        break;
    
                    case "move":
                        handleMovement(ws, jsonNode);
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
                broadcastData();
            } catch (Exception e) {
                System.out.println("booo err " + e);
            }
        }
    
        private void update() {
            for (Player p : players.values()) {
                updatePosition(p);
            }
        }
    
        private void broadcastData() {
            ObjectNode resp = objectMapper.createObjectNode();
            resp.put("type", "players");
            resp.set("players", objectMapper.valueToTree(
                    players.values().stream().map(pl -> Map.of("id", pl.id, "x", pl.x, "y", pl.y, "name", pl.name,"last_x", pl.last_x, "last_y",pl.last_y)).toList()));
    
            String msg = resp.toString();
            broadcast(msg);
        }
    
        // classic java...
        public static void main(String[] args) {
            GameServer server = new GameServer();
            server.start();
    
            System.out.println("ws server running on ws://localhost:something!!!!!");
            System.out.println("working");
        }
    }
