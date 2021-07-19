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
    private boolean player1IsReady;
    private boolean player2IsReady;

    public Game(String player1Token, String player2Token){
        events = new ArrayList<>();
        this.player1Token = player1Token;
        this.player2Token = player2Token;
        this.ID = counter;
        this.turn = 1;
        this.player1IsReady = false;
        this.player2IsReady = false;
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

    public boolean checkStart(String token){
        if (token.equals(player1Token))
            player1IsReady = true;
        else
            player2IsReady = true;
        return player1IsReady && player2IsReady;
    }

    public String getStartInfo(String token){
        String info1 = Repository.getInstance().getPlayerUsername(player2Token) + " T";
        String info2 = Repository.getInstance().getPlayerUsername(player1Token) + " F";

        if (token.equals(player1Token)){
            try {
                Repository.getInstance().getPlayerThread(player2Token).getDataOutputStream().writeUTF("1 " + info2);
                Repository.getInstance().getPlayerThread(player2Token).getDataOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return info1;
        }
        try {
            Repository.getInstance().getPlayerThread(player1Token).getDataOutputStream().writeUTF("1 " + info1);
            Repository.getInstance().getPlayerThread(player1Token).getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info2;
    }

    public boolean isMyTurn(String token){
        if (token.equals(player1Token)){
            if (turn == 1)
                return true;
            return false;
        }
        if (turn == 2)
            return true;
        return false;
    }

    public ArrayList<Integer> getWatersAroundShip(int x, int y){
        if (turn == 1)
            return table2.getWaterAroundShip(x, y);
        return table1.getWaterAroundShip(x, y);
    }

    public void setMap(String token, int[][] map){
        if (token.equals(player1Token))
            table1.setMap(map);
        else
            table2.setMap(map);
    }

}
