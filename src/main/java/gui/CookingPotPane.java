package gui;

import core.Dish;
import core.GameEngine;
import core.GameRegistry;
import core.Ingredient;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.*;

public class CookingPotPane extends StackPane {

    private final VBox ingredientBox;          // Left pane: scrollable ingredients
    private final HBox potIngredientDisplay;  // Under pot: selected ingredients preview
    private final ImageView potImageView;     // Cooking pot image
    private final Button cookButton;
    private final Button clearButton;

    private final Map<Ingredient, Integer> availableQuantities = new HashMap<>();
    private final List<Ingredient> selectedIngredients = new ArrayList<>();

    private final GameEngine gameEngine = GameEngine.getInstance();
    private final StackPane overlay = new StackPane();


    public CookingPotPane() {
        setPrefSize(720, 450);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #fff8dc; -fx-border-color: #805b4c; -fx-border-radius: 15; -fx-background-radius: 15;");

        // Left side: Ingredient list inside scrollpane
        ingredientBox = new VBox(15);
        ingredientBox.setAlignment(Pos.TOP_CENTER);
        ingredientBox.setPadding(new Insets(10));
        refreshAvailableQuantities();
        populateIngredientBox();

        ScrollPane scrollPane = new ScrollPane(ingredientBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(320);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // Right side: pot image on top, then buttons, then selected ingredient display (drop box)
        VBox potSide = new VBox(10);  // spacing 10
        potSide.setAlignment(Pos.CENTER);

        potImageView = new ImageView(new Image("assets/cookingpot.png"));
        potImageView.setFitWidth(300);
        potImageView.setPreserveRatio(true);

        potIngredientDisplay = new HBox(10);
        potIngredientDisplay.setAlignment(Pos.CENTER);
        potIngredientDisplay.setMinHeight(80);
        potIngredientDisplay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-border-color: #805b4c; -fx-border-radius: 10; -fx-background-radius: 10;");
        potIngredientDisplay.setPadding(new Insets(10));

        cookButton = new Button("Cook!");
        cookButton.getStyleClass().add("button-cook");
        cookButton.setOnAction(e -> cookDish());

        clearButton = new Button("Clear Pot");
        clearButton.getStyleClass().add("button-cook");
        clearButton.setOnAction(e -> clearPot());

        HBox buttonBox = new HBox(10);  // spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(clearButton, cookButton);

        potSide.getChildren().addAll(potImageView, buttonBox, potIngredientDisplay);



        // New HBox to hold left and right sides with spacing
        HBox mainContent = new HBox(30);  // spacing 30
        mainContent.getChildren().addAll(scrollPane, potSide);
        mainContent.setAlignment(Pos.CENTER);

        // Add the mainContent HBox and overlay StackPane as children of this StackPane
        this.getChildren().addAll(mainContent, overlay);

        // Initialize overlay (make it transparent so it doesn't block clicks)
        overlay.setPickOnBounds(false);

        // Enable drag target for potIngredientDisplay (for removing ingredients back)
        potIngredientDisplay.setOnDragOver(this::handleDragOver);
        potIngredientDisplay.setOnDragDropped(this::handleDragDroppedOnPotIngredients);
    }

    private void refreshAvailableQuantities() {
        availableQuantities.clear();
        // Load current inventory counts from GameEngine inventory
        for (Ingredient ingredient : gameEngine.getAllIngredients()) {
            int qty = gameEngine.getInventory().getQuantity(ingredient);
            availableQuantities.put(ingredient, qty);
        }
    }

    private void populateIngredientBox() {
        ingredientBox.getChildren().clear();

        // Show ingredients in rows of 3
        List<Ingredient> allIngredients = new ArrayList<>(gameEngine.getAllIngredients());
        int rowCount = (int) Math.ceil(allIngredients.size() / 3.0);

        for (int row = 0; row < rowCount; row++) {
            HBox rowBox = new HBox(20);
            rowBox.setAlignment(Pos.CENTER);

            for (int col = 0; col < 3; col++) {
                int index = row * 3 + col;
                if (index >= allIngredients.size()) break;

                Ingredient ing = allIngredients.get(index);
                VBox ingredientVBox = createIngredientNode(ing);
                rowBox.getChildren().add(ingredientVBox);
            }

            ingredientBox.getChildren().add(rowBox);
        }
    }

    private VBox createIngredientNode(Ingredient ingredient) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(80);

        ImageView imgView = new ImageView(new Image(ingredient.getImagePath()));
        imgView.setFitWidth(64);
        imgView.setFitHeight(64);

        Label qtyLabel = new Label("x" + availableQuantities.getOrDefault(ingredient, 0));
        qtyLabel.getStyleClass().add("label-regular");

        box.getChildren().addAll(imgView, qtyLabel);

        if (availableQuantities.getOrDefault(ingredient, 0) > 0) {
            box.setCursor(Cursor.HAND);

            // Setup drag detected
            box.setOnDragDetected(event -> {
                Dragboard db = box.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(ingredient.getName());
                db.setContent(content);
                db.setDragView(imgView.snapshot(null, null));
                event.consume();
            });
        } else {
            box.setOpacity(0.5); // dim if none available
        }

        // Store qtyLabel as user data for updates
        box.setUserData(qtyLabel);

        return box;
    }

