package gui;

import core.Customer;
import core.GameEngine;
import core.Dish;
import core.GameRegistry;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class CustomerPane extends StackPane {
    private final Customer customer;
    private final Label reactionLabel = new Label();
    private final RestaurantView parentView;

    public CustomerPane(Customer customer, RestaurantView parentView) {
        this.customer = customer;
        this.parentView = parentView;

        VBox content = new VBox(5);
        content.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(new Image(customer.getImagePath()));
        imageView.setFitWidth(560);
        imageView.setFitHeight(500);
        imageView.setTranslateX(130);
        imageView.setTranslateY(-67);

        content.getChildren().addAll(imageView, reactionLabel);

        getChildren().add(content);
        //setStyle("-fx-border-color: #a9a9a9; -fx-padding: 10; -fx-background-color: #f0f0f0;");

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
                int basePrice = dish.getSellPrice();
                if (dish.containsIngredient(customer.getFavoriteIngredient())) {
                    basePrice += 3;
                }
                int payment = basePrice;

                // Show reaction
                reactionLabel.setText(liked ? "Yum! 🍽️" : "Meh... 😐");

                // Update player money
                GameEngine.getPlayer().addMoney(payment);

                // Update money UI
                parentView.updateMoneyDisplay((int) GameEngine.getPlayer().getMoney());


                // Optionally: mark customer as served and remove them from scene
                this.setDisable(true);
                this.setOpacity(0.5);

                // ⏳ Remove customer after 2 seconds
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> {
                    parentView.removeActiveCustomer(); // remove this customer from view

                    // Notify parent to spawn next customer
                    parentView.spawnNextCustomer();
                });
                pause.play();

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
