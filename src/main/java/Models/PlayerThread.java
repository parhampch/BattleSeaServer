package Models;

import Repository.Repository;
import com.google.gson.Gson;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
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
                        String newToken = generateToken();
                        Repository.getInstance().addOnlinePlayer(newToken, split[2]);
                        output = "1 " + newToken;
                    }
                    else
                        output = "0";
                }
                else if (command.equals("logout")){
                    Repository.getInstance().removeOnlinePlayer(token);
                    output = "1";
                }
                else if (command.equals("close")){
                    break;
                }
                else if (command.equals("newGame")){
                    output = Integer.toString(Repository.getInstance().createNewCGame(token));
                }
                else if (command.equals("attack")){
                    int x = Integer.parseInt(split[2]);
                    int y = Integer.parseInt(split[3]);
                    int result = Repository.getInstance().attackInGame(token, x, y);
                    String massage = Integer.toString(result) + " ";
                    //todo : water cells around ship
                    output = massage;
                }
                else if (command.equals("ongoingGames")){
                    output = new Gson().toJson(Repository.getInstance().getAllGames());
                }
                else if (command.equals("nextTurn")){
                    Repository.getInstance().nextTurnOfAGame(token);
                    output = "1";
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

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
}
