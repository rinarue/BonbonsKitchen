package gui;

import core.Dish;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Stack;

public class DraggableDishNode extends StackPane {
    private final Dish dish;
    private final int quantity;

    public DraggableDishNode(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;

        ImageView imageView = new ImageView(new Image(dish.getImagePath()));
        VBox dishNode = new VBox(5);
        dishNode.setAlignment(Pos.CENTER);

        //ImageView dishImg = new ImageView(new Image(dish.getImagePath()));
        //dishImg.setFitWidth(130);
        //dishImg.setFitHeight(130);
//
        //Label qtyLabel = new Label("x" + count);
        //qtyLabel.getStyleClass().add("label-regular");
//
        //dishNode.getChildren().addAll(dishImg, qtyLabel);
        //cookedDishBox.getChildren().add(dishNode);
        imageView.setFitWidth(130);
        imageView.setFitHeight(130);

        Label qtyLabel = new Label("x" + quantity);
        qtyLabel.getStyleClass().add("label-regular");
        //qtyLabel.setTextFill(Color.WHITE);
        //qtyLabel.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-padding: 2 6; -fx-background-radius: 8;");
        StackPane.setAlignment(qtyLabel, Pos.TOP_CENTER);
        qtyLabel.setTranslateY(5);

        getChildren().addAll(imageView, qtyLabel);
        this.setPickOnBounds(true);
        setOnDragDetected(e -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
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