package Models;

import java.io.*;
import java.net.Socket;

public class PlayerThread extends Thread{
    private String playerTocken;
    private Server server;
    private Socket playerSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public PlayerThread(Socket playerSocket, Server server, String playerTocken) {
        this.server = server;
        this.playerSocket = playerSocket;
        this.playerTocken = playerTocken;
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
