package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.List;

public class TutorialView extends BaseView {

    private final List<Image> tutorialImages;
    private int currentIndex = 0;

    private final ImageView tutorialImageView;

    private final Button leftArrowButton;
    private final Button rightArrowButton;

    public TutorialView(List<Image> images) {
        super();

        this.tutorialImages = images;

        // Background (optional, or just white)

        // Image view to display current tutorial screenshot
        tutorialImageView = new ImageView();
        tutorialImageView.setFitWidth(1440);
        tutorialImageView.setFitHeight(900);
        tutorialImageView.setPreserveRatio(true);
        tutorialImageView.setSmooth(true);
        tutorialImageView.setMouseTransparent(true); // <-- this line fixes the issue
        updateImage();

        // Center the image view
        StackPane.setAlignment(tutorialImageView, Pos.CENTER);
        getChildren().add(tutorialImageView);

        // Navigation arrows container
        HBox navBox = new HBox(40);
        navBox.setAlignment(Pos.CENTER);
        navBox.setPadding(new Insets(20));
        StackPane.setAlignment(navBox, Pos.BOTTOM_CENTER);

        // Left arrow button
        leftArrowButton = new Button();
        ImageView leftArrowIcon = new ImageView(new Image("assets/arrow_left.png"));
        leftArrowIcon.setFitWidth(50);
        leftArrowIcon.setFitHeight(50);
        leftArrowButton.setGraphic(leftArrowIcon);
        leftArrowButton.getStyleClass().add("button-nav");
        leftArrowButton.setOnAction(e -> showPrevious());

        // Right arrow button
        rightArrowButton = new Button();
        ImageView rightArrowIcon = new ImageView(new Image("assets/arrow_right.png"));
        rightArrowIcon.setFitWidth(50);
        rightArrowIcon.setFitHeight(50);
        rightArrowButton.setGraphic(rightArrowIcon);
        rightArrowButton.getStyleClass().add("button-nav");
        rightArrowButton.setOnAction(e -> showNext());
        navBox.setTranslateY(-60);

        navBox.getChildren().addAll(leftArrowButton, rightArrowButton);
        navBox.setMaxHeight(100);

        getChildren().add(navBox);

        setupNavigation(() -> {
            // Handled in main.java.gui.MainApp
        });

        // Initially update arrows in case at ends
        updateArrowButtons();
    }

    private void updateImage() {
        tutorialImageView.setImage(tutorialImages.get(currentIndex));
    }

    private void showPrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            updateImage();
            updateArrowButtons();
        }
    }

    private void showNext() {
        if (currentIndex < tutorialImages.size() - 1) {
            currentIndex++;
            updateImage();
            updateArrowButtons();
        }
    }

    private void updateArrowButtons() {
        leftArrowButton.setDisable(currentIndex == 0);
        rightArrowButton.setDisable(currentIndex == tutorialImages.size() - 1);
    }

    @Override
    public void setupNavigation(Runnable navigateAction) {
        homeButton.setOnAction(e -> navigateAction.run());
    }
}
