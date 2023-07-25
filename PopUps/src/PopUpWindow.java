import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;

public class PopUpWindow {

    public static Popup showPopup(int popupNumber) {
        Popup popup = new Popup();
        popup.setAutoHide(true);

        BorderPane popupContent = PopUpDesign.getPopupContent(popupNumber);
        popup.getContent().add(popupContent);

        return popup;
    }
}
