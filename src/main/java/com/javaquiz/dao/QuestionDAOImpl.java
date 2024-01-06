package com.javaquiz.dao;

import com.javaquiz.beans.Option;
import com.javaquiz.beans.Question;
import com.javaquiz.db.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAOImpl implements QuestionDAO {

    private final DatabaseManager dbManager;

    public QuestionDAOImpl() {
        this.dbManager = DatabaseManager.getInstance();
    }

    @Override
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        try {
            System.out.println("Executing query: SELECT * FROM Questions");
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Questions");
            while (resultSet.next()) {
                Question question = createQuestionFromResultSet(resultSet);
                questionList.add(question);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Questions: " + e.getMessage());
            e.printStackTrace();
        }
        return questionList;
    }

    @Override
    public List<Question> getAllQuestionsByQuizId(int quizId) {
        List<Question> questionList = new ArrayList<>();
        try {
            System.out.println("Executing query: SELECT * FROM Questions WHERE quiz_id=" + quizId);
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Questions WHERE quiz_id=?", quizId);
            while (resultSet.next()) {
                Question question = createQuestionFromResultSet(resultSet);
                questionList.add(question);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Questions WHERE quiz_id=" + quizId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return questionList;
    }

    @Override
    public Question getQuestionById(int questionId) {
        try {
            System.out.println("Executing query: SELECT * FROM Questions WHERE id=" + questionId);
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Questions WHERE id=?", questionId);
            if (resultSet.next()) {
                return createQuestionFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Questions WHERE id=" + questionId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addQuestion(Question question) {
        try {
            // Insert the question into the Questions table
            System.out.printf("Executing query: INSERT INTO Questions (question_text, points, quiz_id) VALUES (%s, %d, %d)\n",
                    question.getQuestionText(), question.getPoints(), question.getQuizId());
            int questionId = dbManager.executeUpdate(
                    "INSERT INTO Questions (question_text, points, quiz_id) VALUES (?, ?, ?)",
                    question.getQuestionText(), question.getPoints(), question.getQuizId()
            );

            return questionId;
        } catch (Exception e) {
            System.out.printf("Error executing query: INSERT INTO Questions (question_text, points, quiz_id) VALUES (%s, %d, %d): %s\n",
                    question.getQuestionText(), question.getPoints(), question.getQuizId(), e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean updateQuestion(int questionId, Question question) {
        try {
            // Update the question in the Questions table
            System.out.printf("Executing query: UPDATE Questions SET question_text=%s, points=%d, quiz_id=%s WHERE id=%d\n",
                    question.getQuestionText(), question.getPoints(), question.getQuizId(), questionId);
            int questionCount = dbManager.executeUpdate(
                    "UPDATE Questions SET question_text=?, points=?, quiz_id=? WHERE id=?",
                    question.getQuestionText(), question.getPoints(), question.getQuizId(), questionId
            );

            // Update the options in the Options table
/*            System.out.println("Updating options from QuestionDAOImpl");
            OptionDAO optionDAO = new OptionDAOImpl();
            for (Option option : question.getOptions()) {
                optionDAO.updateOption(option.getId(), option);
            }*/

            return questionCount == 1;
        } catch (Exception e) {
            System.out.printf("Error executing query: UPDATE Questions SET question_text=%s, points=%d, quiz_id=%s WHERE id=%d: %s\n",
                    question.getQuestionText(), question.getPoints(), question.getQuizId(), questionId, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteQuestion(int questionId) {
        try {
            // Delete the question from the Questions table
            System.out.printf("Executing query: DELETE FROM Questions WHERE id=%d\n", questionId);
            int questionCount = dbManager.executeUpdate("DELETE FROM Questions WHERE id=?", questionId);
            return questionCount == 1;
        } catch (Exception e) {
            System.out.printf("Error executing query: DELETE FROM Questions WHERE id=%d: %s\n", questionId, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Question createQuestionFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String questionText = resultSet.getString("question_text");
        int points = resultSet.getInt("points");
        int quizId = resultSet.getInt("quiz_id");

        // Retrieve options for the question
        OptionDAO optionDAO = new OptionDAOImpl();
        List<Option> options = optionDAO.getAllOptionsByQuestionId(id);

        return new Question(id, questionText, options, points, quizId);
    }

    // Testing the QuestionDAOImpl class
    public static void main(String[] args) {
        QuestionDAOImpl questionDAOImpl = new QuestionDAOImpl();
//        for (int i = 0; i < 10; i++) {
            questionDAOImpl.testAddQuestion();
//        }
//        questionDAOImpl.testUpdateQuestion();
//        questionDAOImpl.testDeleteQuestion();
//        questionDAOImpl.testGetQuestionById();
//        questionDAOImpl.testGetAllQuestions();
//        questionDAOImpl.testGetAllQuestionsByQuizId();
    }

    // Testing getAllQuestions() method
    private void testGetAllQuestions() {
        QuestionDAO questionDAO = new QuestionDAOImpl();
        List<Question> questionList = questionDAO.getAllQuestions();
        for (Question question : questionList) {
            System.out.println(question);
        }
    }

    // Testing getQuestionById() method
    private void testGetQuestionById() {
        QuestionDAO questionDAO = new QuestionDAOImpl();
        Question question = questionDAO.getQuestionById(1);
        System.out.println(question);
    }

    // Testing addQuestion() method
    private void testAddQuestion() {
        QuestionDAO questionDAO = new QuestionDAOImpl();
        List<Option> options = new ArrayList<>();
        options.add(new Option(1, "Option 1", true));
        options.add(new Option(2, "Option 2", false));
        options.add(new Option(3, "Option 3", false));
        options.add(new Option(4, "Option 4", false));

        Question question = new Question("What is 2 + 2?", options, 5, 1);
        int id = questionDAO.addQuestion(question);
        System.out.println("Question added: " + id);
    }

    // Testing updateQuestion() method
    private void testUpdateQuestion() {
        QuestionDAO questionDAO = new QuestionDAOImpl();
        OptionDAO optionDAO = new OptionDAOImpl();


        Question question = new Question("Updated What is 2 + 2?", optionDAO.getAllOptionsByQuestionId(1), 10, 1);
        boolean success = questionDAO.updateQuestion(1, question);
        System.out.println("Question updated: " + success);
    }

    // Testing deleteQuestion() method
    private void testDeleteQuestion() {
        QuestionDAO questionDAO = new QuestionDAOImpl();
        boolean success = questionDAO.deleteQuestion(1); // assuming there is a question with id=1 in the database
        System.out.println("Question deleted: " + success);
    }

    // Testing getAllQuestionsByQuizId() method
    private void testGetAllQuestionsByQuizId() {
        QuestionDAO questionDAO = new QuestionDAOImpl();
        List<Question> questionList = questionDAO.getAllQuestionsByQuizId(1); // assuming there is a quiz with id=1 in the database
        for (Question question : questionList) {
            System.out.println(question);
        }
    }
}

