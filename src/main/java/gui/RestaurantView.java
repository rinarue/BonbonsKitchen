package gui;

import core.Customer;
import core.Dish;
import core.GameEngine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Map;

public class RestaurantView extends BaseView {
    private final ImageView backgroundImageView;
    private final ImageView counterImageView;
    private final Button tabletButton;
    private final HBox counterArea;
    private CustomerPane activeCustomerPane;

    public RestaurantView() {
        super();

        // Background Image
        backgroundImageView = new ImageView(new Image("assets/restaurant_bg.png"));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(900);
        getChildren().add(0, backgroundImageView);

        // Counter Image
        counterImageView = new ImageView(new Image("assets/restaurant_counter.png"));
        counterImageView.setFitWidth(1440);
        counterImageView.setFitHeight(900);
        getChildren().add(counterImageView);

        // Counter area (for customer nodes), positioned above counter image with some padding
        counterArea = new HBox(20);
        counterArea.setPadding(new Insets(0, 20, 100, 20));
        counterArea.setAlignment(Pos.BOTTOM_LEFT);
        counterArea.setPrefHeight(180);
        counterArea.setPickOnBounds(false);  // Allow clicks through transparent areas
        getChildren().add(counterArea);

        // Tablet Button on bottom right (above counter)
        tabletButton = new Button();
        ImageView tabletIcon = new ImageView(new Image("assets/tablet.png"));
        tabletIcon.setFitWidth(331);
        tabletIcon.setFitHeight(265);
        tabletButton.setGraphic(tabletIcon);
        tabletButton.getStyleClass().add("button-nav");  // add the style class
        StackPane.setAlignment(tabletButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(tabletButton, new Insets(0, 245, 190, 0));
        getChildren().add(tabletButton);

        for (Map.Entry<Dish, Integer> entry : GameEngine.getAllCookedDishes().entrySet()) {
            Dish dish = entry.getKey();
            int qty = entry.getValue();

            if (qty > 0) {
                Node dishNode = createDraggableDishView(dish, qty);
                dishPane.getChildren().add(dishNode);
            }
        }

        // Navigation button to kitchen (bottom right)
        ImageView kitchenIcon = new ImageView(new Image("assets/kitchen.png"));
        kitchenIcon.setFitWidth(76);
        kitchenIcon.setFitHeight(70);
        navButton.setGraphic(kitchenIcon);
        StackPane.setAlignment(navButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(navButton, new Insets(20));
        setupNavigation(() -> {
            // Handled in main.java.gui.MainApp
        });
        navButton.setTranslateY(-40);
        navButton.toFront();
    }

    public void showNextCustomer(Customer customer) {
        if (activeCustomerPane != null) {
            // Should not add if there is already a customer displayed
            return;
        }
        activeCustomerPane = new CustomerPane(customer, this);
        counterArea.getChildren().add(activeCustomerPane);
    }

    public void removeActiveCustomer() {
        if (activeCustomerPane != null) {
            counterArea.getChildren().remove(activeCustomerPane);
            activeCustomerPane = null;
        }
    }

    public void spawnNextCustomer() {
        // Pick a random customer from GameEngine (or your customer list)
        List<Customer> customers = GameEngine.getInstance().getCustomers();

        // Simple example: pick random customer
        if (!customers.isEmpty()) {
            Customer nextCustomer = customers.get((int)(Math.random() * customers.size()));
            showNextCustomer(nextCustomer);
        }
    }

    private Node createDraggableDishView(Dish dish, int qty) {
        ImageView dishView = new ImageView(new Image(dish.getImagePath()));
        dishView.setFitWidth(64);
        dishView.setFitHeight(64);

        dishView.setOnDragDetected(e -> {
            Dragboard db = dishView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(dish.getName());
            db.setContent(content);
            db.setDragView(dishView.snapshot(null, null));
            e.consume();
        });

        return dishView;
    }


    public HBox getCounterArea() {
        return counterArea;
    }

    public Button getTabletButton() {
        return tabletButton;
    }

    public void addCustomer(Customer customer) {
        CustomerPane customerPane = new CustomerPane(customer, this); // pass RestaurantView to allow removal
        counterArea.getChildren().add(customerPane);
    }

    public CustomerPane getActiveCustomer() {
        return activeCustomerPane;
    }

    @Override
    public void setupNavigation(Runnable navigateAction) {
        navButton.setOnAction(e -> navigateAction.run());
    }
}
