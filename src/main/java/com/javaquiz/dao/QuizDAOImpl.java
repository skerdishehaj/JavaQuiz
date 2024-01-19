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

    /*    @Override
        public String getPhoto(int quizId) {
            try {
                System.out.println("Executing query: SELECT photo FROM Quizzes WHERE id=" + quizId);
                ResultSet resultSet = dbManager.executeQuery("SELECT photo FROM Quizzes WHERE id=?", quizId);
                if (resultSet.next()) {
                    return resultSet.getString("photo");
                }
            } catch (SQLException e) {
                System.out.println("Error executing query: SELECT photo FROM Quizzes WHERE id=" + quizId + ": " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }*/
    @Override
    public List<Quiz> getAllQuizzes(boolean includeQuestions) {
        List<Quiz> quizList = new ArrayList<>();
        try {
            System.out.println("Executing query: SELECT * FROM Quizzes");
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Quizzes");
            while (resultSet.next()) {
                Quiz quiz = createQuizFromResultSet(resultSet, includeQuestions);
                quizList.add(quiz);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Quizzes: " + e.getMessage());
            e.printStackTrace();
        }
        return quizList;
    }

    @Override
    public Quiz getQuizById(int quizId, boolean includeQuestions) {
        try {
            System.out.println("Executing query: SELECT * FROM Quizzes WHERE id=" + quizId);
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Quizzes WHERE id=?", quizId);
            if (resultSet.next()) {
                return createQuizFromResultSet(resultSet, includeQuestions);
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
            System.out.printf("Executing query: INSERT INTO Quizzes (title, topic, photo) VALUES (%s, %s, %s)\n",
                    quiz.getTitle(), quiz.getTopic(), quiz.getPhoto());
            int quizId = dbManager.executeUpdate(
                    "INSERT INTO Quizzes (title, topic, photo) VALUES (?, ?, ?)",
                    quiz.getTitle(), quiz.getTopic(), quiz.getPhoto()
            );
            return quizId == 1;
        } catch (Exception e) {
            System.out.printf("Error executing query: INSERT INTO Quizzes (title, topic, photo) VALUES (%s, %s, %s): %s\n",
                    quiz.getTitle(), quiz.getTopic(), quiz.getPhoto(), e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateQuiz(int quizId, Quiz quiz) {
        try {
            // Update the quiz in the Quizzes table
            System.out.printf("Executing query: UPDATE Quizzes SET title=%s, topic=%s, photo=%s WHERE id=%d\n",
                    quiz.getTitle(), quiz.getTopic(), quiz.getPhoto(), quizId);
            int quizCount = dbManager.executeUpdate(
                    "UPDATE Quizzes SET title=?, topic=?, photo=? WHERE id=?",
                    quiz.getTitle(), quiz.getTopic(), quiz.getPhoto(), quizId
            );

            return quizCount != -1;
        } catch (Exception e) {
            System.out.printf("Error executing query: UPDATE Quizzes SET title=%s, topic=%s, photo=%s WHERE id=%d: %s\n",
                    quiz.getTitle(), quiz.getTopic(), quiz.getPhoto(), quizId, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteQuiz(int quizId) {
        try {
            // Delete the quiz from the Quizzes table
            System.out.printf("Executing query: DELETE FROM Quizzes WHERE id=%d\n", quizId);
            int quizCount = dbManager.executeUpdate("DELETE FROM Quizzes WHERE id=?", quizId);
            return quizCount == 1;
        } catch (Exception e) {
            System.out.printf("Error executing query: DELETE FROM Quizzes WHERE id=%d: %s\n", quizId, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Quiz createQuizFromResultSet(ResultSet resultSet, boolean includeQuestions) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String topic = resultSet.getString("topic");
        String photo = resultSet.getString("photo");

        // Retrieve questions for the quiz
        if (includeQuestions) {
            QuestionDAO questionDAO = new QuestionDAOImpl();
            List<Question> questions = questionDAO.getAllQuestionsByQuizId(id);
            return new Quiz(id, title, topic, photo, questions);
        }

        return new Quiz(id, title, topic, photo);
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
        List<Quiz> quizList = quizDAO.getAllQuizzes(true);
        for (Quiz quiz : quizList) {
            System.out.println(quiz);
        }
    }

    // Testing getQuizById() method
    private void testGetQuizById() {
        QuizDAO quizDAO = new QuizDAOImpl();
        Quiz quiz = quizDAO.getQuizById(1, true);
        System.out.println(quiz);
    }

    // Testing addQuiz() method
    private void testAddQuiz() {
        QuizDAO quizDAO = new QuizDAOImpl();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", null, 5, 1));
        questions.add(new Question("Question 2", null, 10, 1));

        Quiz quiz = new Quiz("Quiz 1", "Java", "", questions);
        boolean success = quizDAO.addQuiz(quiz);
        System.out.println("Quiz added: " + success);
    }

    // Testing updateQuiz() method
    private void testUpdateQuiz() {
        QuizDAO quizDAO = new QuizDAOImpl();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Updated Question 1", null, 8, 1));
        questions.add(new Question("Updated Question 2", null, 12, 1));

        Quiz quiz = new Quiz("Updated Quiz 1", "Java", "", questions);
        boolean success = quizDAO.updateQuiz(1, quiz);
        System.out.println("Quiz updated: " + success);
    }

    // Testing deleteQuiz() method
    private void testDeleteQuiz() {
        QuizDAO quizDAO = new QuizDAOImpl();
        boolean success = quizDAO.deleteQuiz(1); // assuming there is a quiz with id=1 in the database
        System.out.println("Quiz deleted: " + success);
    }
}