    // Called when ingredient is dropped into pot area (on potImageView or potIngredientDisplay)
    private void handleDragDroppedOnPotIngredients(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            String ingName = db.getString();
            Ingredient ingredient = GameRegistry.getIngredientByName(ingName);

            if (ingredient != null) {
                int qtyAvailable = availableQuantities.getOrDefault(ingredient, 0);
                if (qtyAvailable > 0) {
                    selectedIngredients.add(ingredient);
                    availableQuantities.put(ingredient, qtyAvailable - 1);
                    GameEngine.getInventory().useIngredient(ingredient); // Add this line
                    updateUIAfterIngredientChange();
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
            }
        } else {
            event.setDropCompleted(false);
        }
        event.consume();
    }

    // Allow drag over potIngredientDisplay if it contains a selected ingredient to remove
    private void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != this && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    private void updateUIAfterIngredientChange() {
        refreshAvailableQuantities();

        // Update ingredient box quantity labels
        for (var node : ingredientBox.getChildren()) {
            if (node instanceof HBox rowBox) {
                for (var ingBoxNode : rowBox.getChildren()) {
                    if (ingBoxNode instanceof VBox ingVBox) {
                        Label qtyLabel = (Label) ingVBox.getUserData();
                        if (qtyLabel != null) {
                            ImageView imgView = (ImageView) ingVBox.getChildren().get(0);
                            String ingName = getIngredientNameFromImage(imgView.getImage().getUrl());
                            Ingredient ing = GameRegistry.getIngredientByName(ingName);
                            int qty = availableQuantities.getOrDefault(ing, 0);
                            qtyLabel.setText("x" + qty);
                            ingVBox.setOpacity(qty > 0 ? 1.0 : 0.5);
                        }
                    }
                }
            }
        }

        // Update potIngredientDisplay to show selected ingredients with remove drag support
        potIngredientDisplay.getChildren().clear();
        for (Ingredient ing : selectedIngredients) {
            StackPane ingNode = createSelectedIngredientNode(ing);
            potIngredientDisplay.getChildren().add(ingNode);
        }
    }

    // Extract ingredient name from image URL path
    private String getIngredientNameFromImage(String imageUrl) {
        if (imageUrl == null) return "";
        int lastSlash = imageUrl.lastIndexOf('/');
        int lastDot = imageUrl.lastIndexOf('.');
        if (lastSlash >= 0 && lastDot > lastSlash) {
            return imageUrl.substring(lastSlash + 1, lastDot);
        }
        return imageUrl;
    }

