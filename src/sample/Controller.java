package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;


public class Controller {
    static Double Height=0.0;

    @FXML private AnchorPane listaNoticias;
    @FXML private ScrollPane scrollNoticias;

    public void cambiarScroll(){
        scrollNoticias.setPrefHeight(Height);
    }

}
