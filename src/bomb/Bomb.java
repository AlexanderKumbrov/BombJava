package bomb;

import java.util.Random;

public class Bomb {
    private Matrix bombMap;
    private int totalBomb;

    Bomb (int totalBomb){
        this.totalBomb = totalBomb;
        fixBombCount();     // Вызов ограничение количество бомб
    }
    void start (){
        bombMap = new Matrix(Box.ZERO);
        for (int j =0 ; j < totalBomb ; j++)      //цикл для размещенние 10 бомб
        placeBomb();
    }
    Box get (Coord coord){
        return bombMap.get(coord);
    }


    private void fixBombCount(){                                       //Не больше половины поля
        int maxBomb = Ranges.getSize().x * Ranges.getSize().y /2;      //Количество бомб
        if (totalBomb > maxBomb)
            totalBomb = maxBomb;
    }
    private void placeBomb(){
        while (true) {                          //Что бы бомбы не ставились друг на друга
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord))  //Если в координате есть бомба
                continue;                        // то пропускаем это место
            bombMap.set(new Coord(coord.x , coord.y),Box.BOMB);
            inNumbersAroundBomb(coord);
            break;
        }

    }
    private void inNumbersAroundBomb (Coord coord ){
        for (Coord around : Ranges.getCoordsAround(coord))  //<-- Проствлять 1 вокруг каждой бомбы
           if (Box.BOMB!= bombMap.get(around))       //Проверка не находится ли там уже бомба
            bombMap.set(around , bombMap.get(around).getNextNumberBox());      //Увеличение цифры вокруг каждой бомбы
    }

    int getTotalBombs() {
        return totalBomb;
    }
}
