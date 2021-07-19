package Models;

import java.util.ArrayList;

public class Table {
    private int remainShips;
    private int [][] map;

    public Table() {
        this.remainShips = 9;
    }

    public void setMap(int[][] map) {
        this.map = new int[10][10];
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++)
                this.map[i][j] = map[i][j];
        }
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
        if (x > 0){
            int tempX = x;
            while (tempX > -1 && map[tempX][y] == -1)
                tempX--;
            if (tempX > -1)
                waters.addAll(horizontalWater(tempX, y, true));
        }
        if (x < 9){
            int tempX = x;
            while (tempX > -1 && map[tempX][y] == -1)
                tempX++;
            if (tempX < 10)
                waters.addAll(horizontalWater(tempX, y, false));
        }
        if (y > 0){
            int tempY = y;
            while (tempY > -1 && map[x][tempY] == -1)
                tempY--;
            if (tempY > -1)
                waters.addAll(verticalWater(x, tempY, false));
        }
        if (y < 10){
            int tempY = y;
            while (tempY > -1 && map[x][tempY] == -1)
                tempY++;
            if (tempY < 10)
                waters.addAll(verticalWater(x, tempY, true));
        }
        return waters;
    }

    private ArrayList<Integer> horizontalWater(int tempX, int y, boolean isUp){
        ArrayList<Integer> waters = new ArrayList<>();
        int tempY = y;
        int xPrime = isUp ? tempX + 1 : tempX - 1;
        while (tempY < 10 && map[xPrime][tempY] == -1){
            waters.add(tempX);
            waters.add(tempY);
            tempY++;
        }
        tempY = y - 1;
        while (tempY > -1 && map[xPrime][tempY] == -1){
            waters.add(tempX);
            waters.add(tempY);
            tempY--;
        }
        return waters;
    }

    private ArrayList<Integer> verticalWater(int x, int tempY, boolean isRight){
        ArrayList<Integer> waters = new ArrayList<>();
        int tempX = x;
        int yPrime = isRight ? tempY - 1 : tempY + 1;
        while (tempX < 10 && map[tempX][yPrime] == -1){
            waters.add(tempX);
            waters.add(tempY);
            tempX++;
        }
        if (tempX < 10) {
            waters.add(tempX);
            waters.add(tempY);
        }
        tempX = x - 1;
        while (map[tempX][yPrime] == -1){
            waters.add(tempX);
            waters.add(tempY);
            tempY--;
        }
        if (tempX > -1) {
            waters.add(tempX);
            waters.add(tempY);
        }
        return waters;
    }
}
