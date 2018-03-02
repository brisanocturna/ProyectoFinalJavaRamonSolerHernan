package sample.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.Modelos.Noticias;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ItemNoticia {
    Noticias noticia;
    Double width;
    Double height;
    Pane principal;
    AnchorPane rellenarcomentarios;

    @FXML TextField tituloItemNoticia;
    @FXML TextField fechaItemNoticia;
    @FXML TextField autorItemNoticia;
    @FXML ImageView imagenItemNoticia;
    public void initData(Noticias noticia, Double width, Double height, Pane principal, AnchorPane rellenarcomentarios){
        this.rellenarcomentarios=rellenarcomentarios;
        this.noticia=noticia;
        this.width=width;
        this.height=height;
        this.principal=principal;
        tituloItemNoticia.setText(noticia.getTitulo());
        autorItemNoticia.setText(noticia.getAutor().getNick());
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        fechaItemNoticia.setText(sdf.format(noticia.getFechaUpdate()));
        String sourcefile=("http://rmhdam2017.ddns.net/image/"+noticia.getImagen());
        Image imagen = new Image(sourcefile, 100,100,false,false);
        imagenItemNoticia.setImage(imagen);
    }

    public void onClickNoticia(){
        try {
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("../Vistas/mostrar_noticia.fxml"));
            VBox root= fxmlLoader.load();
            root.setPrefWidth(this.width*0.5);
            root.setPrefHeight(this.height);
            MostrarNoticia controller = fxmlLoader.<MostrarNoticia>getController();
            controller.mostrarNoticia(noticia, this.width, this.height, this.rellenarcomentarios);
            principal.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
