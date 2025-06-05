package gui;

import core.Dish;
import core.Ingredient;
import core.Inventory;
import core.Recipe;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CookingPotPane extends BorderPane {

    private final core.GameEngine gameEngine;
    private final KitchenView parentKitchenView;

    private final VBox inventoryBox;
    private final VBox potBox;
    private final List<Ingredient> potIngredients = new ArrayList<>();

    private final Text feedbackText;

    public CookingPotPane(core.GameEngine engine, KitchenView parent) {
        this.gameEngine = engine;
        this.parentKitchenView = parent;

        setPadding(new Insets(10));

        Label titleLabel = new Label("Cooking Pot");

        inventoryBox = new VBox(10);
        inventoryBox.setPrefWidth(250);
        inventoryBox.setPadding(new Insets(5));
        inventoryBox.setStyle("-fx-border-color: gray; -fx-border-width: 2;");
        inventoryBox.getChildren().add(new Label("Inventory"));

        potBox = new VBox(10);
        potBox.setPrefWidth(250);
        potBox.setPadding(new Insets(5));
        potBox.setStyle("-fx-border-color: gray; -fx-border-width: 2;");
        potBox.getChildren().add(new Label("Pot (Drag ingredients here)"));

        feedbackText = new Text();

        HBox centerBox = new HBox(20, inventoryBox, potBox);
        centerBox.setAlignment(Pos.CENTER);

        Button cookButton = new Button("Cook Dish");
        cookButton.setOnAction(e -> cookDish());

        VBox mainBox = new VBox(10, titleLabel, centerBox, cookButton, feedbackText);
        mainBox.setAlignment(Pos.CENTER);

        setCenter(mainBox);

        refreshInventory();
        setupDragAndDrop();
    }

    private void refreshInventory() {
        inventoryBox.getChildren().removeIf(node -> node instanceof HBox);

        Inventory inventory = gameEngine.getPlayer().getInventory();

        for (Ingredient ingredient : inventory.getIngredients()) {
            int qty = inventory.getQuantity(ingredient);
            if (qty <= 0) continue;

            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);

            ImageView img = new ImageView(new Image("file:" + ingredient.getImagePath()));
            img.setFitHeight(40);
            img.setPreserveRatio(true);

            Label nameLabel = new Label(ingredient.getName() + " x" + qty);

            row.getChildren().addAll(img, nameLabel);

            // Drag detected on ingredient row to drag ingredient name
            row.setOnDragDetected(event -> {
                Dragboard db = row.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(ingredient.getName());
                db.setContent(content);
                event.consume();
            });

            inventoryBox.getChildren().add(row);
        }
    }

    private void setupDragAndDrop() {
        // Pot box drag over - accept ingredient names from inventory
        potBox.setOnDragOver(event -> {
            if (event.getGestureSource() != potBox && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        // Drop ingredient on pot box
        potBox.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String ingredientName = db.getString();
                Ingredient ing = getIngredientByName(ingredientName);
                if (ing != null) {
                    // Add to pot ingredients if enough inventory quantity
                    if (gameEngine.getPlayer().getInventory().getQuantity(ing) > countInPot(ing)) {
                        potIngredients.add(ing);
                        refreshPot();
                        success = true;
                        feedbackText.setText("Added " + ing.getName() + " to pot.");
                    } else {
                        feedbackText.setText("Not enough " + ing.getName() + " in inventory.");
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // Drag from potBox to remove ingredient
        potBox.setOnDragDetected(event -> {
            // Drag ingredient only if clicked on one
            // Here for simplicity, no direct drag to remove - user must click ingredient to remove
            event.consume();
        });

        // Allow removing ingredients by clicking them in pot
        potBox.setOnMouseClicked(event -> {
            if (!potIngredients.isEmpty()) {
                // Remove last ingredient added (simple)
                Ingredient removed = potIngredients.remove(potIngredients.size() - 1);
                refreshPot();
                feedbackText.setText("Removed " + removed.getName() + " from pot.");
            }
        });
    }

    private int countInPot(Ingredient ing) {
        int count = 0;
        for (Ingredient i : potIngredients) {
            if (i.equals(ing)) count++;
        }
        return count;
    }

    private void refreshPot() {
        potBox.getChildren().removeIf(node -> node instanceof HBox);

        for (Ingredient ing : potIngredients) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);

            ImageView img = new ImageView(new Image("file:" + ing.getImagePath()));
            img.setFitHeight(40);
            img.setPreserveRatio(true);

            Label nameLabel = new Label(ing.getName());

            row.getChildren().addAll(img, nameLabel);

            potBox.getChildren().add(row);
        }
    }

    private Ingredient getIngredientByName(String name) {
        for (Ingredient ing : gameEngine.getPlayer().getInventory().getIngredients()) {
            if (ing.getName().equals(name)) return ing;
        }
        return null;
    }

    private void cookDish() {
        if (potIngredients.isEmpty()) {
            feedbackText.setText("Add some ingredients to cook a dish!");
            return;
        }
        // Create dish from pot ingredients
        String dishName = "Dish #" + (gameEngine.getDishesMade().size() + 1);
        List<Ingredient> ingredientsCopy = new ArrayList<>(potIngredients);

        // Check if player has enough ingredients
        Inventory inv = gameEngine.getPlayer().getInventory();
        for (Ingredient ing : ingredientsCopy) {
            if (inv.getQuantity(ing) < countInList(ingredientsCopy, ing)) {
                feedbackText.setText("Not enough " + ing.getName() + " to cook.");
                return;
            }
        }

        // Try to match a known recipe
        Recipe matchedRecipe = null;
        for (Recipe recipe : gameEngine.getKnownRecipes()) {
            if (recipe.matches(ingredientsCopy)) {
                matchedRecipe = recipe;
                break;
            }
        }

        if (matchedRecipe != null) {
            gameEngine.discoverRecipe(matchedRecipe);
            Dish dish = new Dish(matchedRecipe.getName(), ingredientsCopy, matchedRecipe.getBasePrice());
            gameEngine.addDish(dish);
            feedbackText.setText("Cooked " + dish.getName() + "! It's a known recipe.");
        } else {
            // Generic dish if no match
            dishName = "Mystery Dish #" + (gameEngine.getDishesMade().size() + 1);
            Dish dish = new Dish(dishName, ingredientsCopy, 5); // generic price
            gameEngine.addDish(dish);
            feedbackText.setText("Cooked a new mystery dish!");
        }
    }

    private int countInList(List<Ingredient> list, Ingredient ing) {
        int count = 0;
        for (Ingredient i : list) {
            if (i.equals(ing)) count++;
        }
        return count;
    }
}
