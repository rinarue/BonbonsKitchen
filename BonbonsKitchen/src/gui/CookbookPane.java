package gui;

import core.Dish;
import core.Recipe;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CookbookPane extends BorderPane {

    private final core.GameEngine gameEngine;
    private int currentIndex = 0;

    private Label dishNameLabel;
    private HBox ingredientImagesBox;

    public CookbookPane(core.GameEngine engine) {
        this.gameEngine = engine;

        setPadding(new Insets(10));

        dishNameLabel = new Label();
        ingredientImagesBox = new HBox(10);
        ingredientImagesBox.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(10, dishNameLabel, ingredientImagesBox);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);

        Button leftButton = new Button("<");
        Button rightButton = new Button(">");

        leftButton.setOnAction(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateDisplay();
            }
        });

        rightButton.setOnAction(e -> {
            if (currentIndex < gameEngine.getDishesMade().size() - 1) {
                currentIndex++;
                updateDisplay();
            }
        });

        HBox navBox = new HBox(10, leftButton, rightButton);
        navBox.setAlignment(Pos.CENTER);
        setBottom(navBox);

        updateDisplay();
    }

    private void updateDisplay() {
        List<Recipe> recipes = gameEngine.getDiscoveredRecipes();
        if (recipes.isEmpty()) {
            dishNameLabel.setText("No dishes cooked yet.");
            ingredientImagesBox.getChildren().clear();
            return;
        }

        Recipe recipe = recipes.get(currentIndex);
        dishNameLabel.setText("Dish: " + recipe.getName());

        ingredientImagesBox.getChildren().clear();

        for (core.Ingredient ing : recipe.getIngredients()) {
            ImageView img = new ImageView(new Image("file:" + ing.getImagePath()));
            img.setFitHeight(50);
            img.setPreserveRatio(true);
            ingredientImagesBox.getChildren().add(img);
        }
    }
}
