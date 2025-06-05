package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TutorialView extends BorderPane {

    private final List<Image> tutorialImages = new ArrayList<>();
    private int currentIndex = 0;

    public TutorialView(Stage stage, MainMenu mainMenu) {
        // Load tutorial images from assets/tutorial folder (example)
        tutorialImages.add(new Image("file:assets/tutorial1.png"));
        tutorialImages.add(new Image("file:assets/tutorial2.png"));
        tutorialImages.add(new Image("file:assets/tutorial3.png"));

        ImageView imageView = new ImageView(tutorialImages.get(currentIndex));
        imageView.setFitWidth(1296); // 1440 * 0.9
        imageView.setPreserveRatio(true);

        setCenter(imageView);

        Button backBtn = new Button("Back");
        Button nextBtn = new Button("Next");

        backBtn.setDisable(true);

        backBtn.setOnAction(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                imageView.setImage(tutorialImages.get(currentIndex));
                nextBtn.setDisable(false);
            }
            if (currentIndex == 0) backBtn.setDisable(true);
        });

        nextBtn.setOnAction(e -> {
            if (currentIndex < tutorialImages.size() - 1) {
                currentIndex++;
                imageView.setImage(tutorialImages.get(currentIndex));
                backBtn.setDisable(false);
            }
            if (currentIndex == tutorialImages.size() - 1) nextBtn.setDisable(true);
        });

        //needed a return to main menu button
        // Return to menu button (top-right)
        BorderPane topBar = new BorderPane();

        Label placeholder = new Label(" "); // same space as moneyLabel left in RestaurantView
        topBar.setLeft(placeholder);

        HBox topRightBox = NavigationHelper.createTopRightReturnButton(stage, "Return to Menu");
        topBar.setRight(topRightBox);

        topBar.setPrefHeight(50);

        setTop(topBar);

        HBox buttons = new HBox(10, backBtn, nextBtn);
        buttons.setAlignment(Pos.CENTER);
        setBottom(buttons);
        buttons.setPadding(new Insets(10, 0, 20, 0));
    }
}
