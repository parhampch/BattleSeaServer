package Models;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class Repository {
    public ArrayList<Player> allPlayers;
    private static Repository instance;

    private Repository(){
        allPlayers = new ArrayList<>();
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

    public void addPlayer(Player player){
        allPlayers.add(player);
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
}