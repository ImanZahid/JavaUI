import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.application.Platform;

public class FxDesign extends Application {

    private TreeView<String> treeView;
    private SwingNode swingNode;
    private SwingDesign swingDesign;
    private TextField nameField;
    private TextField surnameField;
    private ComboBox<String> genderComboBox;
    private Button addButton;
    private Animation animation;

    private MenuBar topMenu;
    private ToolBar topToolBar;
    private ToolBar bottomToolBar;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        swingDesign = new SwingDesign();

        BorderPane borderPane = createLayout();
        animation = new Animation(this, borderPane); // Assuming instantiation of Animation class is correct

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("FxSwingDesign");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public BorderPane createLayout() {
        BorderPane borderPane = new BorderPane();

        if (swingDesign == null) {
            swingDesign = new SwingDesign();
        }

        topMenu = createTopMenu();
        topToolBar = createTopToolBar();

        topToolBar.getItems().add(topMenu);
        borderPane.setTop(topToolBar);

        VBox rightBox = new VBox();
        treeView = createTreeView();
        rightBox.getChildren().add(treeView);
        borderPane.setRight(rightBox);

        swingNode = swingDesign.getTableNode();  // Using SwingNode from SwingDesign class

        borderPane.setCenter(swingNode);

        bottomToolBar = createBottomToolBar(borderPane);
        borderPane.setBottom(bottomToolBar);
        rightBox.getStyleClass().add("tree-view-container");
        return borderPane;
    }

    private ToolBar createTopToolBar() {
        Label nameLabel = new Label("Name:");
        Label surnameLabel = new Label("Surname:");

        nameField = new TextField();
        surnameField = new TextField();
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Female", "Male", "Other");
        genderComboBox.setValue("Female");

        addButton = new Button("Add");
        addButton.setOnAction(EventHandlers.AddBtnToolBarClicked(this, swingDesign));        
        addButton.getStyleClass().add("button");

        topToolBar = new ToolBar();
        topToolBar.getItems().addAll(nameLabel, nameField, surnameLabel, surnameField, genderComboBox, addButton);

        topToolBar.getStyleClass().add("top-hbox");
        
        return topToolBar;
    }


    private MenuBar createTopMenu() {
        topMenu = new MenuBar();

        Menu addMenu = new Menu("ADD");
        MenuItem addItem = new MenuItem("Add item");
        addItem.setOnAction(EventHandlers.AddBtnClicked(this));
        addMenu.getItems().add(addItem);
      
        Menu spacerMenu1 = new Menu("   ");
        spacerMenu1.setDisable(true);

        Menu deleteMenu = new Menu("DELETE");
        MenuItem deleteItem = new MenuItem("Delete item");
        deleteItem.setOnAction(EventHandlers.DeleteBtnClicked(this));
        deleteMenu.getItems().add(deleteItem);       
        
        Menu spacerMenu2 = new Menu("   ");
        spacerMenu2.setDisable(true);

        Menu renameMenu = new Menu("RENAME");
        MenuItem renameItem = new MenuItem("Rename item");
        renameItem.setOnAction(EventHandlers.RenameBtnClicked(this));
        renameMenu.getItems().add(renameItem);

       
        addMenu.getStyleClass().add("odd-menu");
        deleteMenu.getStyleClass().add("odd-menu");
        renameMenu.getStyleClass().add("odd-menu");
        
        topMenu.getStyleClass().add("padded-menu");

        topMenu.getMenus().addAll(addMenu, spacerMenu1, deleteMenu, spacerMenu2, renameMenu);
        return topMenu;
    }

    private TreeView<String> createTreeView() {
        TreeView<String> treeView = new TreeView<>();
        TreeItem<String> root = new TreeItem<>("");
        root.setExpanded(true);

        TreeItem<String> circle = new TreeItem<>("Circle");
        TreeItem<String> triangle = new TreeItem<>("Triangle");
        TreeItem<String> square = new TreeItem<>("Square");

        root.getChildren().addAll(circle, square, triangle);
        treeView.setShowRoot(false);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setRoot(root);
        return treeView;
    }

    private ToolBar createBottomToolBar(BorderPane borderPane) {
    	 animation = new Animation(this, borderPane); // Again, this needs a proper constructor or details.

        Button openButton = new Button("Open");
        openButton.setOnAction(EventHandlers.OpenBtnClicked(this, swingDesign, animation)); // Changed animationClass to animation
        //openButton.getStyleClass().add("button");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(EventHandlers.CloseBtnClicked(this, swingDesign, animation)); // Changed animationClass to animation
       //closeButton.getStyleClass().add("button");
     
        HBox buttonContainer = new HBox(25);
        buttonContainer.getChildren().addAll(openButton, closeButton);
        buttonContainer.setAlignment(Pos.CENTER);  

        bottomToolBar = new ToolBar(buttonContainer); 
        bottomToolBar.getStyleClass().add("bottom-toolbar");

        return bottomToolBar;
    }

    public TreeItem<String> getSelectedRoot() {
        return treeView.getSelectionModel().getSelectedItem();
    }

    private void createAndSetSwingContent(final SwingNode swingNode) {
        Platform.runLater(() -> {
            swingNode.setContent(swingDesign.getMainPanel());
            
            // Suggestion 3
            swingDesign.getMainPanel().revalidate();
            swingDesign.getMainPanel().repaint();
        });
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getSurnameField() {
        return surnameField;
    }

    public ComboBox<String> getGenderComboBox() {
        return genderComboBox;
    }

    public Button getAddButton() {
        return addButton;
    }

    public MenuBar getTopMenu() {
        return topMenu;
    }

    public ToolBar getTopToolBar() {
        return topToolBar;
    }

    public TreeView<String> getTreeView() {
        return treeView;
    }

    public ToolBar getBottomToolBar() {
        return bottomToolBar;
    }

    public Node getCenterNode() {
        return swingNode;
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