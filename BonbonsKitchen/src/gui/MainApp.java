package gui;

import core.GameEngine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {

    private GameEngine gameEngine;
    private MainMenu mainMenu;
    private RestaurantView restaurantView;
    private KitchenView kitchenView;

    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/assets/ShortStack-Regular.ttf"), 16);
        gameEngine = new GameEngine();

        mainMenu = new MainMenu(gameEngine, stage, this);
        NavigationHelper.mainApp = this;
        // ✅ Initialize views before calling update methods
        restaurantView = new RestaurantView(gameEngine, stage, mainMenu, this);
        kitchenView = new KitchenView(gameEngine, stage, mainMenu, this);

        // ✅ Now it's safe to update them
        restaurantView.updateView();
        kitchenView.updateMoneyDisplay();
        kitchenView.updateCurrentCustomerLabel();

        Scene scene = new Scene(mainMenu, 1440, 900);

        stage.setTitle("Bonbon's Kitchen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public RestaurantView getRestaurantView() {
        return restaurantView;
    }

    public KitchenView getKitchenView() {
        return kitchenView;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
