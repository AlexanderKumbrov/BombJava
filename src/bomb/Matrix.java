package bomb;

 class Matrix {
     private Box [] [] matrix;  //Массив элементов в игре
     Matrix( Box defaultBox){   //Конструктор который принимает один элемент
        matrix = new Box [Ranges.getSize().x][Ranges.getSize().y];      //Заполнение матрицы
        for (Coord coord : Ranges.getAllCoords())
            matrix [coord.x] [coord.y] =defaultBox;
     }
     Box get (Coord coord){
         if (Ranges.inRange (coord))            // Защита от переполнения массива с помощью
         return matrix [coord.x][coord.y];      //функции inRange
         return null;
     }
     void set (Coord coord , Box box){          //Заполнение массива
         if (Ranges.inRange (coord))
         matrix [coord.x][coord.y] = box;
     }
}
