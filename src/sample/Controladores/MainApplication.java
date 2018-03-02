package sample.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.BaseDeDatos.NoticiasHelper;
import sample.Modelos.Noticias;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainApplication {
    public double height;
    public double width;
    public double x;
    public double y;
    public Stage stage;
    public Connection conexion;
    public ArrayList<Noticias> listanoticias;
    public NoticiasHelper noticiasHelper;

    @FXML Pane principal;
    @FXML ScrollPane scrollnoticias;
    @FXML ScrollPane scrollcomentarios;
    @FXML AnchorPane rellenarnoticias;
    @FXML AnchorPane rellenarcomentarios;
    @FXML Button btnAgregarNoticia;
    @FXML Button btnAgregarComentario;

    public void initialize(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://rmhdam2017.ddns.net:3306/rmhdam2017","rmhdam2017","Progresa2017$");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initData(Stage stage, Double height, Double width, double y, double x){
        this.stage = stage;
        this.height = height;
        this.width = width;
        this.x=x;
        this.y=y;
        resizeElements();
        noticiasHelper= new NoticiasHelper();
        listanoticias=new ArrayList<>();
        listanoticias= noticiasHelper.getAllNoticias(conexion);
        rellenarnoticias.setPrefWidth(this.width*0.25);
        try {
            ArrayList<Node> nodos = new ArrayList<>();
            for (int i = 0; i <listanoticias.size() ; i++) {
                FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("../Vistas/item_noticia.fxml"));
                AnchorPane root= fxmlLoader.load();
                ItemNoticia controller = fxmlLoader.<ItemNoticia>getController();
                controller.initData(listanoticias.get(i), this.width, this.height, principal, rellenarcomentarios);
                root.setLayoutY((i*(root.getPrefHeight()+10))+40);
                root.setId(""+i);
                root.setPrefWidth((this.width*0.25)-4);
                nodos.add(root);
            }
            rellenarnoticias.getChildren().setAll(nodos);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void resizeElements(){
        scrollnoticias.setPrefHeight(this.height);
        scrollnoticias.setPrefWidth((this.width*0.25));
        scrollnoticias.setLayoutX(0);
        scrollnoticias.setLayoutY(0);
        scrollcomentarios.setPrefHeight(this.height);
        scrollcomentarios.setPrefWidth((this.width*0.25));
        scrollcomentarios.setLayoutX((this.width*0.75));
        scrollcomentarios.setLayoutY(0);
        principal.setPrefHeight(this.height);
        principal.setPrefWidth(this.width*0.5);
        principal.setLayoutX(scrollnoticias.getPrefWidth());
        principal.setLayoutY(0);
        btnAgregarNoticia.setPrefWidth((this.width*0.25)-2);
        btnAgregarComentario.setPrefWidth((this.width*0.25)-2);
    }

    public void onClickAgregarNoticia(){
        try {
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("../Vistas/mostrar_noticia.fxml"));
            VBox root= null;
            root = fxmlLoader.load();
            root.setPrefWidth(this.width*0.5);
            root.setPrefHeight(this.height);
            MostrarNoticia controller = fxmlLoader.<MostrarNoticia>getController();
            controller.agregarNoticias(this.width, this.height, this.rellenarcomentarios);
            principal.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
