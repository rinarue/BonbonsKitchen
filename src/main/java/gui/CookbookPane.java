package gui;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CookbookPane extends VBox {

    public CookbookPane() {
        setPrefSize(720, 450);
        setStyle("-fx-border-color: gray; -fx-background-color: #fff8dc;");
        setSpacing(10);

        // Placeholder content
        Text header = new Text("Cookbook");
        getChildren().add(header);

        // You can add discovered recipes here dynamically
    }
}
