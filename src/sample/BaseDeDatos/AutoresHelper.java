package sample.BaseDeDatos;

import sample.Modelos.Autores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoresHelper {

    public Autores getAutorById(Connection conexion, Long id){
        Autores autor = new Autores();
        String s="SELECT * FROM autores where id=?";
        try {
            PreparedStatement sentencia = conexion.prepareStatement(s);
            sentencia.setLong(1,id);
            ResultSet resultSet = sentencia.executeQuery();
            if(resultSet.first()){
                autor= new Autores(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4),resultSet.getString(5),resultSet.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autor;
    }
}
