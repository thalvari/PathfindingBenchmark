/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pathfindingbenchmark.algorithms.AStar;
import pathfindingbenchmark.algorithms.AStarAbstract;
import pathfindingbenchmark.algorithms.Dijkstra;
import pathfindingbenchmark.algorithms.JPS;
import pathfindingbenchmark.grid.Grid;

/**
 *
 * @author thalvari
 */
public class Window {

    private static int INIT_TILEPANE_ROWS = 532;
    private static int INIT_TILEPANE_COLUMNS = 532;
    private static final String[] MAPS = {
        "AR0011SR", "lak505d", "orz100d"
    };

    private final Stage stage;
    private BorderPane root;
    private TilePane tilePane;
    private ComboBox comboBox;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private TextField startXTextField;
    private TextField startYTextField;
    private TextField goalXTextField;
    private TextField goalYTextField;
    private Label mapSizeLabel;
    private Label nodeCountLabel;
    private Grid grid;
    private AStarAbstract algo;

    public Window(Stage stage) {
        this.stage = stage;
        init();
    }

    private void init() {
        root = new BorderPane();
        initTopBar();
        initLeftBar();
        initRightBar();
        initBottomBar();

        tilePane = new TilePane();
        tilePane.setMinSize(INIT_TILEPANE_COLUMNS, INIT_TILEPANE_ROWS);
        tilePane.setPadding(new Insets(10, 10, 10, 10));
        root.setCenter(tilePane);
    }

    private void initTopBar() {
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10, 10, 10, 10));
        topBar.setSpacing(8);
        Label mapLabel = new Label("Map:");
        comboBox = new ComboBox();
        comboBox.getItems().addAll(MAPS);
        comboBox.setOnAction(this::comboBoxHandler);
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton1 = new RadioButton("Dijkstra");
        radioButton1.setSelected(true);
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2 = new RadioButton("A*");
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3 = new RadioButton("JPS");
        radioButton3.setToggleGroup(toggleGroup);
        Button findPathButton = new Button("Find path");
        findPathButton.setOnAction(this::findPathButtonHandler);
        topBar.getChildren().addAll(mapLabel, comboBox, radioButton1,
                radioButton2, radioButton3, findPathButton);

        root.setTop(topBar);
    }

    private void initLeftBar() {
        VBox leftBar = new VBox();
        leftBar.setAlignment(Pos.TOP_CENTER);
        leftBar.setPadding(new Insets(10, 10, 10, 10));
        leftBar.setSpacing(8);
        Label startXLabel = new Label("startX:");
        startXTextField = new TextField();
        startXTextField.setPrefColumnCount(2);
        Label startYLabel = new Label("startY:");
        startYTextField = new TextField();
        startYTextField.setPrefColumnCount(2);
        Label goalXLabel = new Label("goalX:");
        goalXTextField = new TextField();
        goalXTextField.setPrefColumnCount(2);
        Label goalYLabel = new Label("goalY:");
        goalYTextField = new TextField();
        goalYTextField.setPrefColumnCount(2);
        leftBar.getChildren().addAll(startXLabel, startXTextField, startYLabel,
                startYTextField, goalXLabel, goalXTextField, goalYLabel,
                goalYTextField);

        root.setLeft(leftBar);
    }

    private void initRightBar() {
    }

    private void initBottomBar() {
        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(10, 10, 10, 10));
        bottomBar.setSpacing(8);
        Label mapSizeTitleLabel = new Label("Map size:");
        mapSizeLabel = new Label();
        Label nodeCountTitleLabel = new Label("Passable nodes:");
        nodeCountLabel = new Label();
        bottomBar.getChildren().addAll(mapSizeTitleLabel, mapSizeLabel,
                nodeCountTitleLabel, nodeCountLabel);

        root.setBottom(bottomBar);
    }

    private void comboBoxHandler(Event event) {
        grid = new Grid((String) comboBox.getValue());
        showMap(grid.getMarkedMap(null));
    }

    private void findPathButtonHandler(Event event) {
        int startX;
        int startY;
        int goalX;
        int goalY;
        try {
            startX = Integer.parseInt(startXTextField.getText());
            startY = Integer.parseInt(startYTextField.getText());
            goalX = Integer.parseInt(goalXTextField.getText());
            goalY = Integer.parseInt(goalYTextField.getText());
        } catch (NumberFormatException e) {
            return;
        }

        if (grid == null
                || !grid.isInBounds(startX, startY)
                || !grid.getNode(startX, startY).isPassable()
                || !grid.isInBounds(goalX, goalY)
                || !grid.getNode(goalX, goalY).isPassable()) {

            return;
        }

        if (radioButton1.isSelected()) {
            algo = new Dijkstra(grid);
        } else if (radioButton2.isSelected()) {
            algo = new AStar(grid);
        } else {
            algo = new JPS(grid);
        }

        algo.run(startX, startY, goalX, goalY);
        showMap(algo.getMarkedMap());
    }

    private void showMap(char[][] map) {
        tilePane.getChildren().clear();
        tilePane.setMaxSize(grid.getWidth() + 20, grid.getHeight() + 20);
        tilePane.setMinSize(grid.getWidth() + 20, grid.getHeight() + 20);
        tilePane.setPrefColumns(grid.getWidth());
        List<Pane> panes = getMapAsPanes(map);
        tilePane.getChildren().addAll(panes);
        mapSizeLabel.setText(grid.getWidth() + " x " + grid.getHeight());
        nodeCountLabel.setText("" + grid.getPassableNodeCount());
        stage.sizeToScene();
    }

    private List<Pane> getMapAsPanes(char[][] map) {
        List<Pane> panes = new ArrayList<>();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Pane pane = new Pane();
                pane.setMinSize(1, 1);
                char c = map[y][x];
                if (c == '.' || c == 'G') {
                    pane.getStyleClass().add("traversable");
                } else if (c == '@' || c == 'O') {
                    pane.getStyleClass().add("obstacle");
                } else if (c == 'T') {
                    pane.getStyleClass().add("trees");
                } else if (c == 'S') {
                    pane.getStyleClass().add("swamp");
                } else if (c == 'W') {
                    pane.getStyleClass().add("water");
                } else if (c == 'o') {
                    pane.getStyleClass().add("closed");
                } else {
                    pane.getStyleClass().add("path");
                }

                panes.add(pane);
            }
        }

        return panes;
    }

    public void show() {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Pathfinding benchmark");
        stage.show();
    }
}
