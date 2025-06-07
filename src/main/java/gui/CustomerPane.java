package gui;

import main.java.core.Customer;
import main.java.core.Dish;
import main.java.core.GameEngine;
import main.java.core.GameRegistry;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CustomerPane extends StackPane {
    private final Customer customer;
    private final Label reactionLabel = new Label();

    public CustomerPane(Customer customer) {
        this.customer = customer;
        VBox content = new VBox(5);
        content.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(new Image(customer.getImagePath()));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Label nameLabel = new Label(customer.getName());
        content.getChildren().addAll(imageView, nameLabel, reactionLabel);

        getChildren().add(content);
        setStyle("-fx-border-color: #a9a9a9; -fx-padding: 10; -fx-background-color: #f0f0f0;");

        setOnDragOver(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        setOnDragDropped(this::handleDishDrop);
    }

    private void handleDishDrop(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString()) {
            String dishName = db.getString();
            Dish dish = GameRegistry.getDishByName(dishName);

            if (dish != null) {
                boolean liked = dish.containsIngredient(customer.getFavoriteIngredient());
                int payment = (int) (liked ? dish.getSellPrice() : dish.getSellPrice() / 2);

                // Show reaction
                reactionLabel.setText(liked ? "Yum! 🍽️" : "Meh... 😐");

                // Update player money
                GameEngine.getPlayer().addMoney(payment);

                // Optionally: mark customer as served and remove them from scene
                this.setDisable(true);
                this.setOpacity(0.5);

                success = true;
            }
        }

        event.setDropCompleted(success);
        event.consume();
    }

    public Customer getCustomer() {
        return customer;
    }
}
