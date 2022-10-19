package gui.shapes;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Connection {
    private int shape1;
    private int shape2;
    private String text;

    public Connection(int selectedShapeConnector1, int selectedShapeConnector2, String text) {
        this.shape1 = selectedShapeConnector1;
        this.shape2 = selectedShapeConnector2;
        this.text = text;
    }

    public Connection(String text) {
        this.text = text;
    }
}
