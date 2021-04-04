package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel  {
    private JButton newGame = new JButton("Новая игра");
    private JButton exit = new JButton("Выход");

    public MainMenuPanel() {
        setLayout(new GridLayout(4, 1, 10, 10));//size button=5;1;10;расстояние между кнопками=10
        //setSize(150, 300); // 150 300
        setBackground(Color.orange);
        add(newGame);
        add(exit);
        setFocusable(true);
        setVisible(true);

        ActionListener startGameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {MainWindow newGameWindow = new MainWindow();}
        };
        newGame.addActionListener(startGameListener);

        ActionListener exitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {System.exit(0);}
        };
        exit.addActionListener(exitListener);
    }
}

