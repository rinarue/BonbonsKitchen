package gui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class PopupHelper {
    public static void showPopup(Pane content, String title) {
        Stage popupStage = new Stage();

        // ✅ Create only ONE Scene
        Scene popupScene = new Scene(content);
        popupScene.getStylesheets().add(Objects.requireNonNull(
                PopupHelper.class.getResource("/assets/style.css")
        ).toExternalForm());

        popupStage.setTitle(title);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(popupScene); // ✅ Use the same scene here
        popupStage.setResizable(false);
        popupStage.show();
    }
}
