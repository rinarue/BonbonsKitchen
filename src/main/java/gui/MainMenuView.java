package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MainMenuView extends StackPane {
    private final Button startButton;
    private final Button tutorialButton;
    private final ImageView backgroundImageView;

    public MainMenuView() {
        setPrefSize(1440, 900);

        backgroundImageView = new ImageView(new Image("assets/mainmenu.png"));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(900);
        backgroundImageView.setPreserveRatio(false);
        getChildren().add(backgroundImageView);

        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);

        startButton = new Button("Start");
        startButton.getStyleClass().add("button-menu");

        tutorialButton = new Button("Tutorial");
        tutorialButton.getStyleClass().add("button-menu");

        // ADD THIS:
        menuBox.getChildren().addAll(startButton, tutorialButton);
        menuBox.setTranslateX(-270);
        menuBox.setTranslateY(170);
        getChildren().add(menuBox);
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getTutorialButton() {
        return tutorialButton;
    }
}
