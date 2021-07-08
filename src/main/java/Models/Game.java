package Models;

import javafx.scene.control.Tab;

public class Game {
    private String  player1Token;
    private String player2Token;
    private int turn;
    private Table table1;
    private Table table2;

    public Game(String player1Token, String player2Token, int turn){
        this.player1Token = player1Token;
        this.player2Token = player2Token;
        this.turn = turn;
    }

    public boolean isTarget(int x, int y){
        if (turn == 1){
            return table2.isTarget(x, y);
        }
        return table1.isTarget(x, y);
    }

    public int attack(int x, int y){
        if (turn == 1){
            if (table2.isTarget(x, y))
                return 1;
        }
        else {
            if (table1.isTarget(x, y))
                return 1;
        }
        return 0;
    }
}
