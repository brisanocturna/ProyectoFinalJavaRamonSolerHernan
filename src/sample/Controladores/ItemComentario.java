package sample.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Modelos.Comentarios;

import java.text.SimpleDateFormat;

public class ItemComentario {

    @FXML TextField tituloItemComentario;
    @FXML TextField autorItemComentario;
    @FXML TextField fechaItemComentario;
    @FXML TextArea contenidoItemComentario;
    public void initData(Comentarios comentario, Double width, Double height){
        tituloItemComentario.setText(comentario.getTitulo());
        autorItemComentario.setText(comentario.getAutor().getNick());
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        fechaItemComentario.setText(sdf.format(comentario.getFechaUpdate()));
        contenidoItemComentario.setText(comentario.getContenido());
    }
}
