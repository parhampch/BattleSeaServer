package Models;
import java.util.ArrayList;


public class Repository {
    private ArrayList<Player> allPlayers;
    private static Repository instance;

    private Repository(){
        allPlayers = new ArrayList<>();
    }

    public static Repository getInstance(){
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public void initialize(){}

    public boolean isUsernameValid(String username){
        for (Player player : allPlayers) {
            if (player.getUsername().equals(username))
                return false;
        }
        return true;
    }

    public void addPlayer(Player player){
        allPlayers.add(player);
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
}
