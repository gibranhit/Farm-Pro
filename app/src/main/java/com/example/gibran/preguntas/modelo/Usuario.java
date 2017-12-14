package com.example.gibran.preguntas.modelo;

/**
 * Created by Gibran on 20/11/2017.
 */

public class Usuario {

  private String nombre;
  private String contraseña;
  private String matricula;
  private String email;

  public Usuario(){

  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
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

  public Usuario(String nombre, String contraseña,String matricula, String email) {
    this.nombre = nombre;
    this.contraseña = contraseña;
    this.matricula = matricula;
    this.email = email;
  }
}
