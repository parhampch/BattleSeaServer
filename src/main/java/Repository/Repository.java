package Repository;
import java.util.ArrayDeque;
import java.util.ArrayList;

import Models.Game;
import Models.Player;
import Models.PlayerThread;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Random;


public class Repository {
    public ArrayList<Player> allPlayers;
    private static Repository instance;
    private HashMap<String, PlayerThread> playersThreads;
    private HashMap<String, Player> onlinePlayers;
    private HashMap<Integer, Game> games;
    private ArrayDeque<String> waitingPlayer;
    private HashMap<String, Game> gameOfPlayers;

    private Repository(){
        this.allPlayers = new ArrayList<>();
        this.playersThreads = new HashMap<>();
        this.onlinePlayers = new HashMap<>();
        this.games = new HashMap<>();
        this.waitingPlayer = new ArrayDeque<>();
        this.gameOfPlayers = new HashMap<>();
    }

    public static Repository getInstance(){
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public void initialize(){
        Gson gson = new Gson();
        File file = new File("src\\main\\resources\\Players.json");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        allPlayers = gson.fromJson(br, type);
    }

    public boolean isUsernameValid(String username){
        for (Player player : allPlayers) {
            if (player.getUsername().equals(username))
                return false;
        }
        return true;
    }

    public void addPlayer(String username, String password){
        allPlayers.add(new Player(username, password));
        this.saveData();
    }

    public boolean isInfoCorrect(String username, String password){
        for (Player player : allPlayers) {
            if (player.getUsername().equals(username) && player.getPassword().equals(password))
                return true;
        }
        return false;
    }

    public Player getPlayer(String username){
        for (Player player : allPlayers) {
            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }

    public void saveData(){
        File file = new File("src\\main\\resources\\Players.json");
        file.getParentFile().mkdirs();
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file);
            Gson gson = new Gson();
            String json = gson.toJson(allPlayers);
            fileWriter.write(json);
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isThereWaitingPlayer(){
        return !waitingPlayer.isEmpty();
    }

    public void addWaitingPlayer(String token){
        waitingPlayer.addLast(token);
    }

    public String getWaitingPlayer(){
        String token = waitingPlayer.getFirst();
        waitingPlayer.removeFirst();
        return token;
    }

    public void addOnlinePlayer(String token, String username){
        onlinePlayers.put(token, getPlayer(username));
    }

    public void addGame(String token1, String token2, Game game){
        gameOfPlayers.put(token1, game);
        gameOfPlayers.put(token2, game);
        games.put(game.getID(), game);
    }

    public int attackInGame(String token, int x, int y){
        return gameOfPlayers.get(token).attack(x, y);
    }

    public ArrayList<String> getAllGames(){
        ArrayList<String> result = new ArrayList<>();
        for (Game game : games.values()) {
            String temp = Integer.toString(game.getID()) + " " +
                    onlinePlayers.get(game.getPlayer1Token()).getUsername() + " " +
                    onlinePlayers.get(game.getPlayer2Token()).getUsername();
            result.add(temp);
        }
        return result;
    }

    public void removeOnlinePlayer(String token){
        onlinePlayers.remove(token);
    }

    public int createNewCGame(String token){
        if (waitingPlayer.isEmpty()){
            waitingPlayer.add(token);
            return 0;
        }
        String player2Token = waitingPlayer.getFirst();
        waitingPlayer.pop();
        Game game = new Game(player2Token, token);

        games.put(game.getID(), game);
        gameOfPlayers.put(token, game);
        gameOfPlayers.put(player2Token, game);
        try {
            playersThreads.get(player2Token).getDataOutputStream().writeUTF(Integer.toString(game.getID()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return game.getID();
    }

}