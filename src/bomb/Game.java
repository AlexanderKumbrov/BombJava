package bomb;

public class Game {
    private Bomb bomb;
    private Flag flag ;
    private GameState gameState;

    public GameState getGameState() {
        return gameState;
    }
    //Конструктор Указывание координат
    public Game (int rows, int cols, int bombs){
        Ranges.setSize(new Coord(cols , rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start(){
       bomb.start();        //<-- Вызывает функцию расстановки бомб
       flag.start();
       gameState = GameState.PLAYED;  //При начале игры состояние PLAYED

    }
     //Функция говорит что необходимо
    // изобразить в том или ином месте экрана
    public Box getBox (Coord coord){
        if (flag.get(coord)==Box.OPENED)        //Возвращает что под матрицой flag
            return bomb.get(coord);             //Матрица bomb (нижний слой)
        else
            return flag.get(coord);
    }

    private void checkWinner(){
        if (gameState == GameState.PLAYED)
            if (flag.getCountOfClosedBoxes()== bomb.getTotalBombs())
                gameState = GameState.WINNER;
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver ()) return;        //Если игра кончена
        openBox(coord); //Рекурсивная функция
        checkWinner();  //Вызов функции которая проверяет выйгрыш
    }
    private void openBox(Coord coord){
        switch (flag.get(coord)){
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); return;
            case FLAGED:  return;
            case CLOSED:
                switch (bomb.get(coord)){   //Если клетка закрыта то под ней может быть
                    case ZERO: openBoxesAround(coord);  return;     //Открыть клетки вокруг
                    case BOMB: openBombs (coord); return;     //Бомба
                    default:  flag.setOpenedToBox(coord);  return; //Если под флагом пуста открывается клетка
                }
        }
    }
    private void setOpenedToClosedBoxesAroundNumber(Coord coord){
        if (bomb.get(coord)!= Box.BOMB)
            if (flag.getCountOfFlagedBoxesAround(coord)== bomb.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around)== Box.CLOSED)
                        openBox(around);

    }

    private void openBombs (Coord bombed){
        gameState = GameState.BOMBED;     //Состояние при нажатие на бомбу
        flag.setBombedToBox (bombed);    //Метод для взрыва бомбы
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord)==Box.BOMB)
                flag.setOpenedToClosedBombBox(coord);
        else
            flag.setNoBombToFlagedSafeBox(coord);

    }

    private void  openBoxesAround (Coord coord){
        flag.setOpenedToBox(coord);     //Открыть одну клетку
        for (Coord around : Ranges.getCoordsAround(coord))  //Открывает клетки вокруг пустого  путем рекурсии
            openBox(around);                                //метода openBox

    }
    public void pressRightButton(Coord coord) {
        if (gameOver ()) return;
        flag.toggleFlagedToBox(coord); //Установить открытие в координату
    }
    private boolean gameOver (){
        if (gameState == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}