    // Create ingredient display in pot with drag to remove support
    private StackPane createSelectedIngredientNode(Ingredient ingredient) {
        StackPane stack = new StackPane();

        ImageView imgView = new ImageView(new Image(ingredient.getImagePath()));
        imgView.setFitWidth(48);
        imgView.setFitHeight(48);
        stack.getChildren().add(imgView);
        stack.setCursor(Cursor.HAND);

        // Drag detected on ingredient in pot to remove it
        stack.setOnDragDetected(event -> {
            Dragboard db = stack.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(ingredient.getName());
            db.setContent(content);
            db.setDragView(imgView.snapshot(null, null));
            event.consume();
        });

        // Drag dropped on ingredient in pot means removing it (handled in drag dropped on potIngredientDisplay)
        stack.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasString() && db.getString().equals(ingredient.getName())) {
                removeIngredientFromPot(ingredient);
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });

        return stack;
    }

    private void removeIngredientFromPot(Ingredient ingredient) {
        selectedIngredients.remove(ingredient);
        int currentQty = availableQuantities.getOrDefault(ingredient, 0);
        availableQuantities.put(ingredient, currentQty + 1);
        updateUIAfterIngredientChange();
    }

    // Cooking logic - check selectedIngredients against known dishes
    private void cookDish() {
        if (selectedIngredients.isEmpty()) {
            showMessagePopup("Uh oh! You haven't selected any ingredients.");
            return;
        }

        List<Ingredient> sortedSelected = new ArrayList<>(selectedIngredients);
        sortedSelected.sort(Comparator.comparing(Ingredient::getName));

        Optional<Dish> matchedDish = GameRegistry.getAllDishes().stream()
                .filter(d -> ingredientsMatch(d.getIngredients(), sortedSelected))
                .findFirst();

        if (matchedDish.isPresent()) {
            Dish dish = matchedDish.get();

            if (!gameEngine.getDiscoveredDishes().contains(dish)) {
                gameEngine.discoverDish(dish);  // First-time discovery
            }

            gameEngine.addCookedDish(dish);  // Add cooked dish to inventory
            showMessagePopup("Cooked " + dish.getName() + "!");

        } else {
            showMessagePopup("Uh oh! That's not right.");
        }

        clearPot();
    }

    private boolean ingredientsMatch(List<Ingredient> dishIngredients, List<Ingredient> selected) {
        if (dishIngredients.size() != selected.size()) return false;

        List<String> dishNames = new ArrayList<>();
        for (Ingredient i : dishIngredients) dishNames.add(i.getName());
        List<String> selectedNames = new ArrayList<>();
        for (Ingredient i : selected) selectedNames.add(i.getName());

        Collections.sort(dishNames);
        Collections.sort(selectedNames);

        return dishNames.equals(selectedNames);
    }

    private void showDishDiscoveredPopup(Dish dish) {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #fff7e6; -fx-border-radius: 15; -fx-background-radius: 15;");

        ImageView dishImage = new ImageView(new Image(dish.getImagePath()));
        dishImage.setFitWidth(120);
        dishImage.setFitHeight(120);

        Label message = new Label("Discovered " + dish.getName() + "!");
        message.getStyleClass().add("label-regular");

        content.getChildren().addAll(dishImage, message);

        PopupHelper.showPopup(content, "Dish Discovered!");
    }

    private void showMessagePopup(String message) {
        Label errorLabel = new Label(message);
        errorLabel.setStyle("-fx-font-family: 'Nunito'; -fx-background-color: #dd81aa; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 10;");
        StackPane.setAlignment(errorLabel, Pos.TOP_CENTER);
        overlay.getChildren().add(errorLabel);

        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> overlay.getChildren().remove(errorLabel));
        pause.play();
    }

    private void clearPot() {
        selectedIngredients.clear();
        updateUIAfterIngredientChange();
        for (Ingredient ing : selectedIngredients) {
            gameEngine.getInventory().addIngredient(ing);
        }
        selectedIngredients.clear();
        updateUIAfterIngredientChange();
    }
}

//package gui;
//
//import core.GameRegistry;
//import core.Ingredient;
//import javafx.scene.layout.HBox;
//import javafx.scene.input.Dragboard;
//import javafx.scene.input.TransferMode;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CookingPotPane extends HBox {
//    private final List<Ingredient> selectedIngredients = new ArrayList<>();
//
//    public CookingPotPane() {
//        setPrefSize(720, 450);
//        setStyle("-fx-border-color: gray; -fx-background-color: #fff8dc;");
//
//        setOnDragOver(event -> {
//            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
//                event.acceptTransferModes(TransferMode.COPY);
//            }
//            event.consume();
//        });
//
//        setOnDragDropped(event -> {
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            if (db.hasString()) {
//                String ingredientName = db.getString();
//                Ingredient ing = GameRegistry.getIngredientByName(ingredientName); // or from GameEngine
//                if (ing != null && !selectedIngredients.contains(ing)) {
//                    selectedIngredients.add(ing);
//                    getChildren().add(new DraggableIngredientNode(ing)); // for visual feedback
//                    success = true;
//                }
//            }
//            event.setDropCompleted(success);
//            event.consume();
//        });
//    }
//
//    public List<Ingredient> getSelectedIngredients() {
//        return new ArrayList<>(selectedIngredients);
//    }
//
//    public void clearPot() {
//        selectedIngredients.clear();
//        getChildren().clear();
//    }
//}
