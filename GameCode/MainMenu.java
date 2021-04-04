package com;
import javax.swing.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MainMenu");
        add(new MainMenuPanel());
        //pack();
        setSize(250, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        System.out.println("Запуск главного меню");
        setVisible(true);
    }
}
