package gui;

import core.Customer;
import core.GameEngine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RestaurantView extends StackPane {

    private final GameEngine engine;
    private final Stage stage;
    private final MainMenu mainMenu;
    private final Label moneyLabel;
    private final MainApp mainApp;  // <-- Add this
    private final Label customerHint;

    public RestaurantView(GameEngine engine, Stage stage, MainMenu mainMenu, MainApp mainApp) {
        this.engine = engine;
        this.stage = stage;
        this.mainMenu = mainMenu;
        this.mainApp = mainApp;

        // --- BACKGROUND ---
        ImageView background = new ImageView(new Image("assets/restaurant_bg.png"));
        background.setPreserveRatio(false);
        background.setFitWidth(1440);
        background.setFitHeight(900);

        // --- COUNTER IMAGE (layered over background) ---
        ImageView counter = new ImageView(new Image("assets/counter.png"));
        counter.setPreserveRatio(true);
        counter.setFitWidth(1440);

        // --- CUSTOMER IMAGE (behind counter) ---
        Customer currentCustomer = engine.getCurrentCustomer(); // or however you get the current one
        ImageView customerImageView = new ImageView(new Image("assets/customers/" + currentCustomer.getImageFileName()));
        customerImageView.setFitWidth(1350);
        customerImageView.setPreserveRatio(true);
        customerImageView.setTranslateY(10);

        // --- TABLET IMAGE (clickable) ---
        ImageView tablet = new ImageView(new Image("assets/tablet.png"));
        tablet.setFitWidth(1440);
        tablet.setPreserveRatio(true);
        tablet.setOnMouseClicked(e -> {
            MeadowMartPane meadowMartPane = new MeadowMartPane(engine, stage, this);
            stage.getScene().setRoot(meadowMartPane);
        });
        DropShadow glow = new DropShadow();
        glow.setColor(Color.ORCHID);
        glow.setRadius(20);

        tablet.setOnMouseEntered(e -> tablet.setEffect(glow));
        tablet.setOnMouseExited(e -> tablet.setEffect(null));

        // --- Dish display (bottom left corner) ---
        Label dishList = new Label("Dishes made will appear here");
        dishList.setStyle("-fx-font-family: 'Short Stack'; -fx-border-color: black; -fx-background-color: white;");
        dishList.setPadding(new Insets(10));
        dishList.setMaxWidth(200);
        dishList.setWrapText(true);
        dishList.setTranslateX(-300);
        dishList.setTranslateY(200);

        // --- Customer message box ---
        customerHint = new Label("Customer’s hint about favorite ingredient.");
        customerHint.setStyle("-fx-font-family: 'Short Stack'; -fx-border-color: gray; -fx-background-color: white; -fx-padding: 10;");
        customerHint.setTranslateY(-200);

        // Top bar
        BorderPane topBar = new BorderPane();

        // --- MONEY ICON WITH LABEL STACK ---
        ImageView moneyIcon = new ImageView(new Image("assets/money_icon.png"));
        moneyIcon.setFitWidth(150);
        moneyIcon.setPreserveRatio(true);

        moneyLabel = new Label("$" + engine.getPlayer().getMoney());
        moneyLabel.setStyle("-fx-font-family: 'Short Stack'; -fx-font-size: 20px; -fx-text-fill: #805b4c"); // Dark green or customize
        moneyLabel.setTranslateX(13);
        moneyLabel.setTranslateY(-1);
        StackPane moneyStack = new StackPane(moneyIcon, moneyLabel);

// Align the label slightly upward so it centers better on icon
        StackPane moneyWrapper = new StackPane(moneyStack);
        moneyWrapper.setPadding(new Insets(20));
        topBar.setLeft(moneyWrapper);

        updateView();

        HBox topRightBox = NavigationHelper.createTopRightReturnButton(stage, "Return to Menu");
        topBar.setRight(topRightBox);

// Bottom bar
        HBox bottomRightBox = NavigationHelper.createBottomRightNavButton(stage, "Kitchen");

        BorderPane overlayContainer = new BorderPane();
        overlayContainer.setTop(topBar);
        overlayContainer.setBottom(bottomRightBox);



// Add overlayContainer to StackPane
        getChildren().addAll(
                background,
                customerImageView,
                counter,
                dishList,
                customerHint,
                overlayContainer,
                tablet
        );
    }

    // Create updateView() method to update dynamic UI components
    public void updateView() {
        // Update money label
        moneyLabel.setText("$" + engine.getPlayer().getMoney());
        moneyLabel.setStyle("-fx-font-family: 'Short Stack'; -fx-font-size: 20px; -fx-text-fill: #805b4c");

        // Update customerHint text depending on current customer
        Customer currentCustomer = engine.getCurrentCustomer();
        if (currentCustomer != null) {
            customerHint.setText("Customer’s hint about favorite ingredient: " + currentCustomer.getFavoriteIngredient().getName());
        } else {
            customerHint.setText("No customers available");
        }

        // Update other UI elements like dishList if needed...
    }
}
