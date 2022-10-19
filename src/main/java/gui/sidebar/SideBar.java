package gui.sidebar;

import utils.ShapeOptions;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SideBar extends JToolBar {
    public SideBar() {
        ImageIcon circle = new ImageIcon("circle.png");
        ImageIcon rectangle = new ImageIcon("rectangle.png");
        ImageIcon diamond = new ImageIcon("diamond.png");
        ImageIcon arrow = new ImageIcon("arrow.png");
        ImageIcon delete = new ImageIcon("delete.png");

        Action circleAction = new AbstractAction("Start/End", circle) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeOptions.SELECTED_SHAPE = 0;
            }
        };
        Action rectangleAction = new AbstractAction("Process", rectangle) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeOptions.SELECTED_SHAPE = 1;
            }
        };
        Action diamondAction = new AbstractAction("Condition", diamond) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeOptions.SELECTED_SHAPE = 2;
            }
        };
        Action arrowAction = new AbstractAction("Arrow Connector", arrow) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeOptions.SELECTED_SHAPE = 3;
            }
        };
        Action deleteAction = new AbstractAction("Delete", delete) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeOptions.SELECTED_SHAPE = 4;
            }
        };
        this.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        this.add(circleAction);
        this.add(rectangleAction);
        this.add(diamondAction);
        this.add(arrowAction);
        this.add(deleteAction);
    }

}
