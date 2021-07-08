package Models;

import Repository.Repository;

import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class PlayerThread extends Thread{
    private String playerToken;
    private Socket playerSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public PlayerThread(Socket playerSocket) {
        this.playerSocket = playerSocket;
        this.playerToken = playerToken;
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(playerSocket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(playerSocket.getOutputStream()));
            dataOutputStream.writeUTF(playerToken);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    @Override
    public void run(){
        //todo: request death on thread (close)

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
                        output = "1 " + generateToken();
                    }
                    else
                        output = "0";
                }
                else if (command.equals("newGame")){
                    if (!Repository.getInstance().isThereWaitingPlayer()){
                        Repository.getInstance().addWaitingPlayer(token);
                        output = "0";
                    }
                    else {
                        int turn = new Random().nextInt() % 2;
                        String secondToken = Repository.getInstance().getWaitingPlayer();
                        Game game = new Game(token, secondToken, turn);
                        Repository.getInstance().addGame(token, secondToken, game);
                        output = "1";
                    }
                }
                dataOutputStream.writeUTF(output);
                dataOutputStream.flush();


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte bytes[] = new byte[20];
        secureRandom.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        return token;
    }
}
