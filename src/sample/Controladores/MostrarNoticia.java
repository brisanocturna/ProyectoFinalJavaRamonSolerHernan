package sample.Controladores;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import jfx.messagebox.MessageBox;
import org.json.JSONException;
import org.json.JSONObject;
import sample.BaseDeDatos.ComentariosHelper;
import sample.Main;
import sample.Modelos.Comentarios;
import sample.Modelos.Noticias;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;


public class MostrarNoticia {
    Button agregarcomentarios;
    Noticias noticia;
    Double width;
    Double height;
    AnchorPane panelcomentarios;
    public Connection conexion;
    public AnchorPane rellenarcomentarios;
    public Image imagen;
    public Stage stage;
    public String nombreimagen;
    public boolean editmode=false;
    public MainApplication actualizar;

    @FXML TextField tituloMostrarNoticia;
    @FXML TextArea contenidoMostrarNoticia;
    @FXML ImageView imgMostrarNoticia;
    @FXML Button btnEditarMostrarNoticia;
    @FXML Button btnEliminarMostrarNoticia;
    @FXML Button btnAceptarMostrarNoticia;
    @FXML Button btnCancelarMostrarNoticia;
    @FXML Button btnAgregarImagen;
    @FXML HBox buttonbar;
    @FXML Separator separador;

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

