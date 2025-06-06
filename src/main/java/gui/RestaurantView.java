package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class RestaurantView extends BaseView {
    private final ImageView backgroundImageView;
    private final ImageView counterImageView;
    private final Button tabletButton;
    private final HBox counterArea;

    public RestaurantView() {
        super();

        // Background Image
        backgroundImageView = new ImageView(new Image("assets/restaurant_bg.png"));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(900);
        backgroundImageView.setPreserveRatio(false);
        getChildren().add(0, backgroundImageView);

        // Counter Image
        counterImageView = new ImageView(new Image("assets/restaurant_counter.png"));
        counterImageView.setFitWidth(1440);
        counterImageView.setFitHeight(180);
        StackPane.setAlignment(counterImageView, Pos.BOTTOM_CENTER);
        getChildren().add(counterImageView);

        // Counter area (for customer nodes), positioned above counter image with some padding
        counterArea = new HBox(20);
        counterArea.setPadding(new Insets(0, 20, 100, 20));
        counterArea.setAlignment(Pos.BOTTOM_LEFT);
        counterArea.setPrefHeight(180);
        counterArea.setPickOnBounds(false);  // Allow clicks through transparent areas
        getChildren().add(counterArea);

        // Tablet Button on bottom right (above counter)
        tabletButton = new Button();
        ImageView tabletIcon = new ImageView(new Image("assets/tablet.png"));
        tabletIcon.setFitWidth(60);
        tabletIcon.setFitHeight(60);
        tabletButton.setGraphic(tabletIcon);
        tabletButton.setStyle("-fx-background-color: transparent;");
        StackPane.setAlignment(tabletButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(tabletButton, new Insets(0, 50, 110, 0));
        getChildren().add(tabletButton);

        // Navigation button to kitchen (bottom right)
        ImageView kitchenIcon = new ImageView(new Image("assets/kitchen.png"));
        kitchenIcon.setFitWidth(55);
        kitchenIcon.setFitHeight(55);
        navButton.setGraphic(kitchenIcon);
        setupNavigation(() -> {
            // Handled in main.java.gui.MainApp
        });
    }

    public HBox getCounterArea() {
        return counterArea;
    }

    public Button getTabletButton() {
        return tabletButton;
    }

    @Override
    public void setupNavigation(Runnable navigateAction) {
        navButton.setOnAction(e -> navigateAction.run());
    }
}
