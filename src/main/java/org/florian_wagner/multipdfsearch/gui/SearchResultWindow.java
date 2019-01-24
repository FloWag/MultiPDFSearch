package org.florian_wagner.multipdfsearch.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultWindow {

    private Text statusLabel;
    private ListView listView;
    private ProgressBar progressBar;
    private ImageView loadingGif;

    /**
     * Replaces the current window with a searchresult window and starts a search.
     * @param primaryStage the primaryStage which has to be overwritten
     * @param pdfClipboard collection of PDFs to search through
     * @param searchText the string to search for
     * @throws Exception never mind
     */
    public void start(Stage primaryStage, ArrayList<File> pdfClipboard, String searchText) throws Exception {
        URL xml = getClass().getResource("/org/florian_wagner/multipdfsearch/resources/searchWindow.fxml");
        FXMLLoader loader = new FXMLLoader(xml);

        Parent root = loader.load();

        int width = 600;
        int height = 400;

        Scene scene = new Scene(root, width,height);

        primaryStage.setTitle("PDF Suche");
        primaryStage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - width) / 2);
        primaryStage.setY((screenBounds.getHeight() - height) / 2);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });

        statusLabel = (Text)scene.lookup("#statusLabel");
        listView = (ListView)scene.lookup("#listView");
        progressBar = (ProgressBar) scene.lookup("#progressBar");
        loadingGif = (ImageView) scene.lookup("#loadingGif");
        statusLabel.setText("Suche nach Begriff \"" + searchText + "\"");

        new Thread(new Runnable() {
            @Override
            public void run() {
                startSearchThread(pdfClipboard,searchText);
            }
        }).start();

    }

    /**
     * starts the search thread
     * @param pdfClipboard collection of PDFs to search through
     * @param searchText the string to search for
     */
    private void startSearchThread(ArrayList<File> pdfClipboard, String searchText)
    {
        for(int i = 0; i < pdfClipboard.size(); i++)
        {
            progressBar.setProgress(calculatePercentage(i,pdfClipboard.size()));
            File file = pdfClipboard.get(i);
            try {
                if(stringExistsInPDF(file,searchText))
                {
                    String[] splittedName = file.getAbsolutePath().split("\\\\");
                    String fileName = splittedName[splittedName.length-1];
                    listView.getItems().add(fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        progressBar.setProgress(1d);
        statusLabel.setText("Suche abgeschlossen!");
        loadingGif.setVisible(false);

    }

    /**
     * Checks if a PDF file contains a specific string
     * @param pdfFile
     * @param searchString
     * @return true if the pdf contains the string
     * @throws IOException
     */
    private boolean stringExistsInPDF(File pdfFile, String searchString) throws IOException {

        PDFParser parser = new PDFParser(new RandomAccessFile(pdfFile,"r"));
        parser.parse();
        boolean found = false;
        try(PDDocument pddoc = new PDDocument(parser.getDocument()))
        {
            String parsedText = new PDFTextStripper().getText(pddoc);
            if(parsedText.contains(searchString))found = true;
        }

        return found;
    }

    /**
     * calculates percentage of progressBar based on the current search progress
     * @param current
     * @param max
     * @return
     */
    private double calculatePercentage(int current, int max)
    {
        return (double)current / (double)max;
    }

}
