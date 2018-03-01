package sample.BaseDeDatos;

import sample.Modelos.Comentarios;
import sample.Modelos.Noticias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoticiasHelper {

    public ArrayList<Noticias> getAllNoticias(Connection conexion){
        AutoresHelper ahelper = new AutoresHelper();
        ComentariosHelper chelper = new ComentariosHelper();
        ArrayList<Noticias> listaNoticias = new ArrayList<>();
        String s="SELECT * FROM noticias";
        try {
            PreparedStatement sentencia = conexion.prepareStatement(s);
            ResultSet resultSet = sentencia.executeQuery();
            while(resultSet.next()){
                            Long idAutor=resultSet.getLong(7);
                            Long idNoticia = resultSet.getLong(1);
                Noticias n = new Noticias(idNoticia,resultSet.getString(2),resultSet.getString(3),
                        resultSet.getDate(4),resultSet.getDate(5),resultSet.getString(6),
                        idAutor, ahelper.getAutorById(conexion,idAutor),chelper.getAllComentariosFromNoticia(conexion,idNoticia));
                listaNoticias.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaNoticias;
    }
}
