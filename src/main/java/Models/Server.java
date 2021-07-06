package Models;

import util.ConfigLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private final static int PORT = Integer.parseInt(ConfigLoader.readProperty("port"));

    private ServerSocket serverSocket;
    private static Server instance;

    private HashMap<String, Thread> playersThreads;
    private HashMap<String, Player> onlinePlayers;

    private HashMap<Integer, Game> games;

    private ArrayDeque<Player> waitingPlayer;

    private Server(){
        try {
            this.serverSocket = new ServerSocket(PORT);
            this.playersThreads = new HashMap<>();
            this.onlinePlayers = new HashMap<>();
            this.games = new HashMap<>();
            this.waitingPlayer = new ArrayDeque<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Server getInstance(){
        if (instance == null)
            instance = new Server();
        return instance;
    }

    public void run(){
        Repository.getInstance().initialize();
        while (true){
            try {
                Socket socket = serverSocket.accept();
                String token = "";
                new PlayerThread(socket, token).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isThereWaitingPlayer(){
        return !waitingPlayer.isEmpty();
    }

    public void addWaitingPlayer(Player player){
        waitingPlayer.addLast(player);
    }

    public Player getWaitingPlayer(){
        Player player = waitingPlayer.getFirst();
        waitingPlayer.removeFirst();
        return player;
    }


}
