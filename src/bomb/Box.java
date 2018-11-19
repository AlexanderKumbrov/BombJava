package bomb;

public enum Box {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    CLOSED,
    FLAGED,
    BOMBED,
    OPENED,
    NOBOMB;


    public Object image;
    Box getNextNumberBox (){
        return Box.values()[this.ordinal() +1];  //Увеличение цифры вокруг каждой бомбы
    }

    int getNumber(){
        return this.ordinal();
    }
}
