package com.example.gibran.preguntas.modelo;

/**
 * Created by Gibran on 24/11/2017.
 */

public class Questions {

  private  String Questions,AnswerA,AnswerB,AnswerC,AnswerD,CorrectAnswer,CategoryId,IsImageQuestion;

  public Questions() {
  }

  public Questions(String questions, String answerA, String answerB, String answerC, String answerD, String correctAnswer, String categoryId, String isImageQuestion) {
    Questions = questions;
    AnswerA = answerA;
    AnswerB = answerB;
    AnswerC = answerC;
    AnswerD = answerD;
    CorrectAnswer = correctAnswer;
    CategoryId = categoryId;
    IsImageQuestion = isImageQuestion;
  }

  public String getQuestions() {
    return Questions;
  }

  public void setQuestions(String questions) {
    Questions = questions;
  }

  public String getAnswerA() {
    return AnswerA;
  }

  public void setAnswerA(String answerA) {
    AnswerA = answerA;
  }

  public String getAnswerB() {
    return AnswerB;
  }

  public void setAnswerB(String answerB) {
    AnswerB = answerB;
  }

  public String getAnswerC() {
    return AnswerC;
  }

  public void setAnswerC(String answerC) {
    AnswerC = answerC;
  }

  public String getAnswerD() {
    return AnswerD;
  }

  public void setAnswerD(String answerD) {
    AnswerD = answerD;
  }

  public String getCorrectAnswer() {
    return CorrectAnswer;
  }

  public void setCorrectAnswer(String correctAnswer) {
    CorrectAnswer = correctAnswer;
  }

  public String getCategoryId() {
    return CategoryId;
  }

  public void setCategoryId(String categoryId) {
    CategoryId = categoryId;
  }

  public String getIsImageQuestion() {
    return IsImageQuestion;
  }

  public void setIsImageQuestion(String isImageQuestion) {
    IsImageQuestion = isImageQuestion;
  }
}
