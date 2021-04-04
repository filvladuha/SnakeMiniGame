package com;
// что бл такое деприкейт??!

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
// идеи: 1) увеличивать скорость движения (ГОТОВО) 2) показывать твой score
// 3) сделать restart кнопку (ГОТОВО) 4)чтобы ябл появлялись не в змейке (ГОТОВО)
public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400; //кол-во игровых единиц по 16
    private Image dot;
    private Image apple;
    private int apple_x;
    private int apple_y;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots; //размер змейки в данный момент времени
    private Timer timer;
    private boolean up; // boolean по дефолту false
    private boolean down;
    private boolean left;
    private boolean right = true;
    private boolean inGame;
    private boolean timerOn;

    public GameField() { // игоровое поле
        setBackground(Color.black);
        loadImages(); // загружаем наши картинки
        initGame();
        addKeyListener(new FieldKeyListener());// обработчик событий
        setFocusable(true);
        startTimer();
    }

    public void initGame() { // инициалтзирует начало игры
        right = true;
        left = false;
        up = false;
        down = false;
        inGame = true;
        timerOn = true;
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }

    }

    public void startTimer() {
        timer = new Timer(250, this); //250 мили секунд с такой частатой он будет инциализировать (тикать)
        timer.start(); //this этот класс(GameField) будет отвачать за обработку каждого вызова таймера (250 милисекунд) для этого нужен спец метод из интерфейса
        createApple();
    }

    private void newApple() {
        apple_x = new Random().nextInt(20) * DOT_SIZE; //от 0 до 19 возможных рандомных "точек" * 16
        apple_y = new Random().nextInt(20) * DOT_SIZE;
    }
    public void createApple() {
        newApple();
        for (int i = dots; i > 0; i--) {
            if (x[i] == apple_x && y[i] == apple_y) {
                newApple();
            }
        }
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple-icon.png");
        apple = iia.getImage(); // Upcasting // Image apple = new ImageIcon(...); // Image apple = iia; // Image apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1]; //передвижение тела ,кроме головы
            y[i] = y[i - 1];
        }
        if (left) { //для головы змейки
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == apple_x && y[0] == apple_y) {
            dots++;
            if (dots % 4 == 0) {
                timer.setDelay(timer.getDelay() - 10);
            }
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i])
                inGame = false;
        }
        if (x[0] > SIZE)
            inGame = false;
        if (x[0] < 0)
            inGame = false;
        if (y[0] > SIZE)
            inGame = false;
        if (y[0] < 0)
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) { // будет обрабатывать каждый раз "тик" таймера + перерисовывать  поле с помощью repaint()
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();// он вызывает paintComponent(стандартный метод в Swing, он отрисовывает все компоненты)
    }

    @Override
    protected void paintComponent(Graphics g) { // его вызовет repaint()
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this); //отрисовка яблок
            for (int i = 0; i < dots; i++) {  //отрисовка змейка в текущем состоянии
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String nik = "Vlando";
            g.setColor(Color.white);
            g.drawString(nik, 320, 335);

            String restart = "Restart (press R)";
            g.setColor(Color.white);
            g.drawString(restart, 135, SIZE / 2 + 20);

            String str = "Game over";
            Font f = new Font("Arial", Font.BOLD, 24);
            g.setColor(Color.white);
            g.setFont(f);
            g.drawString(str, 120, SIZE / 2 - 10);
        }
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
            if (!inGame && key == KeyEvent.VK_R) {
                initGame();

            }
            if (key == KeyEvent.VK_SPACE) {
                if (timerOn) {
                    timer.stop();
                    timerOn = false;
                } else {
                    timerOn = true;
                    timer.start();

                }
            }
        }
    }
}
