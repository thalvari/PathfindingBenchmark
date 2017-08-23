/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingbenchmark.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author thalvari
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader()
                    .getResource("FXMLDocument.fxml"));
        } catch (Exception e) {
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pathfinding benchmark");
        stage.show();
    }

    @Override
    public void stop() {
    }
}
