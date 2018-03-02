package sample.BaseDeDatos;

import sample.Main;
import sample.Modelos.Autores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHelper {

    public boolean Login(String nick, String password, Connection conexion){
        String s="SELECT * FROM autores WHERE nick=? and password=?";
        try {
            PreparedStatement sentencia = conexion.prepareStatement(s);
            sentencia.setString(1,nick);
            sentencia.setString(2,password);
            ResultSet resultSet = sentencia.executeQuery();
            if(resultSet.first()){
                Main.autores= new Autores(resultSet.getLong(1), resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4),resultSet.getString(5),resultSet.getString(6));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
