import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class Animation {

    private FxDesign fxDesign;
    private BorderPane layout;

    public Animation(FxDesign fxDesign, BorderPane layout) {
        this.fxDesign = fxDesign;
        this.layout = layout;
    }

    private FadeTransition getFadeTransition(Node node, boolean fadeIn) {
        FadeTransition fade = new FadeTransition(Duration.millis(500), node);
        fade.setFromValue(fadeIn ? 0 : 1);
        fade.setToValue(fadeIn ? 1 : 0);
        fade.setOnFinished(e -> {
            if (!fadeIn) {
                node.setVisible(false);
            }
        });
        if (fadeIn) {
            node.setVisible(true);
        }
        return fade;
    }

    private TranslateTransition getSlideTransition(Node node, double targetX, double targetY) {
        TranslateTransition slide = new TranslateTransition(Duration.millis(500), node);
        slide.setToX(targetX);
        slide.setToY(targetY);
        return slide;
    }

    public Timeline animateHeight(Region region, double targetHeight) {
        KeyValue kv = new KeyValue(region.prefHeightProperty(), targetHeight);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        return new Timeline(kf);
    }

    public ParallelTransition animateSlide(Node node, double targetX, double targetY, boolean show) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null.");
        }

        ParallelTransition pt = new ParallelTransition();
        TranslateTransition transition = getSlideTransition(node, targetX, targetY);
        FadeTransition fade = getFadeTransition(node, show);

        transition.setOnFinished(e -> {
            if (!show) {
                if (node.equals(fxDesign.getTopMenu()) || node.equals(fxDesign.getTopToolBar())) {
                    layout.setTop(null);
                } else if (node.equals(fxDesign.getTreeView())) {
                    layout.setRight(null);
                }
            }
        });

        pt.getChildren().addAll(transition, fade);
        return pt;
    }

    public BorderPane getLayout() {
        return this.layout;
    }
}
