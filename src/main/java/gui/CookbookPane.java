package gui;

import core.Dish;
import core.GameEngine;
import core.Ingredient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;

public class CookbookPane extends StackPane {
    public GameEngine gameEngine;

    public CookbookPane() {
        this.gameEngine = GameEngine.getInstance();

        setPrefSize(720, 450);

        // Background
        setStyle("-fx-background-color: #fff8dc; -fx-border-color: #805b4c; -fx-border-radius: 15; -fx-background-radius: 15;");

        // Outer layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);

        VBox recipeList = new VBox(10);
        recipeList.setAlignment(Pos.TOP_CENTER);
        recipeList.setPadding(new Insets(10));

        // get list of discovered dishes
        List<Dish> discoveredDishes = gameEngine.getDiscoveredDishes();

        for (Dish dish : discoveredDishes) {
            List<Ingredient> ingredients = dish.getIngredients();  // get ingredients for each dish
            HBox recipeRow = createRecipeRow(dish, ingredients);
            recipeList.getChildren().add(recipeRow);
        }

        ScrollPane scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        layout.getChildren().add(scrollPane);
        getChildren().add(layout);
    }

    private HBox createRecipeRow(Dish dish, List<Ingredient> ingredients) {
        HBox row = new HBox(20);
        row.setPadding(new Insets(10));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setBackground(new Background(new BackgroundFill(Color.web("#fff7e6"), new CornerRadii(10), Insets.EMPTY)));
        row.setBorder(new Border(new BorderStroke(Color.web("#805b4c"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));
        row.setPrefHeight(100);
        row.setMaxWidth(660);

        // Dish Image
        ImageView dishImage = new ImageView(new Image(dish.getImagePath()));
        dishImage.setFitWidth(80);
        dishImage.setFitHeight(80);

        // Dish Name
        Label nameLabel = new Label(dish.getName());
        nameLabel.getStyleClass().add("label-regular");
        nameLabel.setStyle("-fx-font-size: 18;");

        // Ingredients
        // Ingredient Images container
        HBox ingredientImages = new HBox(5);
        ingredientImages.setAlignment(Pos.CENTER_LEFT);

        for (Ingredient ing : ingredients) {
            ImageView ingImage = new ImageView(new Image(ing.getImagePath()));
            ingImage.setFitWidth(40);  // smaller than dish image
            ingImage.setFitHeight(40);
            ingredientImages.getChildren().add(ingImage);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(dishImage, nameLabel, spacer, ingredientImages);
        return row;
    }
}
