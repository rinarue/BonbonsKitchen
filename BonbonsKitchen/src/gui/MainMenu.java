package gui;

import core.GameEngine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainMenu extends StackPane {

    public MainMenu(GameEngine engine, Stage primaryStage, MainApp mainApp) {
        // Load background image
        Image backgroundImage = new Image("assets/mainmenu.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(true);
        backgroundView.setSmooth(true);
        backgroundView.setCache(true);

        backgroundView.setFitWidth(1440);
        backgroundView.setFitHeight(900);

        // VBox for buttons
        VBox menuBox = new VBox(30);
        menuBox.setAlignment(Pos.CENTER);
        StackPane.setAlignment(menuBox, Pos.CENTER);
        StackPane.setMargin(menuBox, new Insets(0, 0, 0, 0));

        Button startButton = new Button("Start");
        Button tutorialButton = new Button("Tutorial");

        startButton.setPrefWidth(400);  // width in pixels
        startButton.setPrefHeight(100);  // height in pixels

        tutorialButton.setPrefWidth(400);
        tutorialButton.setPrefHeight(100);

        menuBox.setTranslateX(-280);
        menuBox.setTranslateY(170);

        startButton.setStyle("-fx-font-family: 'Short Stack'; -fx-font-size: 60px; -fx-text-fill: #805b4c");
        tutorialButton.setStyle("-fx-font-family: 'Short Stack'; -fx-font-size: 60px; -fx-text-fill: #805b4c");

        startButton.setOnAction(e -> {
            RestaurantView restaurantView = new RestaurantView(engine, primaryStage, this,mainApp);
            primaryStage.getScene().setRoot(restaurantView);
        });

        tutorialButton.setOnAction(e -> {
            TutorialView tutorialView = new TutorialView(primaryStage, this);
            primaryStage.getScene().setRoot(tutorialView);
        });

        menuBox.getChildren().addAll(startButton, tutorialButton);

        // Add image and menu to stack
        getChildren().addAll(backgroundView, menuBox);
    }
}


//package gui;
//
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class MainMenu extends VBox {
//
//    public MainMenu(core.GameEngine engine, Stage primaryStage) {
//        setAlignment(Pos.CENTER);
//        setSpacing(20);
//
//        Button startButton = new Button("Start Game");
//        Button tutorialButton = new Button("Tutorial");
//
//        startButton.setOnAction(e -> {
//            KitchenView kitchenView = new KitchenView(engine, primaryStage);
//            primaryStage.getScene().setRoot(kitchenView);
//        });
//
//        tutorialButton.setOnAction(e -> {
//            TutorialView tutorialView = new TutorialView(primaryStage, this);
//            primaryStage.getScene().setRoot(tutorialView);
//        });
//
//        getChildren().addAll(startButton, tutorialButton);
//    }
//}
//