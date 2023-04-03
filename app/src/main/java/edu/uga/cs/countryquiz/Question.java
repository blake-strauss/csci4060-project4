package edu.uga.cs.countryquiz;

public class Question {

    private String countryName;
    private String correctAnswer;
    private String[] wrongAnswers = new String[2];

    public Question() {
        this.countryName = null;
        this.correctAnswer = null;
        this.wrongAnswers = null;
    }

    public Question(String countryName, String correctAnswer, String[] wrongAnswers){
        this.countryName = countryName;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
    }

    public String getCountryName() {return countryName;}
    public void setCountryName(String countryName) {this.countryName = countryName;}
    public String getCorrectAnswer() {return correctAnswer;}
    public void setCorrectAnswer(String correctAnswer) {this.correctAnswer = correctAnswer;}
    public String[] getWrongAnswers() {return wrongAnswers;}
    public void setWrongAnswers(String[] wrongAnswers) {this.wrongAnswers = wrongAnswers;}


}
