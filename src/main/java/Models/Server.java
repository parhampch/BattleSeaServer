package Models;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private final static int port = 9595;

    private ServerSocket serverSocket;
    private Server instance = null;

    private HashMap<String, Thread> playersThreads;
    private HashMap<String, Player> onlinePlayers;

    private HashMap<Integer, Game> games;

    private ArrayDeque<Player> waitedPlayer;

    private Server(){
        try {
            this.serverSocket = new ServerSocket(port);
            this.playersThreads = new HashMap<>();
            this.onlinePlayers = new HashMap<>();
            this.games = new HashMap<>();
            this.waitedPlayer = new ArrayDeque<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server getInstance(){
        if (instance == null)
            instance = new Server();
        return instance;
    }

    public void run(){
        while (true){
            try {
                Socket socket = serverSocket.accept();
                String tocken = "";
                new PlayerThread(socket, this, tocken).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean haveWeWaitedPlayer(){
        return !waitedPlayer.isEmpty();
    }

    public void addWaitedPlayer(Player player){
        waitedPlayer.addLast(player);
    }

    public Player getWaitingPlayer(){
        Player player = waitedPlayer.getFirst();
        waitedPlayer.removeFirst();
        return player;
    }


}
