package Models;

import Repository.Repository;
import util.ConfigLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private final static int PORT = Integer.parseInt(ConfigLoader.readProperty("port"));

    private ServerSocket serverSocket;
    private static Server instance;


    private Server(){
        try {
            this.serverSocket = new ServerSocket(PORT);
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
}
