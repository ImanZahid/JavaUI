import javafx.embed.swing.SwingNode;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javax.swing.SwingUtilities;

public class Main extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        BorderPane pane = new FxDesign().createLayout();
        stage.setTitle("JavaFX and Swing Integration");
        Scene scene = new Scene(pane, 800, 600);
        String css = this.getClass().getResource("styles1.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}















//.menu-bar { /* The menu bar itself */ }
//.menu { /* The File menu item */ }
//.menu:showing { /* menu when it's being shown (activated) */ }
//.menu .label { /* Styles the text on a menu item */ }
//.menu:showing .label { /* Styles the text on a menu item when activated */ }