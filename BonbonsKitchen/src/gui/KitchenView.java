package gui;

import core.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class KitchenView extends StackPane {

    private final core.GameEngine gameEngine;
    private final Stage primaryStage;
    private final MainMenu mainMenu;
    private final MainApp mainApp;

    private Label moneyLabel;
    private Label currentCustomerLabel;
    private ImageView cookingPotImage;
    private ImageView bulletinImage;
    private ImageView cookbookImage;

    public KitchenView(core.GameEngine engine, Stage stage, MainMenu mainMenu, MainApp mainApp) {
        this.gameEngine = engine;
        this.primaryStage = stage;
        this.mainMenu = mainMenu;
        this.mainApp = mainApp;

        // --- BACKGROUND ---
        ImageView background = new ImageView(new Image("assets/kitchen_bg.png"));
        background.setFitWidth(1440);
        background.setFitHeight(900);
        background.setPreserveRatio(false);

        // --- COUNTER IMAGE (layered over background) ---
        ImageView kitchencounter = new ImageView(new Image("assets/kitchen_counter.png"));
        kitchencounter.setPreserveRatio(true);
        kitchencounter.setFitWidth(1440);


        // Top bar with money label and return button
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

        HBox topRightBox = NavigationHelper.createTopRightReturnButton(primaryStage,"Return to Menu");
        topBar.setRight(topRightBox);
        topRightBox.setStyle("-fx-font-family: 'Short Stack'");

// Overlay alignment
        StackPane.setAlignment(topBar, Pos.TOP_CENTER);


        // --- Current customer label below money bar ---
        currentCustomerLabel = new Label();
        updateCurrentCustomerLabel();
        currentCustomerLabel.setStyle("-fx-font-family: 'Short Stack'; -fx-background-color: white; -fx-padding: 8; -fx-border-color: lightgray;");
        currentCustomerLabel.setWrapText(true);
        StackPane.setAlignment(currentCustomerLabel, Pos.TOP_LEFT);
        StackPane.setMargin(currentCustomerLabel, new Insets(60, 0, 0, 20));

        // --- Clickable image bar (cooking, bulletin, cookbook) ---
        cookingPotImage = new ImageView(new Image("assets/cookingpot.png"));
        cookingPotImage.setFitHeight(270);
        cookingPotImage.setPreserveRatio(true);
        cookingPotImage.setTranslateX(740);
        cookingPotImage.setTranslateY(-155);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.ORCHID);
        glow.setRadius(20);
        cookingPotImage.setOnMouseEntered(e -> cookingPotImage.setEffect(glow));
        cookingPotImage.setOnMouseExited(e -> cookingPotImage.setEffect(null));

        bulletinImage = new ImageView(new Image("assets/bulletin.png"));
        bulletinImage.setFitHeight(300);
        bulletinImage.setPreserveRatio(true);
        bulletinImage.setTranslateX(-60);
        bulletinImage.setTranslateY(-475);

        bulletinImage.setOnMouseEntered(e -> bulletinImage.setEffect(glow));
        bulletinImage.setOnMouseExited(e -> bulletinImage.setEffect(null));

        cookbookImage = new ImageView(new Image("assets/cookbook.png"));
        cookbookImage.setFitHeight(270);
        cookbookImage.setPreserveRatio(true);
        cookbookImage.setTranslateX(-765);
        cookbookImage.setTranslateY(-148);

        cookbookImage.setOnMouseEntered(e -> cookbookImage.setEffect(glow));
        cookbookImage.setOnMouseExited(e -> cookbookImage.setEffect(null));

        HBox imageBar = new HBox(cookingPotImage, bulletinImage, cookbookImage);


        imageBar.setAlignment(Pos.BOTTOM_CENTER);
        StackPane.setAlignment(imageBar, Pos.BOTTOM_CENTER);
        StackPane.setMargin(imageBar, new Insets(0, 0, 0, 0));

        // --- Bottom right: "Go to Restaurant" button ---
        HBox bottomRightBox = NavigationHelper.createBottomRightNavButton(primaryStage, "Restaurant");
        bottomRightBox.setStyle("-fx-font-family: 'Short Stack");

        BorderPane overlayContainer = new BorderPane();
        overlayContainer.setBottom(bottomRightBox);

// Top bar with money + return button
        overlayContainer.setTop(topBar);

        // --- Click actions ---
        cookingPotImage.setOnMouseClicked(e -> openCookingPotWindow());
        bulletinImage.setOnMouseClicked(e -> openBulletinWindow());
        cookbookImage.setOnMouseClicked(e -> openCookbookWindow());

        // Create bottom container holding imageBar (left) and bottomRightBox (right)
        HBox bottomContainer = new HBox();
        //bottomContainer.setPadding(new Insets(10));
        //bottomContainer.setSpacing(10);
        bottomContainer.setAlignment(Pos.CENTER_RIGHT); // or Pos.BOTTOM_CENTER if you want them centered

// Add imageBar on left and bottomRightBox on right side
        bottomContainer.getChildren().addAll(
                imageBar,
                bottomRightBox
        );

        // Put topBar and bottomContainer into overlayContainer
        overlayContainer.setTop(topBar);
        overlayContainer.setBottom(bottomContainer);

        // Finally add everything to StackPane
        getChildren().addAll(
                background,
                kitchencounter,
                currentCustomerLabel,
                overlayContainer
        );
    }

    public void updateCurrentCustomerLabel() {
        Customer c = gameEngine.getCurrentCustomer();
        if (c != null) {
            currentCustomerLabel.setText("Customer: " + c.getName() + " - " + c.getBio() +
                    "\nFav ingredient hint: " + c.getFavoriteIngredient().getName());
        } else {
            currentCustomerLabel.setText("No more customers today!");
        }
    }

    private void openCookingPotWindow() {
        Stage cookingPotStage = new Stage();
        cookingPotStage.setTitle("Cooking Pot");
        CookingPotPane cookingPotPane = new CookingPotPane(gameEngine, this);
        cookingPotStage.setScene(new Scene(cookingPotPane, 720, 600));
        cookingPotStage.setResizable(false);
        cookingPotStage.show();
    }

    private void openBulletinWindow() {
        Stage bulletinStage = new Stage();
        bulletinStage.setTitle("Customer Bulletin");
        BulletinPane bulletinPane = new BulletinPane(gameEngine);
        bulletinStage.setScene(new Scene(bulletinPane, 600, 500));
        bulletinStage.setResizable(false);
        bulletinStage.show();
    }

    private void openCookbookWindow() {
        Stage cookbookStage = new Stage();
        cookbookStage.setTitle("Cookbook");
        CookbookPane cookbookPane = new CookbookPane(gameEngine);
        cookbookStage.setScene(new Scene(cookbookPane, 600, 500));
        cookbookStage.setResizable(false);
        cookbookStage.show();
    }

    public void updateMoneyDisplay() {
        moneyLabel.setText("$" + gameEngine.getPlayer().getMoney());
        moneyLabel.setStyle("-fx-font-family: 'Short Stack'; -fx-font-size: 20px; -fx-text-fill: #805b4c");
    }
}
