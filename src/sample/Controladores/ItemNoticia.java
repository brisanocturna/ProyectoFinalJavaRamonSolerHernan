package sample.Controladores;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ItemNoticia {

    @FXML Text label;
    public void initialize(){
        label.setText("hola estoy cargando mis cosas");
    }
}
