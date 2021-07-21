package Models;

import Repository.Repository;
import util.ConfigLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.*;

public class Server {
    private final static int PORT = Integer.parseInt(ConfigLoader.readProperty("port"));
    private ServerSocket serverSocket;
    private static Server instance;

    private Server(){
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            try {
                this.serverSocket = new ServerSocket(8000);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static Server getInstance(){
        if (instance == null)
            instance = new Server();
        return instance;
    }

    public void run(){
        Repository.getInstance().initialize();
        SecureRandom secureRandom = new SecureRandom();
        while (true){
            try {
                System.out.println("Waiting for client ...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                new PlayerThread(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
