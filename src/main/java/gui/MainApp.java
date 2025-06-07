package gui;

import core.GameEngine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainApp extends Application {
    private Stage primaryStage;
    private Scene mainScene;
    private StackPane root;
    private MediaPlayer backgroundMusic;
    private MainMenuView mainMenuView;
    private RestaurantView restaurantView;
    private KitchenView kitchenView;
    private TutorialView tutorialView;

    @Override
    public void start(Stage stage) {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Bold.ttf"), 12);
        playBackgroundMusic();
        primaryStage = stage;
        root = new StackPane();

        mainMenuView = new MainMenuView();
        restaurantView = new RestaurantView();
        kitchenView = new gui.KitchenView();

        // Load tutorial images (make sure these exist in your assets folder)
        List<Image> tutorialImages = List.of(
                new Image("file:/assets/tutorial1.png"),
                new Image("file:/assets/tutorial2.png"),
                new Image("file:/assets/tutorial3.png")
                // add as many as you have
        );

        tutorialView = new TutorialView(tutorialImages);

        root.getChildren().add(mainMenuView);

        mainScene = new Scene(root, 1440, 900);
        mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/assets/style.css")).toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Bonbon's Kitchen");
        primaryStage.setResizable(false);
        primaryStage.show();

        setupNavigation();

        setupHomeButtons();

        setupClickableButtons();

        // Add navigation for tutorial
        setupTutorialNavigation();

    }

    public void refreshMoneyDisplay() {
        int money = (int) GameEngine.getPlayer().getMoney();
        restaurantView.updateMoneyDisplay(money);
        kitchenView.updateMoneyDisplay(money);
    }

    private void setupNavigation() {
        // Main menu start button -> RestaurantView
        mainMenuView.getStartButton().setOnAction(e -> switchToRestaurant());

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
            MeadowMartPane store = new MeadowMartPane(this);
            PopupHelper.showPopup(store, "Meadow Mart");
        });


        // Kitchen clickable buttons:
        kitchenView.getCookingPotButton().setOnAction(e -> {
            CookingPotPane potPane = new CookingPotPane(); // use existing pane
            PopupHelper.showPopup(potPane, "Cooking Pot");
        });

        kitchenView.getCookbookButton().setOnAction(e -> {
            CookbookPane cookbookPane = new CookbookPane(); // make sure this exists
            PopupHelper.showPopup(cookbookPane, "Cookbook");
        });

        kitchenView.getBulletinButton().setOnAction(e -> {
            BulletinPane bulletinPane = new BulletinPane(); // make sure this exists
            PopupHelper.showPopup(bulletinPane, "Bulletin Board");
        });
    }

    private void setupTutorialNavigation() {
        // Example: add a "Tutorial" button in your mainMenuView to open tutorial
        // You have to add this button to MainMenuView first
        // Assuming you add getTutorialButton() in MainMenuView

        mainMenuView.getTutorialButton().setOnAction(e -> switchToTutorial());

        // Home button from tutorial returns to main menu
        tutorialView.setupNavigation(this::switchToMainMenu);
    }

    private void switchToTutorial() {
        root.getChildren().clear();
        root.getChildren().add(tutorialView);
    }

    private void switchToMainMenu() {
        root.getChildren().clear();
        root.getChildren().add(mainMenuView);
    }

    private void switchToRestaurant() {
        root.getChildren().clear();
        root.getChildren().add(restaurantView);
        // Spawn first customer only if none exists
        if (restaurantView.getActiveCustomer() == null) {
            restaurantView.spawnNextCustomer();
        }
    }

    private void switchToKitchen() {
        root.getChildren().clear();
        root.getChildren().add(kitchenView);
    }

    public MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    private void playBackgroundMusic() {
        String musicPath = Objects.requireNonNull(getClass().getResource("/assets/audio/background_music.mp3")).toExternalForm();
        Media media = new Media(musicPath);
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // loop forever
        backgroundMusic.setVolume(0.5); // adjust as needed
        backgroundMusic.play();
    }

    @Override
    public void stop() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
