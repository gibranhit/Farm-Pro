package com.example.gibran.preguntas.modelo;

/**
 * Created by Gibran on 17/01/2018.
 */

public class Conexion {


  private int Intentos;

  public Conexion(){

  }

  public Conexion( int intentos) {

    Intentos = intentos;
  }

  public int getIntentos() {
    return Intentos;
  }

  public void setIntentos(int intentos) {
    Intentos = intentos;
  }
}
