package Models;

import Repository.Repository;

import java.io.IOException;
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

    public int attack(int x, int y){
        int result;
        String enemyToken = "";
        if (turn == 1){
            result = table2.handleAttack(x, y);
            enemyToken = player2Token;
        }
        else {
            result = table1.handleAttack(x, y);
            enemyToken = player1Token;
        }
        if (result == 0){
            try {
                Repository.getInstance().getPlayerThread(enemyToken).getDataOutputStream().writeUTF("0");
                Repository.getInstance().getPlayerThread(enemyToken).getDataOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.nextTurn();
        }
        else {
            String massage = Integer.toString(result) + " " + Integer.toString(x) + " " + Integer.toString(y);
            try {
                Repository.getInstance().getPlayerThread(enemyToken).getDataOutputStream().writeUTF(massage);
                Repository.getInstance().getPlayerThread(enemyToken).getDataOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void nextTurn(){
        turn = (turn == 1) ? 2 : 1;
    }
}
