import java.util.Optional;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class EventHandlers {

    private static double initialWidth = -1;
    private static double initialHeight = -1;
    private Animation animationClass;
    private static boolean isFullScreen = false;
    private static boolean isOpenBtnClicked = false;
    private static boolean isFirstOpen = true;
    
    public EventHandlers(FxDesign fxDesign, BorderPane layout) {
        this.animationClass = new Animation(fxDesign, layout);
    }

    public static EventHandler<ActionEvent> AddBtnClicked(FxDesign design) {
        return event -> {
            TreeItem<String> RootSelected = design.getSelectedRoot();
            if (RootSelected != null) {
                String parent = RootSelected.getValue();
                TreeItem<String> child = new TreeItem<>(parent + "Child");
                RootSelected.getChildren().add(child);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.setContentText("Please select an option!");
                alert.showAndWait();
            }
        };
    }

    public static EventHandler<ActionEvent> DeleteBtnClicked(FxDesign design) {
        return event -> {
            TreeItem<String> rootSelected = design.getSelectedRoot();
            if (rootSelected != null && rootSelected.getParent() != null) {
                rootSelected.getParent().getChildren().remove(rootSelected);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("");
                alert.setContentText("Please select a valid item to delete!");
                alert.showAndWait();
            }
        };
    }

    public static EventHandler<ActionEvent> RenameBtnClicked(FxDesign design) {
        return event -> {
            TreeItem<String> RootSelected = design.getSelectedRoot();
            if (RootSelected != null) {
                TextInputDialog dialog = new TextInputDialog(RootSelected.getValue());
                dialog.setTitle("Rename Item");
                dialog.setHeaderText("Enter new name for the item");
                dialog.setContentText("New name:");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    RootSelected.setValue(result.get());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.setContentText("Please select an item to rename!");
                alert.showAndWait();
            }
        };
    }

    public static EventHandler<ActionEvent> AddBtnToolBarClicked(FxDesign fxDesign, SwingDesign swingDesign) {
        return event -> {
            String name = fxDesign.getNameField().getText().trim();
            String surname = fxDesign.getSurnameField().getText().trim();
            String gender = fxDesign.getGenderComboBox().getSelectionModel().getSelectedItem();

            if (name.isEmpty() || surname.isEmpty() || gender == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Please enter all information before adding!");
                alert.showAndWait();
            } else {
                JTable table = swingDesign.getTable();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{name, surname, gender});
            }
        };
    }
        public static EventHandler<ActionEvent> CloseBtnClicked(FxDesign fxDesign, SwingDesign swingDesign, Animation animationClass) {
            return event -> {
                // Reset the flag for the open button click.
                isOpenBtnClicked = false;

                // Animations for closing
                ParallelTransition topTransition = animationClass.animateSlide(
                        fxDesign.getTopToolBar(),
                        0,
                        -fxDesign.getTopToolBar().getLayoutBounds().getHeight(),
                        false
                );

                ParallelTransition treeTransition = animationClass.animateSlide(
                        fxDesign.getTreeView(),
                        fxDesign.getTreeView().getLayoutBounds().getWidth(),
                        0,
                        false
                );

                topTransition.play();
                treeTransition.play();
            };
        }

        public static EventHandler<ActionEvent> OpenBtnClicked(FxDesign fxDesign, SwingDesign swingDesign, Animation animationClass) {
            return event -> {
            	if (isFirstOpen) {
                    isFirstOpen = false; // Update the flag so the button works next time
                    return;
                }

                // Check if the open button was previously clicked. If so, do nothing.
                if (isOpenBtnClicked) {
                    return;
                }
                isOpenBtnClicked = true;  // Update the flag.

                // Initial offset values for animations
                double topMenuInitialOffset = -fxDesign.getTopToolBar().getLayoutBounds().getHeight();
                double treeViewInitialOffset = fxDesign.getTreeView().getLayoutBounds().getWidth();

                fxDesign.getTopToolBar().setTranslateY(topMenuInitialOffset);
                fxDesign.getTreeView().setTranslateX(treeViewInitialOffset);

                // Get the layout and set nodes accordingly
                BorderPane layout = animationClass.getLayout();
                if (layout == null) {
                    System.err.println("Error: Layout from animationClass is null.");
                    return;
                }

                layout.setTop(fxDesign.getTopToolBar());
                layout.setRight(fxDesign.getTreeView());
                layout.setBottom(fxDesign.getBottomToolBar());

                // Check and set the SwingNode
                SwingNode swingNode = swingDesign.getTableNode();
                if (swingNode == null) {
                    System.err.println("Error: SwingNode from SwingDesign is null.");
                    return;
                }
                layout.setCenter(swingNode);
                layout.requestLayout();

                // Animations for opening
                ParallelTransition topTransition = animationClass.animateSlide(
                        fxDesign.getTopToolBar(),
                        0,
                        0,
                        true
                );

                ParallelTransition treeTransition = animationClass.animateSlide(
                        fxDesign.getTreeView(),
                        0,
                        0,
                        true
                );

                topTransition.play();
                treeTransition.play();
            };
        }
    }
