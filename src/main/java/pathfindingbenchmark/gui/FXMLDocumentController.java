/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import pathfindingbenchmark.util.MapReader;

/**
 *
 * @author thalvari
 */
public class FXMLDocumentController implements Initializable {

    private Wrapper wrapper;
    @FXML
    private BorderPane root;
    @FXML
    private ComboBox cb;
    @FXML
    private ToggleGroup tg;
    @FXML
    private ImageView iv;
    @FXML
    private TextField tf1;
    @FXML
    private TextField tf2;
    @FXML
    private TextField tf3;
    @FXML
    private TextField tf4;
    @FXML
    private Label lb1;
    @FXML
    private Label lb2;
    @FXML
    private Label lb3;
    @FXML
    private Label lb4;
    @FXML
    private Label lb5;
    @FXML
    private Label lb6;
    @FXML
    private Label lb7;
    @FXML
    private Label lb8;
    @FXML
    private Label lb9;
    @FXML
    private Label lb10;

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
        iv.setImage(wrapper.getMapAsWritebleImage());
        root.getScene().getWindow().sizeToScene();
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
            lb10.setVisible(true);
            return;
        }

        lb10.setVisible(false);
        if (!wrapper.checkCoordinates(startX, startY, goalX, goalY)) {
            lb10.setVisible(true);
            return;
        }

        wrapper.setAlgo(((RadioButton) tg.getSelectedToggle()).getText());
        wrapper.runAlgo(startX, startY, goalX, goalY);
        showResults();
        iv.setImage(wrapper.getMapAsWritebleImage());
        root.getScene().getWindow().sizeToScene();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wrapper = new Wrapper();
        cb.getItems().addAll(MapReader.MAPS);
    }
}
