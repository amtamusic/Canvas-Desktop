package gui.shapes;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.geom.Ellipse2D;

@Data
@NoArgsConstructor
public class Ellipse extends Ellipse2D.Double {

    private String text;

    public Ellipse(double x, double y, double width, double height, String text) {
        super(x, y, width, height);
        this.setText(text);
    }

    public Ellipse(String text) {
        this.setText(text);
    }
}
