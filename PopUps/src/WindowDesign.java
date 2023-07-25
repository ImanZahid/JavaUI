import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowDesign {

    public static Scene getMainScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        VBox bottomVBox = new VBox();
        bottomVBox.setAlignment(Pos.CENTER);
        
        Button openPopupBtn = new Button("OPEN POPUP");
        openPopupBtn.getStyleClass().add("button-neon");
        
        EventHandlers.handleOpenFirstPopupButtonClick(openPopupBtn);

        bottomVBox.getChildren().addAll(openPopupBtn);
        borderPane.setCenter(bottomVBox);

        Scene scene = new Scene(borderPane, 400, 400);
        scene.getStylesheets().add("/Assets/Styles.css");

        return scene;
    }
}
