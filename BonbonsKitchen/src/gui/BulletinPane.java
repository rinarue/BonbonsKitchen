package gui;

import core.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class BulletinPane extends BorderPane {

    private final core.GameEngine gameEngine;
    private int currentIndex = 0;

    private Label nameLabel;
    private Label bioLabel;
    private ImageView favIngredientImage;
    private Label hintLabel;

    public BulletinPane(core.GameEngine engine) {
        this.gameEngine = engine;

        setPadding(new Insets(10));

        nameLabel = new Label();
        bioLabel = new Label();
        favIngredientImage = new ImageView();
        favIngredientImage.setFitHeight(100);
        favIngredientImage.setPreserveRatio(true);
        hintLabel = new Label();

        VBox centerBox = new VBox(10, nameLabel, bioLabel, favIngredientImage, hintLabel);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);

        Button leftButton = new Button("<");
        Button rightButton = new Button(">");

        leftButton.setOnAction(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateDisplay();
            }
        });

        rightButton.setOnAction(e -> {
            if (currentIndex < gameEngine.getCustomers().size() - 1) {
                currentIndex++;
                updateDisplay();
            }
        });

        HBox navBox = new HBox(10, leftButton, rightButton);
        navBox.setAlignment(Pos.CENTER);
        setBottom(navBox);

        updateDisplay();
    }

    private void updateDisplay() {
        List<Customer> customers = gameEngine.getCustomers();
        if (customers.isEmpty()) return;

        Customer c = customers.get(currentIndex);

        nameLabel.setText("Name: " + c.getName());
        bioLabel.setText("Bio: " + c.getBio());
        hintLabel.setText("Hint: Likes " + c.getFavoriteIngredient().getName());

        Image img = new Image("file:" + c.getFavoriteIngredient().getImagePath());
        favIngredientImage.setImage(img);
    }
}
