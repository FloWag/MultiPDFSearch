package org.florian_wagner.multipdfsearch.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.florian_wagner.multipdfsearch.gui.SearchResultWindow;
import org.florian_wagner.multipdfsearch.gui.StarterWindow;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StarterWindowController implements Initializable {

    @FXML private Button checkbutton;
    @FXML private TextField pathfield;
    @FXML private Button searchbutton;
    @FXML private TextField searchfield;

    private ArrayList<File> pdfClipboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Executed by the JavaFX Engine
     */
    public void handleCheckButton()
    {
        String path = pathfield.getText();
        File folder = new File(path);
        if(!folder.isDirectory())
        {
            Stage stage = (Stage) checkbutton.getScene().getWindow();
            StarterWindow.getInstance().showStep2(0);
        }else
        {
            pdfClipboard = new ArrayList<>();
            for(File file : folder.listFiles())
            {
                if(file.getAbsolutePath().endsWith(".pdf")) pdfClipboard.add(file);
            }
            StarterWindow.getInstance().showStep2(pdfClipboard.size());
        }
    }

    /**
     * Executed by the JavaFX Engine
     */
    public void handleSearchButton()
    {
        Stage stage = (Stage) checkbutton.getScene().getWindow();
        String searchText = searchfield.getText();
        if(pdfClipboard != null)
        {
            try {
                new SearchResultWindow().start(stage,pdfClipboard,searchText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
