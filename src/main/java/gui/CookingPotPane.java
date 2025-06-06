package gui;

import main.java.core.GameRegistry;
import main.java.core.Ingredient;
import javafx.scene.layout.HBox;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;
import java.util.List;

public class CookingPotPane extends HBox {
    private final List<Ingredient> selectedIngredients = new ArrayList<>();

    public CookingPotPane() {
        setPrefSize(300, 100);
        setStyle("-fx-border-color: gray; -fx-background-color: #fff8dc;");

        setOnDragOver(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String ingredientName = db.getString();
                Ingredient ing = GameRegistry.getIngredientByName(ingredientName); // or from GameEngine
                if (ing != null && !selectedIngredients.contains(ing)) {
                    selectedIngredients.add(ing);
                    getChildren().add(new main.java.gui.DraggableIngredientNode(ing)); // for visual feedback
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    public List<Ingredient> getSelectedIngredients() {
        return new ArrayList<>(selectedIngredients);
    }

    public void clearPot() {
        selectedIngredients.clear();
        getChildren().clear();
    }
}
