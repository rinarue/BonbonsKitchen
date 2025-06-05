package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public abstract class BaseView extends StackPane {
    protected final ImageView logoIcon;
    protected final Label moneyLabel;
    protected final Button homeButton;
    protected final Button navButton;

    public BaseView() {
        setPrefSize(1440, 900);

        // Money Display (top-left)
        logoIcon = new ImageView(new Image("assets/logo_icon.png"));
        logoIcon.setFitWidth(50);
        logoIcon.setFitHeight(50);

        moneyLabel = new Label("Money: $0");
        moneyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox moneyBox = new HBox(8, logoIcon, moneyLabel);
        moneyBox.setAlignment(Pos.CENTER_LEFT);
        moneyBox.setPadding(new Insets(10));
        moneyBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 12;");
        StackPane.setAlignment(moneyBox, Pos.TOP_LEFT);
        getChildren().add(moneyBox);

        // Home Button (top-right)
        homeButton = new Button();
        ImageView homeIcon = new ImageView(new Image("assets/icons/home.png"));
        homeIcon.setFitWidth(35);
        homeIcon.setFitHeight(35);
        homeButton.setGraphic(homeIcon);
        homeButton.setStyle("-fx-background-color: transparent;");
        StackPane.setAlignment(homeButton, Pos.TOP_RIGHT);
        StackPane.setMargin(homeButton, new Insets(10));
        getChildren().add(homeButton);

        // Navigation Button (bottom-right)
        navButton = new Button();
        navButton.setStyle("-fx-background-color: transparent;");
        StackPane.setAlignment(navButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(navButton, new Insets(10));
        getChildren().add(navButton);
    }

    public void updateMoneyDisplay(int money) {
        moneyLabel.setText("Money: $" + money);
    }

    public abstract void setupNavigation(Runnable navigateAction);
}

