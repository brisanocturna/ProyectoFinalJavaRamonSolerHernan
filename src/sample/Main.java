package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.Controladores.Login;
import sample.Modelos.Autores;

public class Main extends Application {

    public static Autores autores;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setMaximized(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas/login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        Login login = loader.<Login>getController();
        login.initData(primaryStage,bounds.getHeight(),bounds.getWidth(),bounds.getMinY(),bounds.getMinX());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
