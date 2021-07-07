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
    private SecureRandom secureRandom;
    private static Server instance;


    private Server(){
        try {
            this.serverSocket = new ServerSocket(PORT);
            this.secureRandom = new SecureRandom();
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
        SecureRandom secureRandom = new SecureRandom();
        while (true){
            try {
                System.out.println("Waiting for client ...");
                Socket socket = serverSocket.accept();
                String token = generateToken();
                System.out.println("Client with token" + token + "connected");
                new PlayerThread(socket, token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateToken() {
        byte bytes[] = new byte[20];
        secureRandom.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        return token;
    }
}
