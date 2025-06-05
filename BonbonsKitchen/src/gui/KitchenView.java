package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class KitchenView extends BaseView {
    private final ImageView backgroundImageView;
    private final ImageView counterImageView;

    private final Button cookingPotButton;
    private final Button cookbookButton;
    private final Button bulletinButton;

    private final HBox buttonBox;

    public KitchenView() {
        super();

        // Background Image
        backgroundImageView = new ImageView(new Image("assets/kitchen_bg.png"));
        backgroundImageView.setFitWidth(1440);
        backgroundImageView.setFitHeight(900);
        backgroundImageView.setPreserveRatio(false);
        getChildren().add(0, backgroundImageView);

        // Counter Image at bottom
        counterImageView = new ImageView(new Image("assets/kitchen_counter.png"));
        counterImageView.setFitWidth(1440);
        counterImageView.setFitHeight(180);
        StackPane.setAlignment(counterImageView, Pos.BOTTOM_CENTER);
        getChildren().add(counterImageView);

        // Buttons (cookingPot, cookbook, bulletin) in a row, above counter
        cookingPotButton = new Button();
        ImageView potIcon = new ImageView(new Image("assets/cookingpot.png"));
        potIcon.setFitWidth(70);
        potIcon.setFitHeight(70);
        cookingPotButton.setGraphic(potIcon);
        cookingPotButton.setStyle("-fx-background-color: transparent;");

        cookbookButton = new Button();
        ImageView bookIcon = new ImageView(new Image("assets/cookbook.png"));
        bookIcon.setFitWidth(70);
        bookIcon.setFitHeight(70);
        cookbookButton.setGraphic(bookIcon);
        cookbookButton.setStyle("-fx-background-color: transparent;");

        bulletinButton = new Button();
        ImageView bulletinIcon = new ImageView(new Image("assets/bulletin.png"));
        bulletinIcon.setFitWidth(70);
        bulletinIcon.setFitHeight(70);
        bulletinButton.setGraphic(bulletinIcon);
        bulletinButton.setStyle("-fx-background-color: transparent;");

        buttonBox = new HBox(40, cookingPotButton, cookbookButton, bulletinButton);
        buttonBox.setPadding(new Insets(0, 0, 110, 0));
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        getChildren().add(buttonBox);

        // Navigation button to restaurant (bottom right)
        ImageView restaurantIcon = new ImageView(new Image("file:assets/icons/restaurant.png"));
        restaurantIcon.setFitWidth(55);
        restaurantIcon.setFitHeight(55);
        navButton.setGraphic(restaurantIcon);
        setupNavigation(() -> {
            // Handled in gui.MainApp
        });
    }

    public Button getCookingPotButton() {
        return cookingPotButton;
    }

    public Button getCookbookButton() {
        return cookbookButton;
    }

    public Button getBulletinButton() {
        return bulletinButton;
    }

    @Override
    public void setupNavigation(Runnable navigateAction) {
        navButton.setOnAction(e -> navigateAction.run());
    }
}
