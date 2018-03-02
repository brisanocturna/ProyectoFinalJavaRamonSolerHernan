package sample.Controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import jfx.messagebox.MessageBox;
import sample.BaseDeDatos.LoginHelper;
import sample.Main;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Login {

    public double height;
    public double width;
    public double x;
    public double y;
    public Stage stage;
    public Connection conexion;
    public LoginHelper loginHelper;

    @FXML VBox Vbox;

    public void initialize(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://rmhdam2017.ddns.net:3306/rmhdam2017","rmhdam2017","Progresa2017$");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loginHelper= new LoginHelper();
    }

    public void initData(Stage stage, Double height, Double width, double y, double x){
        this.stage = stage;
        this.height = height;
        this.width = width;
        this.x=x;
        this.y=y;
        Vbox.setLayoutX((this.width-Vbox.getPrefWidth())/2);
        Vbox.setLayoutY((this.height-Vbox.getPrefHeight())/2);
    }

    @FXML TextField nick;
    @FXML TextField password;
    public void onClickAcceder(MouseEvent event){
        if(loginHelper.Login(nick.getText().toString(),password.getText().toString(),conexion)){
            try {
                Stage newstage = new Stage();
                FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("../Vistas/main_application.fxml"));
                AnchorPane root= fxmlLoader.load();
                newstage.setX(this.x);
                newstage.setY(this.y);
                newstage.setWidth(this.width);
                newstage.setHeight(this.height);
                newstage.setMaximized(true);
                newstage.setScene(new Scene(root, 300, 275));
                MainApplication controller = fxmlLoader.<MainApplication>getController();
                controller.initData(newstage, this.height,this.width,this.y,this.x);
                newstage.show();
                conexion.close();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            MessageBox.show(stage,
                    "El Nick o la Contraseña introducida no son validas por favor revise los datos",
                    "Login Failed",
                    MessageBox.ICON_WARNING | MessageBox.OK );
        }
    }
}
