package gui;

import core.Ingredient;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

public class DraggableIngredientNode extends StackPane {
    private final Ingredient ingredient;

    public DraggableIngredientNode(Ingredient ingredient) {
        this.ingredient = ingredient;
        ImageView imageView = new ImageView(new Image(ingredient.getImagePath()));
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);
        getChildren().add(imageView);

        setOnDragDetected(e -> {
            Dragboard db = startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(ingredient.getName());
            db.setContent(content);
            db.setDragView(imageView.snapshot(null, null));
            e.consume();
        });
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}
