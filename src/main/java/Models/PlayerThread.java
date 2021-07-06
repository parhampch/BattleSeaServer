package Models;

import Repository.Repository;

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
                String token = split[0];
                String command = split[1];
                output = null;
                if (command.equals("register")){
                    if (Repository.getInstance().isUsernameValid(split[2])) {
                        Repository.getInstance().addPlayer(split[2], split[3]);
                        output = "1";
                    }
                    else
                        output = "0";
                }
                else if (command.equals("login")){
                    if (Repository.getInstance().isInfoCorrect(split[2], split[3])){
                        Repository.getInstance().addOnlinePlayer(token, split[2]);
                        output = "1";
                    }
                    else
                        output = "0";
                }
                else if (command.equals("newGame")){
                    if (!Repository.getInstance().isThereWaitingPlayer()){
                        Repository.getInstance().addWaitingPlayer(token);
                    }
                    // todo : handle new game
                    output = "1";
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
