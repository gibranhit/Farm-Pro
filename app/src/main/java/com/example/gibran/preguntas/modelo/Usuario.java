package com.example.gibran.preguntas.modelo;

/**
 * Created by Gibran on 20/11/2017.
 */
//modelo de la base de datos para los usuarios
public class Usuario {

  private String Nombre;
  private String Usuario;
  private String Apellidos;
  private String contraseña;
  private String matricula;
  private String email;

  public Usuario(String Nombre, String Apellidos, String Usuario, String matricula,String email, String contraseña) {
    this.Nombre = Nombre;
    this.Usuario = Usuario;
    this.Apellidos = Apellidos;
    this.contraseña = contraseña;
    this.matricula = matricula;
    this.email = email;
  }



  public String getUsuario() {
    return Usuario;
  }

  public void setUsuario(String Usuario) {
    this.Usuario = Usuario;
  }

  public String getApellidos() {
    return Apellidos;
  }

  public void setApellidos(String Apellidos) {
    this.Apellidos = Apellidos;
  }

  public Usuario(){

  }

  public String getNombre() {
    return Nombre;
  }

  public void setNombre(String Nombre) {
    this.Nombre = Nombre;
  }

  public String getContraseña() {
    return contraseña;
  }

  public void setContraseña(String contraseña) {
    this.contraseña = contraseña;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMatricula() {
    return matricula;
  }

  public void setMatricula(String matricula) {
    this.matricula = matricula;
  }


}
