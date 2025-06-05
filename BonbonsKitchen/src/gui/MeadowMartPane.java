package gui;

import core.GameEngine;
import core.Ingredient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class MeadowMartPane extends StackPane {

    private final GameEngine engine;
    private final Stage stage;
    private final RestaurantView restaurantView;
    private final Label moneyLabel;

    public MeadowMartPane(GameEngine engine, Stage stage, RestaurantView restaurantView) {
        this.engine = engine;
        this.stage = stage;
        this.restaurantView = restaurantView;

        // Background image
        Image bgImage = new Image("file:assets/meadowmart_bg.png");
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(true);
        bgView.setFitWidth(1440);
        bgView.setFitHeight(900);

        // Foreground content
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setMaxWidth(600);
        layout.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 10;");

        // Title and money
        Label title = new Label("🌿 Meadow Mart 🌿");
        title.setStyle("-fx-font-style: 'Short Stack';-fx-font-size: 28px; -fx-font-weight: bold;");

        moneyLabel = new Label("Money: $" + engine.getPlayer().getMoney());
        moneyLabel.setStyle("-fx-font-size: 16px;");

        layout.getChildren().addAll(title, moneyLabel);

        // Ingredient list (scrollable)
        VBox ingredientList = new VBox(10);
        ingredientList.setAlignment(Pos.CENTER);

        List<Ingredient> ingredients = engine.getAvailableIngredients();
        for (Ingredient ingredient : ingredients) {
            HBox row = new HBox(10);
            row.setPrefWidth(500);
            row.setAlignment(Pos.CENTER_LEFT);

            ImageView imageView = new ImageView(new Image("file:" + ingredient.getImagePath()));
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);

            Label nameLabel = new Label(ingredient.getName());
            nameLabel.setPrefWidth(300); // or higher depending on your layout
            nameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black");


            Label priceLabel = new Label("$" + ingredient.getPrice());

            Button buyButton = new Button("Buy");
            buyButton.setOnAction(e -> {
                if (engine.getPlayer().getMoney() >= ingredient.getPrice()) {
                    engine.getPlayer().addMoney(-ingredient.getPrice());
                    engine.getPlayer().getInventory().addIngredient(ingredient, 1);
                    updateMoneyLabel();
                    restaurantView.updateView();
                } else {
                    buyButton.setText("Not enough 💸");
                    buyButton.setDisable(true);
                }
            });

            row.getChildren().addAll(imageView, nameLabel, priceLabel, buyButton);
            ingredientList.getChildren().add(row);
        }

// Wrap ingredient list in scroll pane
        Region ingredientRegion = new Region();
        ingredientRegion.setPrefHeight(Region.USE_COMPUTED_SIZE);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(ingredientList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400); // ensures scroll height
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // Back button
        Button backButton = new Button("Back to Restaurant");
        backButton.setOnAction(e -> stage.getScene().setRoot(restaurantView));

        layout.getChildren().addAll(scrollPane, backButton);

        getChildren().addAll(bgView, layout);
    }

    private void updateMoneyLabel() {
        moneyLabel.setText("Money: $" + engine.getPlayer().getMoney());
    }
}
