package gui.shapes;

import lombok.Data;
import lombok.NoArgsConstructor;
import utils.ShapeConstants;

import java.awt.geom.Path2D;

@Data
@NoArgsConstructor
public class Diamond extends Path2D.Double {

    private String text;

    public Diamond(double x, double y, String text) {
        moveTo(x - ShapeConstants.DIAMOND_WIDTH / 2, y);
        lineTo(x, y - ShapeConstants.DIAMOND_HEIGHT / 2);
        lineTo(x + ShapeConstants.DIAMOND_WIDTH / 2, y);
        lineTo(x, ShapeConstants.DIAMOND_HEIGHT / 2 + y);
        this.setText(text);
        closePath();
    }

    public Diamond(String text) {
        this.setText(text);
    }
}