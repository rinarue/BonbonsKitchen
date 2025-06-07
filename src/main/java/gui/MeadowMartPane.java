package gui;

import core.GameEngine;
import core.GameRegistry;
import core.Ingredient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.animation.PauseTransition;

public class MeadowMartPane extends StackPane {
    private final MainApp mainApp;

    public MeadowMartPane(MainApp mainApp) {
        this.mainApp = mainApp;
        setPrefSize(720, 450);

        // Background image
        ImageView background = new ImageView(new Image("assets/meadowmart_bg.png"));
        background.setFitWidth(720);
        background.setFitHeight(450);

        // Content VBox inside scroll pane
        VBox ingredientList = new VBox(10);
        ingredientList.setPadding(new Insets(10));
        ingredientList.setAlignment(Pos.TOP_CENTER);

        for (Ingredient ingredient : GameRegistry.getAllIngredients()) {
            HBox itemRow = createIngredientRow(ingredient);
            ingredientList.getChildren().add(itemRow);
        }

        ScrollPane scrollPane = new ScrollPane(ingredientList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefViewportHeight(400);

        VBox layout = new VBox(10, scrollPane);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);

        getChildren().addAll(background, layout);
    }

    private HBox createIngredientRow(Ingredient ingredient) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(8));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setBackground(new Background(new BackgroundFill(Color.web("#fff7e6"), new CornerRadii(10), Insets.EMPTY)));
        row.setBorder(new Border(new BorderStroke(Color.web("#805b4c"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));
        row.setPrefHeight(70);
        row.setMaxWidth(660);

        // Icon
        ImageView icon = new ImageView(new Image(ingredient.getImagePath()));
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        // Name label
        Label nameLabel = new Label(ingredient.getName());
        nameLabel.getStyleClass().add("label-regular");

        // Price label
        Label priceLabel = new Label("$" + ingredient.getPrice());
        priceLabel.getStyleClass().add("label-regular");

        ImageView coinIcon = new ImageView(new Image("assets/coin_icon.png"));
        coinIcon.setFitWidth(50);
        coinIcon.setFitHeight(50);

        // Buy button
        Button buyButton = new Button();
        buyButton.setGraphic(coinIcon);
        buyButton.getStyleClass().add("button-nav");
        buyButton.setOnAction(e -> {
            if (GameEngine.getPlayer().spendMoney(ingredient.getPrice())) {
                GameEngine.getInventory().addIngredient(ingredient);
                mainApp.refreshMoneyDisplay();
            } else {
                showErrorMessage("Not enough money to buy " + ingredient.getName() + "!");
                //System.out.println("Not enough money to buy " + ingredient.getName());
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(icon, nameLabel, spacer, priceLabel, buyButton);
        return row;
    }

    private void showErrorMessage(String message) {
        Label errorLabel = new Label(message);
        errorLabel.setStyle("-fx-font-family: 'Nunito'; -fx-background-color: #dd81aa; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 10;");
        StackPane.setAlignment(errorLabel, Pos.TOP_CENTER);
        StackPane.setMargin(errorLabel, new Insets(20));
        getChildren().add(errorLabel);

        // Fade out after 2.5 seconds
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(1));
        pause.setOnFinished(e -> getChildren().remove(errorLabel));
        pause.play();
    }
}
