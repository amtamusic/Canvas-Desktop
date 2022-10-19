package gui;

import gui.sidebar.SideBar;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("Canvas Desktop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.getContentPane().setLayout(new BorderLayout());
        this.add(new SideBar(), BorderLayout.BEFORE_FIRST_LINE);
        this.add(new Canvas(), BorderLayout.CENTER);
        setVisible(true);
    }
}
