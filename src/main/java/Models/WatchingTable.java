package Models;

public class WatchingTable {
    private int [][] map;

    public WatchingTable() {
        map = new int[10][10];
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                map[i][j] = 1;
            }
        }

    }

    public void setWater(int x, int y){
        map[x][y] = 0;
    }

    public void setDestroyed(int x, int y){
        map[x][y] = -1;
    }
}
