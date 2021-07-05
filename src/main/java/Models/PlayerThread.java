package Models;

import java.io.*;
import java.net.Socket;

public class PlayerThread extends Thread{
    private String playerToken;
    private Socket playerSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public PlayerThread(Socket playerSocket, String playerToken) {
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
                input = dataInputStream.readUTF(); // Waiting for command from client
                String[] split = input.split(" "); // token command CommandPhrasesK
                output = null;


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
