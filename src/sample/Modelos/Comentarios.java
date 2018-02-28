package sample.Modelos;


public class Comentarios {

  private long id;
  private String contenido;
  private java.sql.Timestamp fechaCreacion;
  private java.sql.Timestamp fechaUpdate;
  private long idAutor;
  private long idNoticia;
  private String titulo;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }


  public java.sql.Timestamp getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(java.sql.Timestamp fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }


  public java.sql.Timestamp getFechaUpdate() {
    return fechaUpdate;
  }

  public void setFechaUpdate(java.sql.Timestamp fechaUpdate) {
    this.fechaUpdate = fechaUpdate;
  }


  public long getIdAutor() {
    return idAutor;
  }

  public void setIdAutor(long idAutor) {
    this.idAutor = idAutor;
  }


  public long getIdNoticia() {
    return idNoticia;
  }

  public void setIdNoticia(long idNoticia) {
    this.idNoticia = idNoticia;
  }


  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

}
