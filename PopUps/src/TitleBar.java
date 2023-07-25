import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TitleBar {

    // This method will return a BorderPane with HBox set to the top
    public static BorderPane getTitleBarContainer() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getTitleBar()); // Set the HBox to the top of the BorderPane
        return borderPane;
    }

        public static HBox getTitleBar() {
            HBox titleBar = new HBox();
            titleBar.setPadding(new Insets(5)); 
            titleBar.getStyleClass().add("title-bar");
            titleBar.setSpacing(5); // spacing between inner HBoxes

            // Back button with image
            Image backImage = new Image(TitleBar.class.getResource("/Assets/back-icon.png").toString());
            Button backButton = new Button("", new ImageView(backImage));
            backButton.setOnAction(e -> {
                EventHandlers.handleBackButtonClick();
            });
            HBox backContainer = new HBox(backButton);
            backContainer.setAlignment(Pos.CENTER_LEFT);

            // Close button with image
            Image closeImage = new Image(TitleBar.class.getResource("/Assets/close-icon.png").toString());
            Button closeButton = new Button("", new ImageView(closeImage));
            closeButton.setOnAction(e -> {
                EventHandlers.handleCloseButtonClick();
            });

            HBox closeContainer = new HBox(closeButton);
            closeContainer.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(closeContainer, Priority.ALWAYS); // this makes the closeContainer expand

            titleBar.getChildren().addAll(backContainer, closeContainer);

            return titleBar;
        }
    }
