import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import bomb.*;
import bomb.Box;

public class JavaBomb extends JFrame {
    private Game game ;            //Переменная класса Game
    private JPanel panel;         //Переменная класса JPanel Swing
    private final int COLS =9;   //Размер окна
    private final int ROWS = 9; //Размер окна
    private final int BOMBS =5;  //Общее количество бомб
    private final int IMAGE_SIZE=50;        //Размер картинок
    private JLabel label;
    private JLabel labelStat ;
    private int statLose = 0  ;
    private int statWin = 0;
    private JPanel labelStatWin;
    public static void main(String[] args) {
        new JavaBomb();

    }
    private JavaBomb(){
        game = new Game(COLS , ROWS , BOMBS);  //Экземпляр Game в который
        game.start();                         //передается размеры и количество бомб
        setImages();
        initLabelStat();
        initLabel();                       //Вызов статуса
        initPanel();
        initFrame();
    }

    private void initLabel(){
        label = new  JLabel("Добро пожаловать !"); //Приветсвие
        add (label, BorderLayout.SOUTH);     //Размещение статуса
    }
    private void initLabelStat(){
        labelStat = new JLabel("Stat");
        add(labelStat , BorderLayout.NORTH);
    }

    private void initPanel(){  //Создание панели с размером
        panel = new JPanel(){ //Рисование картинок
            @Override
            protected void paintComponent(Graphics g) {  //Вставка картинок с enum
                super.paintComponent(g);
               for (Coord coord : Ranges.getAllCoords())
                    g.drawImage((Image) game.getBox(coord).image, coord.x*IMAGE_SIZE ,coord.y* IMAGE_SIZE, this);
            }
        };

        panel.addMouseListener(new MouseAdapter() {     //Иницилтзация мыши
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;   //   Получение координат курсора по оси x
                int y = e.getY() / IMAGE_SIZE;   // и по оси y
                Coord coord = new Coord(x,y);
                if (e.getButton() == MouseEvent.BUTTON1)    //Если нажата левая кнопка мыши
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3)    //Если нажата правая кнопка мыши
                    game.pressRightButton (coord);
                if (e.getButton() == MouseEvent.BUTTON2)    //Если нажата правая кнопка мыши
                    game.start();
                label.setText(getMessage());
                panel.repaint();   //Перерисовать панель чтобы изменение были видны
                labelStat.setText(getMessageWin()+ getMessageLose()+statLose+"  You lose                            " +
                        "                                           You Win"+statWin );


            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x*IMAGE_SIZE,
                Ranges.getSize().y*IMAGE_SIZE));        //Размер окна Dimension зависимость awt
        add(panel); //Добавление панели на форму
    }

    private String getMessage(){            //Сообщение при разных состояниях игры
        switch (game.getGameState()){
            case PLAYED:
                return "Удачи !!! ";
            case BOMBED:
                return "Ты взорвался :(";
            case WINNER:
                return "Поздравляю , ты победил !";
                default:
                    return "...";
        }
    }
    private int getMessageLose (){
        switch (game.getGameState()){
            case BOMBED:
               statLose ++;
                default:
                    return 0 ;

        }
    }

    private int getMessageWin(){
        switch (game.getGameState()){
            case WINNER:
                statWin ++;
                default:
                    return 0;
        }
    }

    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  //При закрытие окна завершается программа
        setTitle("Java BOOOMB!");       //Название
        setResizable(false);  //запрет на изменение размера окна
        setVisible(true);        //что бы видна форма
        setIconImage(getImage("icon"));  //иконка программы
        pack();          // метод  устанавливает такой размер приложение что бы вместилось все
        setLocationRelativeTo(null);  //Установка окна по центру
     }
     private void setImages(){      //Загружаем картинки с enum
        for (Box box : Box.values()){
            box.image = getImage(box.name());
        }
     }

     private Image getImage(String name){
        String filename = "img/"+name.toLowerCase()+".png/";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename)); //Получение доступа к файлу с картинками
        return icon.getImage();
     }
}
