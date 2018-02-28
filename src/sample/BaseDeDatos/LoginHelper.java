package sample.BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHelper {

    public boolean Login(String nick, String password, Connection conexion){
        String s="SELECT nick, password FROM autores WHERE nick=? and password=?";
        try {
            PreparedStatement sentencia = conexion.prepareStatement(s);
            sentencia.setString(1,nick);
            sentencia.setString(2,password);
            ResultSet resultSet = sentencia.executeQuery();
            if(resultSet.first()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
