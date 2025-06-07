package gui;

import core.Customer;
import core.GameEngine;
import core.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BulletinPane extends VBox {

    private final GameEngine gameEngine;

    public BulletinPane() {
        this.gameEngine = GameEngine.getInstance();

        setPrefSize(720, 450);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #fff8dc; -fx-border-color: #805b4c; -fx-border-radius: 15; -fx-background-radius: 15;");

        //Label header = new Label("Customer Bulletin");
        //header.setFont(Font.font(24));
        //header.setTextFill(Color.WHITE);
        //header.setAlignment(Pos.CENTER_LEFT);
        //header.setPadding(new Insets(0, 0, 10, 0));

        HBox contentRow = new HBox(20);
        contentRow.setPadding(new Insets(20));
        contentRow.setAlignment(Pos.TOP_CENTER);

        // LEFT BOX: Customer Info
        VBox customerBox = new VBox(15);
        customerBox.setPadding(new Insets(15));
        customerBox.setPrefWidth(460);
        customerBox.setStyle("-fx-background-color: #fefefd; -fx-border-color: #805b4c; -fx-border-radius: 15; -fx-background-radius: 15;");

        for (Customer customer : gameEngine.getCustomers()) {
            HBox customerRow = new HBox(10);
            customerRow.setAlignment(Pos.CENTER_LEFT);

            // Customer Image
            ImageView customerImg = new ImageView(new Image(customer.getImagePath()));
            customerImg.setFitWidth(110);
            customerImg.setFitHeight(100);

            VBox infoBox = new VBox(5);
            Label name = new Label(customer.getName());
            name.getStyleClass().add("label-regular");

            Label bio = new Label(customer.getBio());
            bio.setWrapText(true);
            bio.setMaxWidth(260);
            bio.getStyleClass().add("label-small");

            // Ingredient Image
            ImageView ingredientImg = new ImageView(new Image(customer.getFavoriteIngredient().getImagePath()));
            ingredientImg.setFitWidth(40);
            ingredientImg.setFitHeight(40);

            infoBox.getChildren().addAll(name, bio);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            customerRow.getChildren().addAll(customerImg, infoBox, spacer, ingredientImg);
            customerBox.getChildren().add(customerRow);
        }

        // RIGHT BOX: Money Earned
        VBox moneyBox = new VBox(10);
        moneyBox.setAlignment(Pos.TOP_CENTER);
        moneyBox.setPadding(new Insets(15));
        moneyBox.setPrefWidth(180);
        moneyBox.setStyle("-fx-background-color: #ffe9f3; -fx-border-color: #805b4c; -fx-border-radius: 15; -fx-background-radius: 15;");

        Label moneyHeader = new Label("Profit");
        moneyHeader.getStyleClass().add("label-regular");

        Label moneyAmount = new Label("$" + Player.getTotalMoneyEarned());
        moneyAmount.getStyleClass().add("label-regular");

        moneyBox.getChildren().addAll(moneyHeader, moneyAmount);

        contentRow.getChildren().addAll(customerBox, moneyBox);

        getChildren().addAll(contentRow);
    }
}
