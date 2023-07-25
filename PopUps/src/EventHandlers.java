import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class EventHandlers {
    public static void handleBackButtonClick() {
        if (PopUpManager.popups.size() <= 1) { 
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Popup Error");
            errorAlert.setContentText("No parent popup found!");
            errorAlert.showAndWait();
        } else {
            // Close the current popup
            Popup currentPopup = PopUpManager.popups.pop();
            currentPopup.hide();
        }
    }

    public static void handleCloseButtonClick() {
        while (!PopUpManager.popups.isEmpty()) {
            Popup currentPopup = PopUpManager.popups.pop();
            currentPopup.hide();
        }
    }

    public static void handleOpenFirstPopupButtonClick(Button openPopupButton) {
        openPopupButton.setOnAction(event -> {
            // Close the last popup (if any) before opening a new one
            if (!PopUpManager.popups.isEmpty()) {
                Popup lastPopup = PopUpManager.popups.peek();
                lastPopup.hide();
                PopUpManager.popups.pop(); // Make sure to remove the closed popup from the stack
            }

            // Create and show the first popup irrespective of the stack's state
            Popup newPopup = PopUpWindow.showPopup(1);
            newPopup.show(openPopupButton.getScene().getWindow());

            // Push the new popup to the stack
            PopUpManager.popups.push(newPopup);
        });
    }

    
    public static void handleOpenNextPopupButtonClick(Button clickHereButton) {
        clickHereButton.setOnAction(event -> {
            int popupNumber = PopUpManager.popups.size() + 1;

            if (popupNumber <= 3) {
                Popup newPopup = PopUpWindow.showPopup(popupNumber);
                newPopup.show(clickHereButton.getScene().getWindow());
                PopUpManager.popups.push(newPopup);
            } else {
                Alert errorAlert = new Alert(AlertType.WARNING);
                errorAlert.setHeaderText("Popup Limit Reached");
                errorAlert.setContentText("No more popups can be created!");
                errorAlert.showAndWait();
                PopUpManager.popups.clear();
            }
        });
    }
}