/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import pathfindingbenchmark.util.MapReader;

/**
 *
 * @author thalvari
 */
public class FXMLDocumentController implements Initializable {

    @FXML private BorderPane root;
    @FXML private ComboBox cb;
    @FXML private ToggleGroup tg;
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
    private Wrapper wrapper;

    @FXML
    private void cbHandler(Event event) {
        wrapper.setGrid((String) cb.getValue());
        lb1.setText(wrapper.getWidth() + " x " + wrapper.getHeight());
        lb2.setText("" + wrapper.getGrid().getPassableNodeCount());
        lb3.setText("");
        lb4.setText("");
        lb5.setText("");
        lb6.setText("");
        lb7.setText("");
        lb8.setText("");
        lb9.setText("");
        tf1.clear();
        tf2.clear();
        tf3.clear();
        tf4.clear();
        showMap(wrapper.getStyles(0, 0, 0, 0), calcCoef());
    }

    private int calcCoef() {
        int coef = 1;
        if (wrapper.getHeight() <= 128 && wrapper.getWidth() <= 128) {
            coef = 3;
        } else if (wrapper.getHeight() <= 256 && wrapper.getWidth() <= 256) {
            coef = 2;
        }

        return coef;
    }

    @FXML
    private void btHandler(Event event) {
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

        if (!wrapper.checkCoordinates(startX, startY, goalX, goalY)) {
            return;
        }

        wrapper.setAlgo(((RadioButton) tg.getSelectedToggle()).getText());
        wrapper.runAlgo(startX, startY, goalX, goalY);
        showResults();
        showPath(wrapper.getStyles(startX, startY, goalX, goalY));
    }

    private void showResults() {
        lb3.setText(wrapper.getDist());
        lb4.setText(wrapper.getAvgSuccListSize());
        lb5.setText("" + wrapper.getAlgo().getHeapInsertOperCount());
        lb6.setText("" + wrapper.getAlgo().getHeapDelMinOperCount());
        lb7.setText("" + wrapper.getAlgo().getHeapDecKeyOperCount());
        lb8.setText(wrapper.getCpuTime());
        lb9.setText(wrapper.getUsedMemory());
    }

    private void showMap(List<String> styles, int coef) {
        tp.getChildren().clear();
        tp.setMaxSize(coef * wrapper.getWidth() + 20,
                coef * wrapper.getHeight() + 20);

        tp.setMinSize(coef * wrapper.getWidth() + 20,
                coef * wrapper.getHeight() + 20);

        tp.setPrefColumns(coef * wrapper.getWidth());
        List<Node> panes = new ArrayList<>();
        styles.forEach((style) -> {
            Pane pane = new Pane();
            pane.setMinSize(coef, coef);
            pane.getStyleClass().add(style);
            panes.add(pane);
        });

        tp.getChildren().addAll(panes);
        root.getScene().getWindow().sizeToScene();
    }

    private void showPath(List<String> styles) {
        List<Node> nodes = tp.getChildren();
        for (int i = 0; i < nodes.size(); i++) {
            if (!nodes.get(i).getStyleClass().contains(styles.get(i))) {
                nodes.get(i).getStyleClass().clear();
                nodes.get(i).getStyleClass().add(styles.get(i));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wrapper = new Wrapper();
        cb.getItems().addAll(MapReader.MAPS);
    }
}
