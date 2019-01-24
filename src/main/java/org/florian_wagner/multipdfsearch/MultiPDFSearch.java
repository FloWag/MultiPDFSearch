package org.florian_wagner.multipdfsearch;

import javafx.application.Application;
import org.florian_wagner.multipdfsearch.gui.StarterWindow;

public class MultiPDFSearch {

    public static void main(String[] args)
    {
        new MultiPDFSearch().init();
    }

    public void init()
    {
        new Thread(new Runnable() {
            public void run() {
                Application.launch(StarterWindow.class);
            }
        }).start();
    }

}
