package sample.Modelos;


public class Noticias {

  private long id;
  private String titulo;
  private String contenido;
  private java.sql.Timestamp fechaCreacion;
  private java.sql.Timestamp fechaUpdate;
  private String imagen;
  private long idAutor;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
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


  public String getImagen() {
    return imagen;
  }

  public void setImagen(String imagen) {
    this.imagen = imagen;
  }


  public long getIdAutor() {
    return idAutor;
  }

  public void setIdAutor(long idAutor) {
    this.idAutor = idAutor;
  }

}
