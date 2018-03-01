package sample.BaseDeDatos;

import sample.Modelos.Comentarios;
import sample.Modelos.Noticias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComentariosHelper {

    public ArrayList<Comentarios> getAllComentariosFromNoticia(Connection conexion, Long id){
        AutoresHelper helper = new AutoresHelper();
        ArrayList<Comentarios> listaComentarios = new ArrayList<>();
        String s="SELECT * FROM comentarios where idNoticia=?";
        try {
            PreparedStatement sentencia = conexion.prepareStatement(s);
            sentencia.setLong(1,id);
            ResultSet resultSet = sentencia.executeQuery();
            while(resultSet.next()){
                Long idAutor=resultSet.getLong(5);
                Comentarios c = new Comentarios(resultSet.getLong(1),resultSet.getString(2),
                        resultSet.getDate(3),resultSet.getDate(4),idAutor,id,
                        resultSet.getString(7),helper.getAutorById(conexion,idAutor));
                listaComentarios.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaComentarios;
    }
}
