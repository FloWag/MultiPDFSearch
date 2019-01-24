package org.florian_wagner.multipdfsearch.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class StarterWindow extends Application {

    private static StarterWindow instance;

    // step2 items
    private Text searchLabel;
    private Button searchButton;
    private TextField searchField;

    /**
     * starts the inital settings window - executed by the JavaFX Engine
     * @param primaryStage
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {
        instance = this;
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

        hideStep2Items(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    /**
     * makes a few more options visible
     * @param pdfAmount the amount of pdfs, we found in provious steps
     */
    public void showStep2(int pdfAmount)
    {
        if(pdfAmount == 0)
        {
            searchLabel.setText("Ich konnte unter diesem Pfad leider keine PDF-Dateien finden. Bitte versuchen Sie es nochmal!");
            searchLabel.setVisible(true);
        }else
        {
            searchLabel.setText("Sehr gut! Ich habe "+pdfAmount+" PDF-Dateien gefunden! Nach welchem Begriff soll gesucht werden?");
            searchLabel.setVisible(true);
            searchButton.setVisible(true);
            searchField.setVisible(true);
        }
    }

    /**
     * hides step 2 options
     * @param scene
     */
    private void hideStep2Items(Scene scene)
    {
        searchLabel = (Text)scene.lookup("#searchlabel");
        searchField = (TextField)scene.lookup("#searchfield");
        searchButton = (Button)scene.lookup("#searchbutton");

        searchLabel.setVisible(false);
        searchField.setVisible(false);
        searchButton.setVisible(false);
    }

    /**
     * Singleton
     * @return
     */
    public static StarterWindow getInstance()
    {
        return instance;
    }
}
