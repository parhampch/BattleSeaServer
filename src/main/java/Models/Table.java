package Models;

import java.util.ArrayList;

public class Table {
    private int remainShips;
    private int [][] map;

    public Table(int[][] map) {
        this.remainShips = 9;
        this.map = map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int handleAttack(int x, int y){
        // -1 : ruined part of ship  0 : water  1 : healthy ship  2 : target destroyed :D
        if (map[x][y] == 0)
            return 0;
        int color = map[x][y];
        map[x][y] = -1;
        int temp = x;
        while (temp > -1){
            if (map[temp][y] == color)
                return 1;
            if (map[temp][y] == 0)
                break;
            temp--;
        }
        temp = x;
        while (temp < 10){
            if (map[temp][y] == color)
                return 1;
            if (map[temp][y] == 0)
                break;
            temp++;
        }
        temp = y;
        while (temp > -1){
            if (map[x][temp] == color)
                return 1;
            if (map[x][temp] == 0)
                break;
            temp--;
        }
        temp = y;
        while (temp < 10){
            if (map[x][temp] == color)
                return 1;
            if (map[x][temp] == 0)
                break;
            temp++;
        }
        remainShips--;
        if (remainShips > 0)
            return 2;
        return 3;
    }

    public ArrayList<Integer> getWaterAroundShip(int x, int y){
        ArrayList<Integer> waters = new ArrayList<>();
        if (x == 0){
            int tempY = y;
            while (tempY < 10 && map[x][y] == -1)
                tempY++;
            if (tempY == 10){
                tempY--;

            }
        }
        return waters;
    }
}
