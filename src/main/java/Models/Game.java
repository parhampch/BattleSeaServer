package Models;

import javafx.scene.control.Tab;

public class Game {
    private Player player1;
    private Player player2;
    private int turn;
    private Table table1;
    private Table table2;

    public boolean isTarget(int x, int y){
        if (turn == 1){
            return table2.isTarget(x, y);
        }
        return table1.isTarget(x, y);
    }
}
