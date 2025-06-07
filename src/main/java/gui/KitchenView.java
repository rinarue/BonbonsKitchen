package gui;

import gui.PopupHelper;
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
        getChildren().add(0, backgroundImageView);

        // Counter Image at bottom
        counterImageView = new ImageView(new Image("assets/kitchen_counter.png"));
        counterImageView.setFitWidth(1440);
        counterImageView.setFitHeight(900);
        getChildren().add(counterImageView);

        // Buttons (cookingPot, cookbook, bulletin) in a row, above counter
        cookingPotButton = new Button();
        ImageView potIcon = new ImageView(new Image("assets/cookingpot.png"));
        potIcon.setFitWidth(459);
        potIcon.setFitHeight(293);
        cookingPotButton.setGraphic(potIcon);
        cookingPotButton.getStyleClass().add("button-nav");
        cookingPotButton.setTranslateX(735);
        cookingPotButton.setTranslateY(395);

        cookbookButton = new Button();
        ImageView bookIcon = new ImageView(new Image("assets/cookbook.png"));
        bookIcon.setFitWidth(313);
        bookIcon.setFitHeight(222);
        cookbookButton.setGraphic(bookIcon);
        cookbookButton.getStyleClass().add("button-nav");
        cookbookButton.setTranslateX(-270);
        cookbookButton.setTranslateY(475);

        bulletinButton = new Button();
        ImageView bulletinIcon = new ImageView(new Image("assets/bulletin.png"));
        bulletinIcon.setFitWidth(453);
        bulletinIcon.setFitHeight(292);
        bulletinButton.setGraphic(bulletinIcon);
        bulletinButton.getStyleClass().add("button-nav");
        bulletinButton.setTranslateX(-410);
        bulletinButton.setTranslateY(76);


        buttonBox = new HBox(cookingPotButton, cookbookButton, bulletinButton);
        getChildren().add(buttonBox);

        // Navigation button to restaurant (bottom right)
        ImageView restaurantIcon = new ImageView(new Image("assets/restaurant.png"));
        restaurantIcon.setFitWidth(76);
        restaurantIcon.setFitHeight(70);
        navButton.setGraphic(restaurantIcon);
        StackPane.setAlignment(navButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(navButton, new Insets(20));
        setupNavigation(() -> {
            // Handled in main.java.gui.MainApp
        });
        navButton.setTranslateY(-40);
        homeButton.toFront();
        navButton.toFront();
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
