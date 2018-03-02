package sample.Controladores;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.json.JSONException;
import org.json.JSONObject;
import sample.BaseDeDatos.ComentariosHelper;
import sample.Main;
import sample.Modelos.Comentarios;
import sample.Modelos.Noticias;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class MostrarNoticia {
    Noticias noticia;
    Double width;
    Double height;
    public Connection conexion;
    public AnchorPane rellenarcomentarios;
    public Image imagen;

    @FXML TextField tituloMostrarNoticia;
    @FXML TextArea contenidoMostrarNoticia;
    @FXML ImageView imgMostrarNoticia;
    @FXML Button btnEditarMostrarNoticia;
    @FXML Button btnEliminarMostrarNoticia;
    @FXML Button btnAceptarMostrarNoticia;
    @FXML Button btnCancelarMostrarNoticia;
    @FXML Button btnAgregarImagen;

    public void initialize(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://rmhdam2017.ddns.net:3306/rmhdam2017","rmhdam2017","Progresa2017$");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.btnEliminarMostrarNoticia.setVisible(false);
        this.btnEditarMostrarNoticia.setVisible(false);
        this.btnAceptarMostrarNoticia.setVisible(false);
        this.btnCancelarMostrarNoticia.setVisible(false);
        this.btnAgregarImagen.setVisible(false);

    }

    public void mostrarNoticia(Noticias noticia, Double width, Double height, AnchorPane rellenarcomentarios ){
        this.rellenarcomentarios=rellenarcomentarios;
        this.noticia=noticia;
        this.width=width;
        this.height=height;
        tituloMostrarNoticia.setText(noticia.getTitulo());
        contenidoMostrarNoticia.setPrefHeight(this.height*0.5);
        imgMostrarNoticia.setLayoutY(imgMostrarNoticia.getLayoutY()+10);
        imgMostrarNoticia.setLayoutX((((this.width)-200)/2)*0.5);
        contenidoMostrarNoticia.setText(noticia.getContenido());
        String sourcefile=("http://rmhdam2017.ddns.net/image/"+noticia.getImagen());
        imagen = new Image(sourcefile, 100,100,false,false);
        imgMostrarNoticia.setImage(imagen);
        if(Main.autores.getId()==noticia.getIdAutor()){
            btnEditarMostrarNoticia.setVisible(true);
            btnEliminarMostrarNoticia.setVisible(true);
        }
        ArrayList<Comentarios> listacomentarios;
        listacomentarios=noticia.getComentarios();
        try {

            ArrayList<Node> nodos = new ArrayList<>();
            for (int i = 0; i <listacomentarios.size() ; i++) {
                System.out.println(listacomentarios.size());
                FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("../Vistas/item_comentario.fxml"));
                AnchorPane root= fxmlLoader.load();
                ItemComentario controller = fxmlLoader.<ItemComentario>getController();
                controller.initData(listacomentarios.get(i), this.width, this.height);
                root.setLayoutY((i*(root.getPrefHeight()+10)));
                root.setId(""+i);
                root.setPrefWidth((this.width*0.25)-4);
                nodos.add(root);
            }
            if(nodos.size()>0){
                rellenarcomentarios.getChildren().addAll(nodos);
            }else{
                ObservableList<Node> eliminar = rellenarcomentarios.getChildren();
                rellenarcomentarios.getChildren().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarNoticias(Double width, Double height, AnchorPane rellenarcomentarios){
        this.width=width;
        this.height=height;
        this.rellenarcomentarios=rellenarcomentarios;
        contenidoMostrarNoticia.setPrefHeight(this.height*0.5);
        imgMostrarNoticia.setLayoutY(imgMostrarNoticia.getLayoutY()+10);
        imgMostrarNoticia.setLayoutX((((this.width)-200)/2)*0.5);
        btnEditarMostrarNoticia.setVisible(false);
        btnEliminarMostrarNoticia.setVisible(false);
        btnAceptarMostrarNoticia.setVisible(true);
        btnCancelarMostrarNoticia.setVisible(true);
        btnAgregarImagen.setVisible(true);
        tituloMostrarNoticia.setText("");
        tituloMostrarNoticia.setEditable(true);
        contenidoMostrarNoticia.setText("");
        tituloMostrarNoticia.setEditable(true);
        String sourcefile=("http://rmhdam2017.ddns.net/image/blanco.jpg");
        imagen = new Image(sourcefile,100,100,false,false);
        imgMostrarNoticia.setImage(imagen);
    }

    public void onClickAceptar(){
        JSONObject jobj = new JSONObject();
        try {
            jobj.append("titulo",tituloMostrarNoticia.getText());
            jobj.append("contenido",contenidoMostrarNoticia.getText());
            jobj.append("imagen","imagen.jpg");
            System.out.println(jobj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
