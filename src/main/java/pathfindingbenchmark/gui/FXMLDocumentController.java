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
import pathfindingbenchmark.wrapper.Wrapper;

/**
 *
 * @author thalvari
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ToggleGroup algoToggleGroup;
    @FXML
    private Label avgSuccListSizeLabel;
    @FXML
    private Label closedNodeLabel;
    @FXML
    private Label cpuTimeLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField goalXTextField;
    @FXML
    private TextField goalYTextField;
    @FXML
    private Label heapOperLabel;
    @FXML
    private ComboBox mapComboBox;
    @FXML
    private ImageView mapImageView;
    @FXML
    private Label mapSizeLabel;
    @FXML
    private Label maxHeapSizeLabel;
    @FXML
    private Label nodeLabel;
    @FXML
    private Label pathLenLabel;
    @FXML
    private BorderPane root;
    @FXML
    private TextField startXTextField;
    @FXML
    private TextField startYTextField;
    @FXML
    private Label usedMemoryLabel;
    private final Wrapper wrapper;

    public FXMLDocumentController() {
        wrapper = new Wrapper();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (MapReader.MAP_NAMES != null) {
            for (int i = 0; i < MapReader.MAP_NAMES.size(); i++) {
                mapComboBox.getItems().add(MapReader.MAP_NAMES.get(i));
            }
        } else {
            errorLabel.setText("No maps");
        }
    }

    @FXML
    private void handleFindButton(Event event) {
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
            errorLabel.setText("Out of bounds");
            return;
        }

        if (!wrapper.checkStartGoal(startX, startY, goalX, goalY)) {
            errorLabel.setText("Out of bounds");
            return;
        }

        errorLabel.setText("");
        wrapper.setAlgo(((RadioButton) algoToggleGroup.getSelectedToggle())
                .getText());

        wrapper.runAlgo(startX, startY, goalX, goalY);
        showResults();
        root.getScene().getWindow().sizeToScene();
    }

    @FXML
    private void handleMapComboBox(Event event) {
        wrapper.setGrid((String) mapComboBox.getValue());
        showNewMap();
        root.getScene().getWindow().sizeToScene();
    }

    private void showNewMap() {
        avgSuccListSizeLabel.setText("");
        closedNodeLabel.setText("");
        cpuTimeLabel.setText("");
        errorLabel.setText("");
        goalXTextField.clear();
        goalYTextField.clear();
        heapOperLabel.setText("");
        mapImageView.setImage(wrapper.getMapAsWritableImage());
        mapSizeLabel.setText(wrapper.getGrid().getWidth() + " x "
                + wrapper.getGrid().getHeight());

        maxHeapSizeLabel.setText("");
        nodeLabel.setText("" + wrapper.getGrid().getPassableNodeCount());
        pathLenLabel.setText("");
        startXTextField.clear();
        startYTextField.clear();
        usedMemoryLabel.setText("");
    }

    private void showResults() {
        avgSuccListSizeLabel.setText(wrapper.getAvgSuccListSize());
        closedNodeLabel.setText("" + wrapper.getClosedNodePercentage());
        cpuTimeLabel.setText(wrapper.getCpuTime());
        heapOperLabel.setText("" + wrapper.getAlgo().getHeapOperCount());
        mapImageView.setImage(wrapper.getMapAsWritableImage());
        maxHeapSizeLabel.setText("" + wrapper.getAlgo().getMaxHeapSize());
        pathLenLabel.setText(wrapper.getPathLen());
        usedMemoryLabel.setText(wrapper.getUsedMemory());
    }
}
