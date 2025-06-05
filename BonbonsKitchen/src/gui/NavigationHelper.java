package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NavigationHelper {
    public static MainApp mainApp;

    public static HBox createTopRightReturnButton(Stage stage, String buttonText) {
        Button returnButton = new Button(buttonText);
        returnButton.setOnAction(e -> {
            stage.getScene().setRoot(mainApp.getMainMenu());
        });
        HBox box = new HBox(returnButton);
        box.setAlignment(Pos.TOP_RIGHT);
        box.setPadding(new Insets(20, 20, 20, 20));  // uniform vertical + right padding
        return box;
    }

    public static HBox createBottomRightNavButton(Stage stage, String buttonText) {
        Button button = new Button("Go to " + buttonText);
        button.setOnAction(e -> {
            switch (buttonText) {
                case "Kitchen":
                    KitchenView kitchenView = new KitchenView(mainApp.getGameEngine(), stage, mainApp.getMainMenu(), mainApp);
                    stage.getScene().setRoot(kitchenView);
                    break;
                case "Restaurant":
                    RestaurantView restaurantView = new RestaurantView(mainApp.getGameEngine(), stage, mainApp.getMainMenu(), mainApp);
                    stage.getScene().setRoot(restaurantView);
                    break;
                case "Menu":
                    stage.getScene().setRoot(mainApp.getMainMenu());
                    break;
            }
        });

        HBox box = new HBox(button);
        box.setAlignment(Pos.BOTTOM_RIGHT);
        box.setPadding(new Insets(20, 20, 20, 20));
        return box;
    }
}