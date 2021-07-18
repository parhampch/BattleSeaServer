package Models;

public class Table {
    private int remainShips;
    int [][] map;

    public Table(int[][] map) {
        this.remainShips = 9;
        this.map = map;
    }

    public int handleAttack(int x, int y){
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
}