    public void mostrarNoticia(Noticias noticia, Double width, Double height, AnchorPane rellenarcomentarios, MainApplication actualizar, AnchorPane panelcomentarios, Button agregarcomentarios){
        this.agregarcomentarios=agregarcomentarios;
        this.panelcomentarios=panelcomentarios;
        this.actualizar=actualizar;
        this.rellenarcomentarios=rellenarcomentarios;
        this.noticia=noticia;
        this.width=width;
        this.height=height;
        agregarcomentarios.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickAgregarComentarios);
        panelcomentarios.setVisible(true);
        panelcomentarios.setPrefWidth((this.width*0.25)-4);
        agregarcomentarios.setPrefWidth((this.width*0.25)-4);
        tituloMostrarNoticia.setText(noticia.getTitulo());
        contenidoMostrarNoticia.setPrefHeight(this.height*0.5);
        buttonbar.setPrefWidth(this.width*0.5);
        separador.setPrefWidth((this.width*0.5)-400);
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
        cargarComentarios();
    }

    public void cargarComentarios(){
        ComentariosHelper comentariosHelper = new ComentariosHelper();
        ArrayList<Comentarios> listacomentarios;
        listacomentarios=comentariosHelper.getAllComentariosFromNoticia(conexion,noticia.getId());
        try {
            rellenarcomentarios.getChildren().clear();
            ArrayList<Node> nodos = new ArrayList<>();
            for (int i = 0; i <listacomentarios.size() ; i++) {
                System.out.println(listacomentarios.size());
                FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("../Vistas/item_comentario.fxml"));
                AnchorPane root= fxmlLoader.load();
                ItemComentario controller = fxmlLoader.<ItemComentario>getController();
                controller.initData(listacomentarios.get(i), this.width, this.height, this.stage,this);
                root.setLayoutY((i*(root.getPrefHeight()+20))+40);
                root.setId(""+i);
                root.setPrefWidth((this.width*0.25)-4);
                nodos.add(root);
            }
            if(nodos.size()>0){
                rellenarcomentarios.getChildren().addAll(nodos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarNoticias(Double width, Double height, Stage stage, MainApplication actualizar){
        this.actualizar=actualizar;
        this.stage=stage;
        this.width=width;
        this.height=height;
        this.rellenarcomentarios=rellenarcomentarios;
        contenidoMostrarNoticia.setPrefHeight(this.height*0.5);
        imgMostrarNoticia.setLayoutY(imgMostrarNoticia.getLayoutY()+10);
        imgMostrarNoticia.setLayoutX((((this.width)-200)/2)*0.5);
        separador.setPrefWidth((this.width*0.5)-400);
        btnEditarMostrarNoticia.setVisible(false);
        btnEliminarMostrarNoticia.setVisible(false);
        btnAceptarMostrarNoticia.setVisible(true);
        btnCancelarMostrarNoticia.setVisible(true);
        btnAgregarImagen.setVisible(true);
        tituloMostrarNoticia.setText("");
        tituloMostrarNoticia.setEditable(true);
        contenidoMostrarNoticia.setText("");
        contenidoMostrarNoticia.setEditable(true);
        String sourcefile=("http://rmhdam2017.ddns.net/image/blanco.jpg");
        imagen = new Image(sourcefile,100,100,false,false);
        imgMostrarNoticia.setImage(imagen);
    }

    public void onClickAceptar(){
        JSONObject jobj = new JSONObject();
        try {
            if(editmode){
                jobj.put("id",this.noticia.getId());
            }
            jobj.put("titulo",tituloMostrarNoticia.getText());
            jobj.put("contenido",contenidoMostrarNoticia.getText());
            jobj.put("imagen",nombreimagen);
            jobj.put("idAutor",Main.autores.getId());
            jobj.put("fechaCreacion","");
            jobj.put("fechaUpdate","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String subirnoticia=jobj.toString();
        System.out.println(jobj.toString());
        byte[] postData = subirnoticia.getBytes();

        try {
            URL url = new URL("http://rmhdam2017.ddns.net/index.php/api/noticias");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(2000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            if(editmode){
                connection.setRequestMethod("PUT");
            }else{
                connection.setRequestMethod("POST");
            }
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("charset", "UTF-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
            connection.setUseCaches(false);

            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(subirnoticia);
            out.close();

            int responsecode = connection.getResponseCode();

            if(responsecode==200){
                if(editmode){
                    MessageBox.show(stage,
                            "Noticia actualizada correctamente",
                            "Noticia Succes",
                            MessageBox.ICON_WARNING | MessageBox.OK );

                }else{
                    MessageBox.show(stage,
                            "Noticia insertada correctamente",
                            "Noticia Succes",
                            MessageBox.ICON_WARNING | MessageBox.OK );
                }
            }else{
                if(editmode){
                    MessageBox.show(stage,
                            "Fallo al actulizar noticia",
                            "Noticia Failed",
                            MessageBox.ICON_WARNING | MessageBox.OK );

                }else{
                    MessageBox.show(stage,
                            "Fallo al insertar noticia",
                            "Noticia Failed",
                            MessageBox.ICON_WARNING | MessageBox.OK );
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        actualizar.cargarNoticias();
    }

    public void onClickAgregarImagen(){
        boolean imagen = false;
        do {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open File");
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files",
                            "*.bmp", "*.png", "*.jpg", "*.gif"));
            File file = chooser.showOpenDialog(new Stage());
            if(file != null) {
                String imagepath = null;
                try {
                    imagepath = file.toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Image image = new Image(imagepath);
                String base64String = encodeImageToString(SwingFXUtils.fromFXImage(image, null), "jpg");
                JSONObject jobj = new JSONObject();
                try {
                    nombreimagen=System.currentTimeMillis()+".jpg";
                    jobj.put("encodedimage",base64String);
                    jobj.put("name",nombreimagen);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String subirnoticia=jobj.toString();

                byte[] postData = subirnoticia.getBytes();

                try {
                    URL url = new URL("http://rmhdam2017.ddns.net/index.php/api/image");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setConnectTimeout(2000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setInstanceFollowRedirects(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type","application/json");
                    connection.setRequestProperty("charset", "UTF-8");
                    connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
                    connection.setUseCaches(false);

                    PrintWriter out = new PrintWriter(connection.getOutputStream());
                    out.print(subirnoticia);
                    out.close();

                    int responsecode = connection.getResponseCode();

                    if(responsecode==200){
                        MessageBox.show(stage,
                                "Imagen insertada correctamente",
                                "Imagen Succes",
                                MessageBox.ICON_WARNING | MessageBox.OK );
                        imagen=true;
                    }else{
                        MessageBox.show(stage,
                                "fallo al insertar imagen",
                                "Imagen Failed",
                                MessageBox.ICON_WARNING | MessageBox.OK );
                        imagen=false;
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(""+base64String);
                imgMostrarNoticia.setImage(image);
            }
            else
            {
                MessageBox.show(stage,
                        "porfavor seleccione una imgen",
                        "Select Image",
                        MessageBox.ICON_WARNING | MessageBox.OK );
            }
        }while (!imagen);
    }

    private String encodeImageToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = Base64.getEncoder().encodeToString(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public void onClickCancelar(){
        this.tituloMostrarNoticia.setText("");
        this.contenidoMostrarNoticia.setText("");
        String sourcefile=("http://rmhdam2017.ddns.net/image/blanco.jpg");
        imagen = new Image(sourcefile,100,100,false,false);
        imgMostrarNoticia.setImage(imagen);
    }

    public void onClickUpdate(){
        this.btnEditarMostrarNoticia.setVisible(false);
        this.btnEliminarMostrarNoticia.setVisible(false);
        this.tituloMostrarNoticia.setEditable(true);
        this.contenidoMostrarNoticia.setEditable(true);
        this.btnAceptarMostrarNoticia.setVisible(true);
        this.btnCancelarMostrarNoticia.setVisible(true);
        this.btnAgregarImagen.setVisible(true);
        editmode=true;
        this.nombreimagen=noticia.getImagen();
    }

    public void onClickEliminar(){
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("id",noticia.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String subirnoticia=jobj.toString();
        System.out.println(jobj.toString());
        byte[] postData = subirnoticia.getBytes();

        try {
            URL url = new URL("http://rmhdam2017.ddns.net/index.php/api/noticias");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(2000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("charset", "UTF-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
            connection.setUseCaches(false);

            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(subirnoticia);
            out.close();

            int responsecode = connection.getResponseCode();

            if(responsecode==200){
                    MessageBox.show(stage,
                            "Noticia eliminada correctamente",
                            "Noticia Succes",
                            MessageBox.ICON_WARNING | MessageBox.OK );
            }else{
                    MessageBox.show(stage,
                            "Fallo al eliminar noticia",
                            "Noticia Failed",
                            MessageBox.ICON_WARNING | MessageBox.OK );
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        actualizar.cargarNoticias();
    }

    EventHandler<MouseEvent> onClickAgregarComentarios = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Comentario");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField titulo = new TextField();
            titulo.setPromptText("Titulo");
            TextArea contenido = new TextArea();
            contenido.setPromptText("Comentario");
            grid.add(titulo, 0, 0);
            grid.add(contenido, 0, 1);
            dialog.getDialogPane().setContent(grid);
            Platform.runLater(() -> titulo.requestFocus());
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    return new Pair<>(titulo.getText(), contenido.getText());
                }
                return null;
            });
            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(usernamePassword -> {
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("titulo",usernamePassword.getKey());
                    jobj.put("contenido",usernamePassword.getValue());
                    jobj.put("idAutor",Main.autores.getId());
                    jobj.put("idNoticia",noticia.getId());
                    jobj.put("fechaCreacion","");
                    jobj.put("fechaUpdate","");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String subirnoticia=jobj.toString();
                System.out.println(jobj.toString());
                byte[] postData = subirnoticia.getBytes();

                try {
                    URL url = new URL("http://rmhdam2017.ddns.net/index.php/api/comentarios");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setConnectTimeout(2000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setInstanceFollowRedirects(false);
                    if(editmode){
                        connection.setRequestMethod("PUT");
                    }else{
                        connection.setRequestMethod("POST");
                    }
                    connection.setRequestProperty("Content-Type","application/json");
                    connection.setRequestProperty("charset", "UTF-8");
                    connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
                    connection.setUseCaches(false);

                    PrintWriter out = new PrintWriter(connection.getOutputStream());
                    out.print(subirnoticia);
                    out.close();

                    int responsecode = connection.getResponseCode();

                    if(responsecode==200){
                        if(editmode){
                            MessageBox.show(stage,
                                    "Comentario actualizado correctamente",
                                    "Comentario Succes",
                                    MessageBox.ICON_WARNING | MessageBox.OK );

                        }else{
                            MessageBox.show(stage,
                                    "Comentario insertado correctamente",
                                    "Comentario Succes",
                                    MessageBox.ICON_WARNING | MessageBox.OK );
                        }
                    }else{
                        if(editmode){
                            MessageBox.show(stage,
                                    "Fallo al actulizar comentario",
                                    "comentario Failed",
                                    MessageBox.ICON_WARNING | MessageBox.OK );

                        }else{
                            MessageBox.show(stage,
                                    "Fallo al insertar comentario",
                                    "comentario Failed",
                                    MessageBox.ICON_WARNING | MessageBox.OK );
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cargarComentarios();
            });
        }
    };
}
