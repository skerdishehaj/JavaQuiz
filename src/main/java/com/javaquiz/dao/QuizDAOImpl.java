package com.javaquiz.dao;

import com.javaquiz.beans.Question;
import com.javaquiz.beans.Quiz;
import com.javaquiz.db.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAOImpl implements QuizDAO {

    private final DatabaseManager dbManager;

    public QuizDAOImpl() {
        this.dbManager = DatabaseManager.getInstance();
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizList = new ArrayList<>();
        try {
            System.out.println("Executing query: SELECT * FROM Quizzes");
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Quizzes");
            while (resultSet.next()) {
                Quiz quiz = createQuizFromResultSet(resultSet);
                quizList.add(quiz);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Quizzes: " + e.getMessage());
            e.printStackTrace();
        }
        return quizList;
    }

    @Override
    public Quiz getQuizById(int quizId) {
        try {
            System.out.println("Executing query: SELECT * FROM Quizzes WHERE id=" + quizId);
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Quizzes WHERE id=?", quizId);
            if (resultSet.next()) {
                return createQuizFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Quizzes WHERE id=" + quizId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addQuiz(Quiz quiz) {
        try {
            // Insert the quiz into the Quizzes table
            System.out.println("Executing query: INSERT INTO Quizzes (title, topic) VALUES (?, ?)");
            int quizId = dbManager.executeUpdate(
                    "INSERT INTO Quizzes (title, topic) VALUES (?, ?)",
                    quiz.getTitle(), quiz.getTopic()
            );

            // Add questions to the quiz
            if (quizId == 1) {
                for (Question question : quiz.getQuestions()) {
                    question.setQuizId(quizId);
                    QuestionDAO questionDAO = new QuestionDAOImpl();
                    questionDAO.addQuestion(question);
                }
            }

            return quizId == 1;
        } catch (Exception e) {
            System.out.println("Error executing query: INSERT INTO Quizzes (title, topic) VALUES (?, ?): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateQuiz(Quiz quiz) {
        try {
            // Update the quiz in the Quizzes table
            System.out.println("Executing query: UPDATE Quizzes SET title=?, topic=? WHERE id=?");
            int quizCount = dbManager.executeUpdate(
                    "UPDATE Quizzes SET title=?, topic=? WHERE id=?",
                    quiz.getTitle(), quiz.getTopic(), quiz.getId()
            );

            // Update questions in the quiz
            if (quizCount == 1) {
                for (Question question : quiz.getQuestions()) {
                    QuestionDAO questionDAO = new QuestionDAOImpl();
                    questionDAO.updateQuestion(question);
                }
            }

            return quizCount == 1;
        } catch (Exception e) {
            System.out.println("Error executing query: UPDATE Quizzes SET title=?, topic=? WHERE id=?: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteQuiz(int quizId) {
        try {
            // Delete the quiz from the Quizzes table
            System.out.println("Executing query: DELETE FROM Quizzes WHERE id=?");
            int quizCount = dbManager.executeUpdate("DELETE FROM Quizzes WHERE id=?", quizId);
            return quizCount == 1;
        } catch (Exception e) {
            System.out.println("Error executing query: DELETE FROM Quizzes WHERE id=?: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Quiz createQuizFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String topic = resultSet.getString("topic");

        // Retrieve questions for the quiz
        QuestionDAO questionDAO = new QuestionDAOImpl();
        List<Question> questions = questionDAO.getAllQuestionsByQuizId(id);

        return new Quiz(id, title, topic, questions);
    }

    // Testing the QuizDAOImpl class
    public static void main(String[] args) {
        QuizDAOImpl quizDAOImpl = new QuizDAOImpl();
        quizDAOImpl.testAddQuiz();
//        quizDAOImpl.testUpdateQuiz();
//        quizDAOImpl.testDeleteQuiz();
        quizDAOImpl.testGetQuizById();
        quizDAOImpl.testGetAllQuizzes();
    }

    // Testing getAllQuizzes() method
    private void testGetAllQuizzes() {
        QuizDAO quizDAO = new QuizDAOImpl();
        List<Quiz> quizList = quizDAO.getAllQuizzes();
        for (Quiz quiz : quizList) {
            System.out.println(quiz);
        }
    }

    // Testing getQuizById() method
    private void testGetQuizById() {
        QuizDAO quizDAO = new QuizDAOImpl();
        Quiz quiz = quizDAO.getQuizById(1);
        System.out.println(quiz);
    }

    // Testing addQuiz() method
    private void testAddQuiz() {
        QuizDAO quizDAO = new QuizDAOImpl();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", null, 5, 1));
        questions.add(new Question("Question 2", null, 10, 1));

        Quiz quiz = new Quiz("Quiz 1", "Java", questions);
        boolean success = quizDAO.addQuiz(quiz);
        System.out.println("Quiz added: " + success);
    }

    // Testing updateQuiz() method
    private void testUpdateQuiz() {
        QuizDAO quizDAO = new QuizDAOImpl();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Updated Question 1", null, 8, 1));
        questions.add(new Question("Updated Question 2", null, 12, 1));

        Quiz quiz = new Quiz(1, "Updated Quiz 1", "Java", questions);
        boolean success = quizDAO.updateQuiz(quiz);
        System.out.println("Quiz updated: " + success);
    }

    // Testing deleteQuiz() method
    private void testDeleteQuiz() {
        QuizDAO quizDAO = new QuizDAOImpl();
        boolean success = quizDAO.deleteQuiz(1); // assuming there is a quiz with id=1 in the database
        System.out.println("Quiz deleted: " + success);
    }
}
