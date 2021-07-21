package Models;

import Repository.Repository;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    private static int counter = 1;
    private final int ID;
    private final String  player1Token;
    private final String player2Token;
    private int turn;
    private final Table table1;
    private final Table table2;
    private final WatchingTable watchingTable1;
    private final WatchingTable watchingTable2;
    private final ArrayList<String> events;
    private boolean player1IsReady;
    private boolean player2IsReady;
    private boolean isFinished;

    public Game(String player1Token, String player2Token){
        events = new ArrayList<>();
        this.player1Token = player1Token;
        this.player2Token = player2Token;
        this.ID = counter;
        this.turn = 1;
        this.player1IsReady = false;
        this.player2IsReady = false;
        this.isFinished = false;
        this.table1 = new Table();
        this.table2 = new Table();
        this.watchingTable1 = new WatchingTable();
        this.watchingTable2 = new WatchingTable();
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

    public boolean isFinished() {
        return isFinished;
    }

    public int attack(int x, int y){
        int result;
        String enemyToken = "";
        String myToken = "";
        if (turn == 1){
            result = handleAttackInTables(table2, watchingTable1, x, y);
            myToken = player1Token;
            enemyToken = player2Token;
        }
        else {
            result = handleAttackInTables(table1, watchingTable2, x, y);
            myToken = player2Token;
            enemyToken = player1Token;
        }
        if (result == 0){
            try {
                String massage = "T " + (result + 1) + " " + x + " " + y;
                Repository.getInstance().getPlayerThread(enemyToken).getDataOutputStream().writeUTF(massage);
                Repository.getInstance().getPlayerThread(enemyToken).getDataOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.nextTurn();
        }
        else {
            String massage = "F " + (result + 1) + " " + x + " " + y;
            if (result > 1)
                massage += " " + new Gson().toJson(getWatersAroundShip(x, y));
            try {
                Repository.getInstance().getPlayerThread(enemyToken).getDataOutputStream().writeUTF(massage);
                Repository.getInstance().getPlayerThread(enemyToken).getDataOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result == 3){
                this.isFinished = true;
                Repository.getInstance().playerWin(myToken);
                Repository.getInstance().playerLose(enemyToken);
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
            return turn == 1;
        }
        return turn == 2;
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

    public void infoTimeOut(String token){
        if (token.equals(player1Token)){
            try {
                Repository.getInstance().getPlayerThread(player2Token).getDataOutputStream().writeUTF("T 0");
                Repository.getInstance().getPlayerThread(player2Token).getDataOutputStream().flush();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Repository.getInstance().getPlayerThread(player1Token).getDataOutputStream().writeUTF("T 0");
            Repository.getInstance().getPlayerThread(player1Token).getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int handleAttackInTables(Table table, WatchingTable watchingTable, int x, int y){
        table.increaseShots();
        int result = table.handleAttack(x, y);
        if (result > 0)
            table.increaseGoodShots();

        if (result == 0)
            watchingTable.setWater(x, y);
        else if (result == 1)
            watchingTable.setDestroyed(x, y);
        else if (result > 1){
            watchingTable.setDestroyed(x, y);
            ArrayList<Integer> watersAroundShip = getWatersAroundShip(x, y);
            for (int i = 0; i < watersAroundShip.size(); i+=2){
                int tempX = watersAroundShip.get(i);
                int tempY = watersAroundShip.get(i + 1);
                watchingTable.setWater(tempX, tempY);
            }
        }
        return result;
    }

    public String getWatchingData(){
        return Repository.getInstance().getPlayerUsername(player1Token) + " " +
                new Gson().toJson(watchingTable2.getMap()) + " " +
                Repository.getInstance().getPlayerUsername(player2Token) + " " +
                new Gson().toJson(watchingTable1.getMap());

    }

    public String getPrimeInfoForWatching(){
        return this.ID + " " + table1.getShots() + " " + table1.getRemainShips() +  " " +
                table1.getGoodShots() + " " + table2.getShots() + " " + table2.getRemainShips() +  " " +
                table2.getGoodShots();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return ID == game.ID;

    }

}
