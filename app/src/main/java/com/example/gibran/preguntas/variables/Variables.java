package com.example.gibran.preguntas.variables;

import com.example.gibran.preguntas.modelo.Questions;
import com.example.gibran.preguntas.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gibran on 22/11/2017.
 */

public class Variables {

  public static String categoryId, categoryName;
  public static Usuario currentUser;
  public  static List<Questions> questionsList = new ArrayList<>();

  public static final String STR_PUSH = "pushNotification";

  public static final String USER_KEY = "Usuario";
  public static final String PWD_KEY = "Contraseña";



}
