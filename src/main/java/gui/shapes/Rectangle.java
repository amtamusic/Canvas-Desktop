package gui.shapes;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.geom.Rectangle2D;

@Data
@NoArgsConstructor
public class Rectangle extends Rectangle2D.Double {
    private String text;

    public Rectangle(double x, double y, double width, double height, String text) {
        super(x, y, width, height);
        this.setText(text);
    }

    public Rectangle(String text) {
        this.setText(text);
    }
}
