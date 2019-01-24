package org.florian_wagner.multipdfsearch.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class StarterWindow extends Application {


    public void start(Stage primaryStage) throws Exception {
        URL xml = getClass().getResource("/org/florian_wagner/multipdfsearch/resources/starterWindow.fxml");
        FXMLLoader loader = new FXMLLoader(xml);
        Parent root = loader.load();

        int width = 735;
        int height = 290;

        Scene scene = new Scene(root, width,height);
        primaryStage.setTitle("MultiPDFSearch");
        primaryStage.setScene(scene);

        //make stage in center of the screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);

        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
