package com.javaquiz.beans;

public class Option {
    private int id;
    private int questionId;
    private String optionText;
    private boolean isCorrect;

    // Konstruktor, getter dhe setter metoda
    public Option() {
        super();
    }

    public Option(int id, int questionId, String optionText, boolean isCorrect) {
        // Konstruktor, getter dhe setter metoda
        super();
        this.id = id;
        this.questionId = questionId;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    public Option(int questionId, String optionText, boolean isCorrect) {
        super();
        this.questionId = questionId;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }
    public Option( String optionText, boolean isCorrect) {
        super();
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "Option{" +
               "id=" + id +
               ", questionId=" + questionId +
               ", optionText='" + optionText + '\'' +
               ", isCorrect=" + isCorrect +
               '}';
    }

    // Test
    public static void main(String[] args) {
        Option option = new Option(1, 1, "Java", true);
        System.out.println(option);
    }
}
