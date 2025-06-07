package gui;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class BulletinPane extends VBox {

    public BulletinPane() {
        setPrefSize(720, 450);
        setStyle("-fx-border-color: gray; -fx-background-color: #fff8dc;");
        setSpacing(10);

        Text header = new Text("Customer Bulletin");
        getChildren().add(header);

        // You can populate this with customer hints/preferences
    }
}
