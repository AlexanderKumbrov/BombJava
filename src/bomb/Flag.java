package bomb;

class Flag {
    private Matrix flagMap ;
    private int countOfClosedBoxes;     //Переменная для подсчет бомб

    void start (){
        flagMap = new Matrix(Box.CLOSED);   //Инициализация матрици на закрытые ячейки
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;   //В переменную присваивается общее количество box
    }
    Box get (Coord coord){      //Геттер возвращает
        return flagMap.get(coord);  //Координату
    }
    public void setOpenedToBox(Coord coord){ //открывает box
        flagMap.set(coord , Box.OPENED);
        countOfClosedBoxes--; //Дикремент перменной при открытие box
    }

    void toggleFlagedToBox (Coord coord){           //Установка и снятие флага
        switch (flagMap.get(coord)){
            case FLAGED: setClosedToBox(coord);       //Убрать флагг
            break;
            case CLOSED: setFlagedToBomb(coord);        //Поставить флаг
            break;
        }
    }

    private void setClosedToBox(Coord coord) {      //Метод для снятие флага
        flagMap.set(coord,Box.CLOSED);
    }


    public void setFlagedToBomb(Coord coord){
        flagMap.set(coord , Box.FLAGED);
    }

    int getCountOfClosedBoxes() {        //Метод для проверки бколичество бомб
    return countOfClosedBoxes;

    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord , Box.BOMBED);
    }

    void setOpenedToClosedBombBox(Coord coord) {
    if (flagMap.get(coord)== Box.CLOSED)
        flagMap.set(coord , Box.OPENED);
    }

    void setNoBombToFlagedSafeBox(Coord coord) {
        if (flagMap.get(coord)== Box.FLAGED)
            flagMap.set(coord , Box.NOBOMB);

    }

    int getCountOfFlagedBoxesAround (Coord coord){
        int count =0 ;
        for (Coord around :  Ranges.getCoordsAround(coord))
            if (flagMap.get(around)==Box.FLAGED)
                count ++;
        return count;
    }
}
