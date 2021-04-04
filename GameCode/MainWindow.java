package com;
import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Snake" + "   score: ");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(370, 380);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new GameField());
        setVisible(true);
    }
}
