package sample.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import sample.Modelos.Noticias;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ItemNoticia {

    @FXML TextField tituloItemNoticia;
    @FXML TextField fechaItemNoticia;
    @FXML TextField autorItemNoticia;
    @FXML ImageView imagenItemNoticia;
    public void initData(Noticias noticia){
        tituloItemNoticia.setText(noticia.getTitulo());
        autorItemNoticia.setText(noticia.getAutor().getNick());
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        fechaItemNoticia.setText(sdf.format(noticia.getFechaUpdate()));
        String sourcefile=("http://rmhdam2017.ddns.net/image/"+noticia.getImagen());
        Image imagen = new Image(sourcefile, 100,100,false,false);
        imagenItemNoticia.setImage(imagen);
    }
}
