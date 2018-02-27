package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class Controller {

    public static Double Height;
    public static Double Width;

    @FXML VBox Vbox;

    public void initialize(){
        System.out.println(Width);
        Vbox.setLayoutX((Width-Vbox.getPrefWidth())/2);
        Vbox.setLayoutY((Height-Vbox.getPrefHeight())/2);
    }
}
