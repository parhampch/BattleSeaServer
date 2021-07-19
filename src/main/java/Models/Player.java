package Models;

public class Player implements Comparable<Player> {
    private String username;
    private String password;
    private int winNumber;
    private int loseNumber;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public int getWinNumber() {
        return winNumber;
    }

    public int getLoseNumber() {
        return loseNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void win(){
        winNumber++;
    }

    public void lose(){
        loseNumber++;
    }

    public int getScore(){
        return winNumber - loseNumber;
    }

    @Override
    public int compareTo(Player player) {
        if (this.getScore() > player.getScore())
            return -1;
        if (this.getScore() < player.getScore())
            return 1;
        return this.username.compareTo(player.username);
    }

    @Override
    public String toString() {
        return "Username : " + this.username + " Score : " + this.getUsername();
    }
}

