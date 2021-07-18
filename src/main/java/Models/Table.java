package Models;

public class Table {
    private int remainShips;
    int [][] map;

    public Table(int[][] map) {
        this.remainShips = 9;
        this.map = map;
    }

    public boolean isTarget(int x, int y){
        return true;
    }
}
