package Models;

import javafx.scene.control.Tab;

import java.util.ArrayList;

public class Game {
    private static int counter = 1;
    private int ID;
    private String  player1Token;
    private String player2Token;
    private int turn;
    private Table table1;
    private Table table2;
    private ArrayList<String> events;

    public Game(String player1Token, String player2Token){
        events = new ArrayList<>();
        this.player1Token = player1Token;
        this.player2Token = player2Token;
        this.ID = counter;
        counter++;
    }

    public int getID() {
        return ID;
    }

    public String getPlayer1Token() {
        return player1Token;
    }

    public String getPlayer2Token() {
        return player2Token;
    }

    public boolean isTarget(int x, int y){
        if (turn == 1){
            return table2.isTarget(x, y);
        }
        return table1.isTarget(x, y);
    }

    public int attack(int x, int y){
        if (turn == 1){
            turn = 2;
            if (table2.isTarget(x, y))
                return 1;
        }
        else {
            turn = 1;
            if (table1.isTarget(x, y))
                return 1;
        }
        return 0;
    }

    public void nextTurn(){
        turn = (turn == 1) ? 2 : 1;
    }
}
