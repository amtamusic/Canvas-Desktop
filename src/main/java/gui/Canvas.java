package gui;

import gui.shapes.Connection;
import gui.shapes.Diamond;
import gui.shapes.Ellipse;
import gui.shapes.Rectangle;
import utils.ShapeConstants;
import utils.ShapeOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class Canvas extends JPanel {
    ArrayList<Shape> shapes = new ArrayList<>();
    ArrayList<Connection> connections = new ArrayList<>();
    int drawOption = 0;
    int selectedShapeIndex = -1;
    int selectedShapeConnector1 = -1;
    int selectedShapeConnector2 = -1;

    public Canvas() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setSize(width - (width / 10), Toolkit.getDefaultToolkit().getScreenSize().height);
        this.setBackground(Color.white);

        // Mouse Listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                if (selectedShapeIndex == -1) {
                    if (ShapeOptions.SELECTED_SHAPE == 0) {
                        String text = promptForText();
                        if (text != null) {
                            shapes.add(createNewShape(new Ellipse(text), me.getX(), me.getY()));
                        }
                    } else if (ShapeOptions.SELECTED_SHAPE == 1) {
                        String text = promptForText();
                        if (text != null) {
                            shapes.add(createNewShape(new Rectangle(text), me.getX(), me.getY()));
                        }
                    } else if (ShapeOptions.SELECTED_SHAPE == 2) {
                        String text = promptForText();
                        if (text != null) {
                            shapes.add(createNewShape(new Diamond(text), me.getX(), me.getY()));
                        }
                    }
                    repaint();
                }


                if (ShapeOptions.SELECTED_SHAPE == 3) {
                    int index = 0;
                    boolean found = false;
                    for (Shape s : shapes) {
                        if (s.contains(me.getPoint())) {
                            if (selectedShapeConnector1 == -1) {
                                selectedShapeConnector1 = index;
                                found = true;
                            } else if (selectedShapeConnector2 == -1 && selectedShapeConnector1 != index) {
                                selectedShapeConnector2 = index;
                                found = true;
                            }
                        }
                        index++;
                    }
                    if (!found) {
                        selectedShapeConnector1 = -1;
                        selectedShapeConnector2 = -1;
                    }
                    if (selectedShapeConnector1 != -1 && selectedShapeConnector2 != -1) {
                        System.out.println("Connect " + selectedShapeConnector1 + " to " + selectedShapeConnector2);
                        String text = promptForText();
                        if (text != null) {
                            Connection c = new Connection(selectedShapeConnector1, selectedShapeConnector2, text);
                            connections.add(c);
                        }
                        repaint();
                        selectedShapeConnector1 = -1;
                        selectedShapeConnector2 = -1;
                    }
                } else {
                    selectedShapeConnector1 = -1;
                    selectedShapeConnector2 = -1;
                }

                if (ShapeOptions.SELECTED_SHAPE == 4) {
                    boolean confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this shape?", "Confirm Delete", JOptionPane.YES_NO_OPTION) == 0;
                    if (confirm) {
                        int index = 0;
                        for (Shape s : shapes) {
                            if (s.contains(me.getPoint())) {
                                selectedShapeIndex = index;
                            }
                            index++;
                        }
                        if (selectedShapeIndex != -1) {
                            shapes.remove(selectedShapeIndex);
                            ArrayList<Integer> removedConnections = new ArrayList<>();
                            for (Connection c : connections) {
                                if (c.getShape1() == selectedShapeIndex || c.getShape2() == selectedShapeIndex) {
                                    int removeIndex = connections.indexOf(c);
                                    removedConnections.add(removeIndex);
                                }
                            }
                            Collections.sort(removedConnections);
                            for (int i = removedConnections.size() - 1; i >= 0; i--) {
                                //System.out.println("Removing connection " + removedConnections.get(i));
                                connections.remove((int) removedConnections.get(i));
                            }
                            Collections.sort(removedConnections);
                            //System.out.println(connections);
                            for (Connection c2 : connections) {
                                for (int i : removedConnections) {
                                    if (c2.getShape1() > i) {
                                        c2.setShape1(c2.getShape1() - 1);
                                    }
                                    if (c2.getShape2() > i) {
                                        c2.setShape2(c2.getShape2() - 1);
                                    }
                                }
                            }
                        }
                        repaint();
                    }
                }
            }

        });

        //On mouse drag listener
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                super.mouseDragged(me);
                //Move current selected shape
                if (drawOption == 0 && selectedShapeIndex != -1) {
                    shapes.set(selectedShapeIndex, createNewShape(shapes.get(selectedShapeIndex), me.getX(), me.getY()));
                }

                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                super.mouseMoved(me);
                int index = 0;
                boolean found = false;
                //Check if mouse is over a shape and change selected shape, if more then one shape is found, select the last one.
                for (Shape s : shapes) {
                    if (s.contains(me.getPoint())) {
                        selectedShapeIndex = index;
                        //System.out.println("Shape selected: " + selectedShapeIndex);
                        found = true;
                    }
                    index++;
                }
                if (!found) {
                    selectedShapeIndex = -1;
                }
            }
        });


    }

    //this method is called when the panel is first created and every time the panel is resized or repainted
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        for (Shape s : shapes) {
            g2d.setColor(new Color(66, 133, 255));
            g2d.fill(s);
            g2d.setColor(new Color(79, 145, 255));
            g2d.draw(s);
            //g2d.setColor(new Color(0, 0, 0));
            renderText(g2d, s);
        }
        for (Connection c : connections) {
            g2d.setColor(new Color(66, 133, 255));
            g2d.drawLine(shapes.get(c.getShape1()).getBounds().getLocation().x + shapes.get(c.getShape1()).getBounds().width / 2,
                    shapes.get(c.getShape1()).getBounds().getLocation().y + shapes.get(c.getShape1()).getBounds().height / 2,
                    shapes.get(c.getShape2()).getBounds().getLocation().x + shapes.get(c.getShape2()).getBounds().width / 2,
                    shapes.get(c.getShape2()).getBounds().getLocation().y + shapes.get(c.getShape2()).getBounds().height / 2);
            //g2d.setColor(new Color(0, 0, 0));
            g2d.drawString(c.getText(), shapes.get(c.getShape1()).getBounds().getLocation().x + shapes.get(c.getShape1()).getBounds().width / 2 + (shapes.get(c.getShape2()).getBounds().x - shapes.get(c.getShape1()).getBounds().x) / 2,
                    shapes.get(c.getShape1()).getBounds().getLocation().y + shapes.get(c.getShape1()).getBounds().height / 2 + (shapes.get(c.getShape2()).getBounds().y - shapes.get(c.getShape1()).getBounds().y) / 2);
        }
        g2d.dispose();
    }

    //this method is used to render text on the shapes
    private void renderText(Graphics2D g2d, Shape s) {
        if (s instanceof Diamond) {
            Diamond d = (Diamond) s;
            //g2d.setColor(Color.BLACK);
            g2d.drawString(d.getText(), s.getBounds().getLocation().x + ShapeConstants.DIAMOND_WIDTH + 20, s.getBounds().getLocation().y + ShapeConstants.DIAMOND_HEIGHT / 2);
        }
        if (s instanceof Rectangle) {
            Rectangle r = (Rectangle) s;
            //g2d.setColor(Color.BLACK);
            g2d.drawString(r.getText(), s.getBounds().getLocation().x + ShapeConstants.RECTANGLE_WIDTH + 20, s.getBounds().getLocation().y + ShapeConstants.RECTANGLE_HEIGHT / 2);
        }
        if (s instanceof Ellipse) {
            Ellipse e = (Ellipse) s;
            //g2d.setColor(Color.BLACK);
            g2d.drawString(e.getText(), s.getBounds().getLocation().x + ShapeConstants.ELLIPSE_WIDTH + 20, s.getBounds().getLocation().y + ShapeConstants.ELLIPSE_HEIGHT / 2);
        }
    }

    public Shape createNewShape(Shape old, int x, int y) {
        //System.out.println("Old shape: "+old.getClass().getName());
        switch (old.getClass().getName()) {
            case "gui.shapes.Rectangle":
                Rectangle r = (Rectangle) old;
                return new Rectangle(x - ShapeConstants.RECTANGLE_WIDTH / 2, y - ShapeConstants.RECTANGLE_HEIGHT / 2, ShapeConstants.RECTANGLE_WIDTH, ShapeConstants.RECTANGLE_HEIGHT, r.getText());
            case "gui.shapes.Ellipse":
                Ellipse e = (Ellipse) old;
                return new Ellipse(x - ShapeConstants.ELLIPSE_WIDTH / 2, y - ShapeConstants.ELLIPSE_HEIGHT / 2, ShapeConstants.ELLIPSE_WIDTH, ShapeConstants.ELLIPSE_HEIGHT, ((Ellipse) old).getText());
            case "gui.shapes.Diamond":
                Diamond d = (Diamond) old;
                return new Diamond(x, y, d.getText());
            default:
                return null;
        }
    }

    public String promptForText() {
        String text = JOptionPane.showInputDialog("Enter text");
        return text;
    }

}
