package Models;

import java.io.*;
import java.net.Socket;

public class PlayerThread extends Thread{
    private String playerToken;
    private Server server;
    private Socket playerSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public PlayerThread(Socket playerSocket, Server server, String playerToken) {
        this.server = server;
        this.playerSocket = playerSocket;
        this.playerToken = playerToken;
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(playerSocket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(playerSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try{
            String input;
            String output;
            String[] details;

            while (true){
                input = dataInputStream.readUTF();
                String[] split = input.split(" ");
                output = null;


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
