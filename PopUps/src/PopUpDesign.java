import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PopUpDesign {

    public static BorderPane getPopupContent(int popupNumber) {
        // Main container is the BorderPane returned from the TitleBar
        BorderPane mainContainer = TitleBar.getTitleBarContainer();

        // VBox for the main content of the popup
        VBox contentBox = new VBox(10); // Spacing of 10 between elements

        String popupMessage = "  This is the " + getOrdinal(popupNumber) + " popup.";
        Text popupText = new Text(popupMessage);
        
        Button clickHereButton = new Button("Click Here");
        clickHereButton.getStyleClass().add("click-here");
        EventHandlers.handleOpenNextPopupButtonClick(clickHereButton);

        contentBox.getChildren().addAll(popupText, clickHereButton);
        contentBox.setAlignment(javafx.geometry.Pos.CENTER);
        mainContainer.setCenter(contentBox);
        contentBox.getStyleClass().add("content-box");

        return mainContainer;
    }

    private static String getOrdinal(int value) {
        switch (value) {
            case 1:
                return "first";
            case 2:
                return "second";
            case 3:
                return "third";
            default:
                return "";
        }
    }
}
