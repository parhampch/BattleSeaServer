package Models;

public class Player {
    private String username;
    private String password;
    private long score;

    public Player(String username, String password, long score) {
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
}

