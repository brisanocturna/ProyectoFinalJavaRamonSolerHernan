package sample.Controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import jfx.messagebox.MessageBox;
import org.json.JSONException;
import org.json.JSONObject;
import sample.Main;
import sample.Modelos.Comentarios;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class ItemComentario {
    Comentarios comentario;
    Stage stage;
    MostrarNoticia mostrarNoticia;

    @FXML
    TextField tituloItemComentario;
    @FXML
    TextField autorItemComentario;
    @FXML
    TextField fechaItemComentario;
    @FXML
    TextArea contenidoItemComentario;
    @FXML
    Button btnComentarioUpdate;
    @FXML
    Button btnComentarioEliminar;
    @FXML
    Separator separadorcomentarios;

    public void initData(Comentarios comentario, Double width, Double height, Stage stage, MostrarNoticia mostrarNoticia) {
        this.stage = stage;
        this.comentario = comentario;
        this.mostrarNoticia=mostrarNoticia;
        separadorcomentarios.setPrefWidth((width*0.25)-150);
        tituloItemComentario.setText(comentario.getTitulo());
        autorItemComentario.setText(comentario.getAutor().getNick());
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        fechaItemComentario.setText(sdf.format(comentario.getFechaUpdate()));
        contenidoItemComentario.setText(comentario.getContenido());
    }

    public void onClickUpdate() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Comentario");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titulo = new TextField();
        titulo.setText(comentario.getTitulo());
        TextArea contenido = new TextArea();
        contenido.setText(comentario.getContenido());
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
                jobj.put("id",comentario.getId());
                jobj.put("titulo", usernamePassword.getKey());
                jobj.put("contenido", usernamePassword.getValue());
                jobj.put("idAutor", Main.autores.getId());
                jobj.put("idNoticia", comentario.getIdNoticia());
                jobj.put("fechaCreacion", comentario.getFechaCreacion());
                jobj.put("fechaUpdate", "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String subirnoticia = jobj.toString();
            System.out.println(jobj.toString());
            byte[] postData = subirnoticia.getBytes();

            try {
                URL url = new URL("http://rmhdam2017.ddns.net/index.php/api/comentarios");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setConnectTimeout(2000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("charset", "UTF-8");
                connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
                connection.setUseCaches(false);

                PrintWriter out = new PrintWriter(connection.getOutputStream());
                out.print(subirnoticia);
                out.close();

                int responsecode = connection.getResponseCode();

                if (responsecode == 200) {
                    MessageBox.show(stage,
                            "Comentario actualizado correctamente",
                            "Comentario Succes",
                            MessageBox.ICON_WARNING | MessageBox.OK);


                } else {
                    MessageBox.show(stage,
                            "Fallo al actulizar comentario",
                            "comentario Failed",
                            MessageBox.ICON_WARNING | MessageBox.OK);

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mostrarNoticia.cargarComentarios();
        });

    }

    public void onClickEliminar(){
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("id",comentario.getId());
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
                        "Comentario eliminado correctamente",
                        "ComentarioSucces",
                        MessageBox.ICON_WARNING | MessageBox.OK );
            }else{
                MessageBox.show(stage,
                        "Fallo al eliminar Comentario",
                        "Comentario Failed",
                        MessageBox.ICON_WARNING | MessageBox.OK );
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mostrarNoticia.cargarComentarios();
    }
}
