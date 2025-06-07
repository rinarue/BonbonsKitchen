package gui;

import core.GameEngine;
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
        logoIcon = new ImageView(new Image("assets/money_icon.png"));
        logoIcon.setFitWidth(200);
        logoIcon.setFitHeight(70);

        moneyLabel = new Label("$" + GameEngine.getPlayer().getMoney());
        moneyLabel.getStyleClass().add("label-money");
        moneyLabel.setTranslateX(30);

        StackPane moneyStack = new StackPane(logoIcon, moneyLabel);
        moneyStack.setPadding(new Insets(20));
        moneyStack.setMaxSize(200, 70);
        moneyStack.setPrefSize(200, 70);
        moneyStack.setTranslateY(50);

        StackPane.setAlignment(moneyLabel, Pos.CENTER);
        StackPane.setAlignment(logoIcon, Pos.CENTER);

        StackPane.setAlignment(moneyStack, Pos.TOP_LEFT);
        getChildren().add(moneyStack);

        // Home Button (top-right)
        homeButton = new Button();
        ImageView homeIcon = new ImageView(new Image("assets/home.png"));
        homeIcon.setFitWidth(76);
        homeIcon.setFitHeight(70);
        homeButton.setGraphic(homeIcon);
        homeButton.setTranslateY(50);
        homeButton.getStyleClass().add("button-nav");
        StackPane.setAlignment(homeButton, Pos.TOP_RIGHT);
        StackPane.setMargin(homeButton, new Insets(20));
        getChildren().add(homeButton);

        // Navigation Button (bottom-right)
        navButton = new Button();
        navButton.getStyleClass().add("button-nav");
        StackPane.setAlignment(navButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(navButton, new Insets(20));
        getChildren().add(navButton);
    }

    public void updateMoneyDisplay(int money) {
        moneyLabel.setText("$" + money);
    }

    public abstract void setupNavigation(Runnable navigateAction);
}

