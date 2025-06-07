package main.java.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MainMenuView extends StackPane {
    private final Button startButton;
    private final Button exitButton;
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

        startButton = new Button("Start Game");
        startButton.getStyleClass().add("button-menu");

        exitButton = new Button("Exit");
        exitButton.getStyleClass().add("button-menu");

        menuBox.getChildren().addAll(startButton, exitButton);
        getChildren().add(menuBox);
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
