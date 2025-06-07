package gui;

import core.Customer;
import core.Dish;
import core.GameEngine;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RestaurantView extends BaseView {
    private VBox dayCompletePopup;
    private final ImageView backgroundImageView;
    private final ImageView counterImageView;
    private final Button tabletButton;
    private final HBox counterArea;
    private CustomerPane activeCustomerPane;
    private static final HBox cookedDishBox = new HBox(10); // NEW
    private final Pane overlayLayer = new Pane();
    private final Set<Customer> customersShownToday = new HashSet<>();
    private SpeechBubble currentBubble;
    private boolean dayStartPopupShown = false;
    private boolean dayCompletePopupShown = false;



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
        counterArea.setPadding(new Insets(0, 0, 100, 0));
        counterArea.setAlignment(Pos.BOTTOM_LEFT);
        counterArea.setPrefHeight(40);
        counterArea.setMaxHeight(40);
        counterArea.setPickOnBounds(false);  // Allow clicks through transparent areas
        getChildren().add(counterArea);

        overlayLayer.setPickOnBounds(false); // So it doesn't block input
        getChildren().add(overlayLayer);

        // Cooked Dish Box (fixed size, positioned on counter)
        cookedDishBox.setPadding(new Insets(10));
        cookedDishBox.setAlignment(Pos.CENTER_LEFT);
        cookedDishBox.setPrefSize(800, 100);
        ScrollPane cookedScrollPane = getScrollPane();
        //cookedScrollPane.setStyle("-fx-background-color: rgba(255, 0, 0, 0.2);"); // Transparent red

        getChildren().add(cookedScrollPane);

        // Add background or style if you want (optional)
        //cookedDishBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10;");

        //getChildren().add(cookedDishBox);

        // Populate cookedDishBox with existing dishes
        updateCookedDishDisplay();

        for (Map.Entry<Dish, Integer> entry : GameEngine.getAllCookedDishes().entrySet()) {
            Dish dish = entry.getKey();
            int qty = entry.getValue();

            if (qty > 0) {
                Node dishNode = new DraggableDishNode(dish, qty);
                cookedDishBox.getChildren().add(dishNode);
            }
        }

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

    private static ScrollPane getScrollPane() {
        ScrollPane cookedScrollPane = new ScrollPane(cookedDishBox);
        cookedScrollPane.setPickOnBounds(false);
        cookedScrollPane.setPrefSize(800, 100);
        cookedScrollPane.setFitToHeight(false);
        cookedScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cookedScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cookedScrollPane.setMaxHeight(150); // explicitly set max height
        cookedScrollPane.setTranslateX(-300);
        cookedScrollPane.setTranslateY(200);
        cookedScrollPane.setMaxWidth(800);

        cookedScrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        return cookedScrollPane;
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
        if (currentBubble != null) {
            overlayLayer.getChildren().remove(currentBubble);
            currentBubble = null;
        }

        // ✅ Check if day is done
        if (GameEngine.getInstance().isDayComplete()) {
            showDayCompletePopup();
        } else {
            spawnNextCustomer(); // only if the day isn't over
        }
    }

    public void spawnNextCustomer() {
        if (activeCustomerPane != null) return; // ✅ Don't spawn if one is already active

        List<Customer> todaysCustomers = GameEngine.getInstance().getCustomersToday();

        for (Customer customer : todaysCustomers) {
            if (!customersShownToday.contains(customer)) {
                customersShownToday.add(customer);
                showNextCustomer(customer);
                return;
            }
        }

        // ✅ All customers shown
        if (GameEngine.getInstance().isDayComplete()) {
            showDayCompletePopup();
        }
        //// At this point, no customers left to show
        //if (activeCustomerPane != null) {
        //    Customer served = activeCustomerPane.getCustomer();
        //    GameEngine.getInstance().customerServed(served);
        //    removeActiveCustomer();
        //    //spawnNextCustomer(); // <== this is missing!
        //}
//
        //// Try to spawn a customer that hasn't been shown yet
        //List<Customer> customersToday = GameEngine.getInstance().getCustomersToday();
        //for (Customer customer : todaysCustomers) {
        //    if (!customersShownToday.contains(customer)) {
        //        customersShownToday.add(customer);
        //        showNextCustomer(customer);
        //        return;
        //    }
        //}

        // Show end-of-day popup only once
        ///if (GameEngine.getInstance().isDayComplete()) {
        ///    showDayCompletePopup();
        ///}
        //List<Customer> allCustomers = GameEngine.getInstance().getCustomers();
//
        //// Filter out already shown customers
        //List<Customer> available = allCustomers.stream()
        //        .filter(c -> !customersShownToday.contains(c))
        //        .toList();
//
        //if (!available.isEmpty()) {
        //    Customer nextCustomer = available.get((int)(Math.random() * available.size()));
        //    customersShownToday.add(nextCustomer);
        //    showNextCustomer(nextCustomer);
        //}
    }

    //private Node createDraggableDishView(Dish dish, int qty) {
    //    VBox dishNode = new VBox(5);
    //    dishNode.setAlignment(Pos.CENTER);
//
    //    ImageView dishView = new ImageView(new Image(dish.getImagePath()));
    //    dishView.setFitWidth(130);
    //    dishView.setFitHeight(130);
//
    //    Label qtyLabel = new Label("x" + qty);
    //    qtyLabel.getStyleClass().add("label-regular");
//
    //    dishNode.getChildren().addAll(dishView, qtyLabel);
//
    //    // Drag logic
    //    dishView.setOnDragDetected(e -> {
    //        Dragboard db = dishView.startDragAndDrop(TransferMode.MOVE);
    //        ClipboardContent content = new ClipboardContent();
    //        content.putString(dish.getName());
    //        db.setContent(content);
    //        db.setDragView(dishView.snapshot(null, null));
    //        e.consume();
    //    });
//
    //    return dishNode;
    //}


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

    public static void updateCookedDishDisplay() {
        cookedDishBox.getChildren().clear();

        Map<Dish, Integer> cookedDishes = GameEngine.getAllCookedDishes();

        for (Map.Entry<Dish, Integer> entry : cookedDishes.entrySet()) {
            Dish dish = entry.getKey();
            int count = entry.getValue();

            // Only show dishes with quantity > 0
            if (count > 0) {
                DraggableDishNode dishNode = new DraggableDishNode(dish, count);
                cookedDishBox.getChildren().add(dishNode);
            }
        }
    }

    public void showSpeechBubbleOverCustomer(CustomerPane customerPane, SpeechBubble bubble) {
        if (!overlayLayer.getChildren().contains(bubble)) {
            overlayLayer.getChildren().add(bubble);
        }

        //// Use scene coordinates for precise placement
        //double sceneX = customerPane.localToScene(0, 0).getX();
        //double sceneY = customerPane.localToScene(0, 0).getY();

        //// Convert to overlayLayer coordinates
        //Point2D localPoint = overlayLayer.sceneToLocal(sceneX, sceneY);
        //double localX = localPoint.getX();
        //double localY = localPoint.getY();

        // Position bubble relative to customerPane position
        //bubble.setLayoutX(localX + 100);  // Offset X as needed
        //bubble.setLayoutY(localY - 80);   // Offset Y as needed
        bubble.setLayoutX(700);
        bubble.setLayoutY(200);
        currentBubble = bubble;

        currentBubble.toFront();
    }

    public void showDayStartPopup() {
        VBox popup = new VBox(20);
        popup.setAlignment(Pos.CENTER);
        popup.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-background-radius: 20;");
        popup.setPadding(new Insets(30));
        popup.setLayoutX(500);
        popup.setLayoutY(250);

        Label message = new Label("Welcome to Day " + GameEngine.getInstance().getCurrentDay() + "!");
        message.setStyle("-fx-font-size: 28px;");
        message.getStyleClass().add("label-regular");

        Button beginDayBtn = new Button("Start Serving");
        beginDayBtn.getStyleClass().add("button-cook");
        beginDayBtn.setOnAction(e -> {
            getChildren().remove(popup);
            spawnNextCustomer(); // ✅ Begin gameplay only after popup is closed
        });

        popup.getChildren().addAll(message, beginDayBtn);
        getChildren().add(popup);
        popup.toFront(); // <-- Important!

    }

    public void showDayCompletePopup() {
        if (dayCompletePopupShown) return;
        dayCompletePopupShown = true;

        dayCompletePopup = new VBox(20);
        dayCompletePopup.setAlignment(Pos.CENTER);
        dayCompletePopup.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-background-radius: 20;");
        dayCompletePopup.setPadding(new Insets(30));
        dayCompletePopup.setLayoutX(500);
        dayCompletePopup.setLayoutY(250);

        Label message = new Label("Day " + GameEngine.getInstance().getCurrentDay() + " Complete!");
        message.setStyle("-fx-font-size: 32px;");
        message.getStyleClass().add("label-regular");

        Button nextDayBtn = new Button("Start Next Day");
        nextDayBtn.getStyleClass().add("button-cook");
        nextDayBtn.setOnAction(e -> {
            getChildren().remove(dayCompletePopup);
            dayCompletePopup = null;
            GameEngine.getInstance().advanceDay();
            dayStartPopupShown = false; //set to true after showing
            startDay();
        });

        dayCompletePopup.getChildren().addAll(message, nextDayBtn);
        getChildren().add(dayCompletePopup);
        dayCompletePopup.toFront(); // <-- Important!
    }


    public void removeSpeechBubble(SpeechBubble bubble) {
        overlayLayer.getChildren().remove(bubble); // CORRECT
    }

    public void startDay() {
        GameEngine.getInstance().startNewDay();
        customersShownToday.clear(); // clear record of past customers
        // ✅ Reset the popup flag for the new day
        dayCompletePopupShown = false;
        if (!dayStartPopupShown) {
            showDayStartPopup();
            dayStartPopupShown = true;
        } else {
            // If popup already shown, just spawn first customer immediately
            spawnNextCustomer();
        }
    }

    @Override
    public void setupNavigation(Runnable navigateAction) {
        navButton.setOnAction(e -> navigateAction.run());
    }
}
