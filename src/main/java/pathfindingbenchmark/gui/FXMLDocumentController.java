/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import pathfindingbenchmark.algorithms.AStar;
import pathfindingbenchmark.algorithms.AStarAbstract;
import pathfindingbenchmark.algorithms.Dijkstra;
import pathfindingbenchmark.algorithms.JPS;
import pathfindingbenchmark.grid.Grid;

/**
 * FXML Controller class
 *
 * @author thalvari
 */
public class FXMLDocumentController implements Initializable {

    @FXML private BorderPane root;
    @FXML private ComboBox cb;
    @FXML private RadioButton rb1;
    @FXML private RadioButton rb2;
    @FXML private TilePane tp;
    @FXML private TextField tf1;
    @FXML private TextField tf2;
    @FXML private TextField tf3;
    @FXML private TextField tf4;
    @FXML private Label lb1;
    @FXML private Label lb2;
    @FXML private Label lb3;
    @FXML private Label lb4;
    @FXML private Label lb5;
    @FXML private Label lb6;
    @FXML private Label lb7;
    @FXML private Label lb8;
    @FXML private Label lb9;
    private Grid grid;
    private AStarAbstract algo;
    private static long cpuTimeSum;
    private static long usedMemorySum;
    private static final int SAMPLE_SIZE = 25;
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private static final ThreadMXBean BEAN = ManagementFactory
            .getThreadMXBean();

    @FXML
    private void cbHandler(Event event) {
        grid = new Grid((String) cb.getValue());
        showMap(grid.getMarkedMap(null));
    }

    @FXML
    private void buttonHandler(Event event) {
        int startX;
        int startY;
        int goalX;
        int goalY;
        try {
            startX = Integer.parseInt(tf1.getText());
            startY = Integer.parseInt(tf2.getText());
            goalX = Integer.parseInt(tf3.getText());
            goalY = Integer.parseInt(tf4.getText());
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

        if (rb1.isSelected()) {
            algo = new Dijkstra(grid);
        } else if (rb2.isSelected()) {
            algo = new AStar(grid);
        } else {
            algo = new JPS(grid);
        }

        runAlgo(startX, startY, goalX, goalY);
        showResults();
        showMap(algo.getMarkedMap());
    }

    private void runAlgo(int startX, int startY, int goalX, int goalY) {
        cpuTimeSum = 0;
        usedMemorySum = 0;
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            RUNTIME.gc();
            long startUsedMemory = RUNTIME.totalMemory() - RUNTIME.freeMemory();
            long startCpuTime = BEAN.getCurrentThreadCpuTime();
            algo.run(startX, startY, goalX, goalY);
            cpuTimeSum += BEAN.getCurrentThreadCpuTime() - startCpuTime;
            usedMemorySum += RUNTIME.totalMemory() - RUNTIME.freeMemory()
                    - startUsedMemory;
        }
    }

    private String divideAndRound(long numer, long denom) {
        if (denom == 0) {
            return "0";
        }

        return new BigDecimal(numer)
                .divide(BigDecimal.valueOf(denom), 3, RoundingMode.HALF_UP)
                .toString();
    }

    private void showResults() {
        lb3.setText(algo.getRoundedDist(6));
        lb4.setText(divideAndRound(algo.getSuccListTotalSize(),
                algo.getHeapDelMinOperCount() - 1));

        lb5.setText("" + algo.getHeapInsertOperCount());
        lb6.setText("" + algo.getHeapDelMinOperCount());
        lb7.setText("" + algo.getHeapDecKeyOperCount());
        lb8.setText(divideAndRound(cpuTimeSum, SAMPLE_SIZE * 1000000L) + " ms");
        lb9.setText(divideAndRound(usedMemorySum, SAMPLE_SIZE * 1024L * 1024L)
                + " MB");
    }

    private void showMap(char[][] map) {
        tp.getChildren().clear();
        tp.setMaxSize(grid.getWidth() + 20, grid.getHeight() + 20);
        tp.setMinSize(grid.getWidth() + 20, grid.getHeight() + 20);
        tp.setPrefColumns(grid.getWidth());
        List<Pane> panes = getMapAsPanes(map);
        tp.getChildren().addAll(panes);
        lb1.setText(grid.getWidth() + " x " + grid.getHeight());
        lb2.setText("" + grid.getPassableNodeCount());
        root.getScene().getWindow().sizeToScene();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
