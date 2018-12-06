import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import bomb.*;
import bomb.Box;

public class JavaBomb extends JFrame  {
    private Game game ;            //Переменная класса Game
    private JPanel panel;         //Переменная класса JPanel Swing
    private JPanel panel2;
    private final int COLS =9;   //Размер окна
    private final int ROWS = 9; //Размер окна
    private int BOMBS ;                 //Общее количество бомб
    private final int IMAGE_SIZE=50;        //Размер картинок
    private JLabel label;
    private JLabel labelStat ;
    private int statLose = 0  ;
    private int statWin = 0;
    private JLabel lbl = new JLabel("Установите количество бомб");
    private JRadioButton bombRadio1 = new JRadioButton("5 Бомб");
    private JRadioButton bombRadio2 = new JRadioButton("8 Бомб");
    private JRadioButton bombRadio3 = new JRadioButton("12 Бомб");
    private JButton buttonStart = new JButton("Старт");
    public static void main(String[] args) {
        new JavaBomb();

    }

    public JavaBomb(){
        initPanel2();

    }

    private void initLabel(){
        label = new  JLabel("Добро пожаловать !"); //Приветсвие
        add (label, BorderLayout.SOUTH);     //Размещение статуса
    }
    private void initLabelStat(){
        labelStat = new JLabel("Stat");
        add(labelStat , BorderLayout.NORTH);
    }
    private void initBombPanel(){


        setImages();
        initLabelStat();
        initLabel();                       //Вызов статуса
       initPanel();
       initFrame();

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


    ////////////////////////////////////////////////////////////////


private void initPanel2(){
        panel2 = new JPanel();
panel2.setLayout(new GridLayout(5,5,2,2));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Java BOOOMB!");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(false);  //запрет на изменение размера окна
    setVisible(true);        //что бы видна форма
    setIconImage(getImage("icon"));  //иконка программы
    setLocationRelativeTo(null);  //Установка окна по центру
    panel2.add(lbl);
ButtonGroup group = new ButtonGroup();
group.add(bombRadio1);
group.add(bombRadio2);
group.add(bombRadio3);

panel2.add(bombRadio1);
panel2.add(bombRadio2);
panel2.add(bombRadio3);
panel2.add(buttonStart);
buttonStart.addActionListener(this::buttonStart);

    bombRadio1.setSelected(true);
    add(panel2);
    pack();

}
public void buttonStart(ActionEvent e){
        if (bombRadio1.isSelected()) {
            BOMBS = 5;
            game = new Game(COLS, ROWS, BOMBS);  //Экземпляр Game в который
                                     //передается размеры и количество бомб
            initBombPanel();
            game.start();
        }
    if (bombRadio2.isSelected()) {
        BOMBS = 8;
        game = new Game(COLS, ROWS, BOMBS);  //Экземпляр Game в который
        initBombPanel();                     //передается размеры и количество бомб
        game.start();
    }
    if (bombRadio3.isSelected()){
            BOMBS = 12;
            game = new Game(COLS , ROWS , BOMBS);
            initBombPanel();
            game.start();
    }

panel2.setVisible(false);
}
/////////////////////////////////////////////////////////////////

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
