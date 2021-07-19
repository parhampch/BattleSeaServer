package Models;

public class Player implements Comparable<Player> {
    private String username;
    private String password;
    private long score;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    @Override
    public int compareTo(Player player) {
        if (this.score > player.score)
            return -1;
        if (this.score < player.score)
            return 1;
        return this.username.compareTo(player.username);
    }

    @Override
    public String toString() {
        return "Username : " + this.username + " Score : " + this.score;
    }
}

