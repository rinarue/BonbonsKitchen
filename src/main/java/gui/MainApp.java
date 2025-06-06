package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;
    private Scene mainScene;
    private StackPane root;

    private main.java.gui.MainMenuView mainMenuView;
    private RestaurantView restaurantView;
    private KitchenView kitchenView;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        root = new StackPane();

        mainMenuView = new main.java.gui.MainMenuView();
        restaurantView = new RestaurantView();
        kitchenView = new gui.KitchenView();

        root.getChildren().add(mainMenuView);

        mainScene = new Scene(root, 1440, 900);
        mainScene.getStylesheets().add(getClass().getResource("assets/style.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Bonbon's Kitchen");
        primaryStage.setResizable(false);
        primaryStage.show();

        setupNavigation();

        setupHomeButtons();

        setupClickableButtons();
    }

    private void setupNavigation() {
        // Main menu start button -> RestaurantView
        mainMenuView.getStartButton().setOnAction(e -> switchToRestaurant());

        mainMenuView.getExitButton().setOnAction(e -> primaryStage.close());

        // Restaurant -> Kitchen
        restaurantView.setupNavigation(this::switchToKitchen);

        // Kitchen -> Restaurant
        kitchenView.setupNavigation(this::switchToRestaurant);
    }

    private void setupHomeButtons() {
        // All home buttons go to main menu
        restaurantView.homeButton.setOnAction(e -> switchToMainMenu());
        kitchenView.homeButton.setOnAction(e -> switchToMainMenu());
    }

    private void setupClickableButtons() {
        // Tablet opens MeadowMart pane (placeholder action)
        restaurantView.getTabletButton().setOnAction(e -> {
            System.out.println("Open MeadowMart Store");
            // TODO: show MeadowMart pane
        });

        // Kitchen clickable buttons:
        kitchenView.getCookingPotButton().setOnAction(e -> {
            System.out.println("Open Cooking Pot Pane");
            // TODO: open cooking pot UI
        });

        kitchenView.getCookbookButton().setOnAction(e -> {
            System.out.println("Open Cookbook Pane");
            // TODO: open cookbook UI
        });

        kitchenView.getBulletinButton().setOnAction(e -> {
            System.out.println("Open Bulletin Pane");
            // TODO: open bulletin UI
        });
    }

    private void switchToMainMenu() {
        root.getChildren().clear();
        root.getChildren().add(mainMenuView);
    }

    private void switchToRestaurant() {
        root.getChildren().clear();
        root.getChildren().add(restaurantView);
    }

    private void switchToKitchen() {
        root.getChildren().clear();
        root.getChildren().add(kitchenView);
    }

    public static void main(String[] args) {
        launch();
    }
}
