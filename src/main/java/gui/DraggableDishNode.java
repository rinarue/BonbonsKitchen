package main.java.gui;

import main.java.core.Dish;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

public class DraggableDishNode extends StackPane {
    private final Dish dish;

    public DraggableDishNode(Dish dish) {
        this.dish = dish;
        ImageView imageView = new ImageView(new Image("file:main.resources.assets/dishes/" + dish.getName() + ".png"));
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);
        getChildren().add(imageView);

        setOnDragDetected(e -> {
            Dragboard db = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(dish.getName());
            db.setContent(content);
            db.setDragView(imageView.snapshot(null, null));
            e.consume();
        });
    }

    public Dish getDish() {
        return dish;
    }
}
