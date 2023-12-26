package com.javaquiz.dao;

import com.javaquiz.beans.Result;
import com.javaquiz.db.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultDAOImpl implements ResultDAO {

    private final DatabaseManager dbManager;

    public ResultDAOImpl() {
        this.dbManager = DatabaseManager.getInstance();
    }

    @Override
    public List<Result> getAllResults() {
        List<Result> resultList = new ArrayList<>();
        try {
            System.out.println("Executing query: SELECT * FROM Results");
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Results" );
            while (resultSet.next()) {
                Result result = createResultFromResultSet(resultSet);
                resultList.add(result);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Results: " + e.getMessage());
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Result getResultById(int resultId) {
        try {
            System.out.println("Executing query: SELECT * FROM Results WHERE id=" + resultId);
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Results WHERE id=?", resultId);
            if (resultSet.next()) {
                return createResultFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Results WHERE id=" + resultId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addResult(Result result) {
        try {
            System.out.println("Executing query: INSERT INTO Results (user_id, quiz_id, score, date) VALUES (" +
                    result.getUserId() + ", " + result.getQuizId() + ", " + result.getScore() + ", " + result.getDate() + ")");
            int resultCount = dbManager.executeUpdate("INSERT INTO Results (user_id, quiz_id, score, date) VALUES (?, ?, ?, ?)",
                    result.getUserId(), result.getQuizId(), result.getScore(), result.getDate());
            return resultCount == 1;
        } catch (Exception e) {
            System.out.println("Error executing query: INSERT INTO Results (user_id, quiz_id, score, date) VALUES (" +
                    result.getUserId() + ", " + result.getQuizId() + ", " + result.getScore() + ", " + result.getDate() + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateResult(Result result) {
        try {
            System.out.println("Executing query: UPDATE Results SET user_id=" + result.getUserId() + ", quiz_id=" + result.getQuizId() +
                    ", score=" + result.getScore() + ", date=" + result.getDate() + " WHERE id=" + result.getId());
            int resultCount = dbManager.executeUpdate("UPDATE Results SET user_id=?, quiz_id=?, score=?, date=? WHERE id=?",
                    result.getUserId(), result.getQuizId(), result.getScore(), result.getDate(), result.getId());
            return resultCount == 1;
        } catch (Exception e) {
            System.out.println("Error executing query: UPDATE Results SET user_id=" + result.getUserId() + ", quiz_id=" + result.getQuizId() +
                    ", score=" + result.getScore() + ", date=" + result.getDate() + " WHERE id=" + result.getId() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteResult(int resultId) {
        try {
            System.out.println("Executing query: DELETE FROM Results WHERE id=" + resultId);
            int resultCount = dbManager.executeUpdate("DELETE FROM Results WHERE id=?", resultId);
            return resultCount == 1;
        } catch (Exception e) {
            System.out.println("Error executing query: DELETE FROM Results WHERE id=" + resultId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Result createResultFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        int quizId = resultSet.getInt("quiz_id");
        int score = resultSet.getInt("score");
        java.sql.Date date = resultSet.getDate("date");
        return new Result(id, userId, quizId, score, new java.util.Date(date.getTime()));
    }

    // Testing the ResultDAOImpl class
    public static void main(String[] args) {
        ResultDAOImpl resultDAOImpl = new ResultDAOImpl();
        /*for (int i = 0; i < 10; i++) {
            resultDAOImpl.testAddResult();
        }*/
//        resultDAOImpl.testUpdateResult();
//        resultDAOImpl.testDeleteResult();
        resultDAOImpl.testGetResultById();
        resultDAOImpl.testGetAllResults();
    }

    // Testing getAllResults() method
    private void testGetAllResults() {
        ResultDAO resultDAO = new ResultDAOImpl();
        List<Result> resultList = resultDAO.getAllResults();
        for (Result result : resultList) {
            System.out.println(result);
        }
    }

    // Testing getResultById() method
    private void testGetResultById() {
        ResultDAO resultDAO = new ResultDAOImpl();
        Result result = resultDAO.getResultById(1);
        System.out.println(result);
    }

    // Testing addResult() method
    private void testAddResult() {
        ResultDAO resultDAO = new ResultDAOImpl();
        Result result = new Result(1, 1, 10, new Date());
        boolean success = resultDAO.addResult(result);
        System.out.println("Result added: " + success);
    }

    // Testing updateResult() method
    private void testUpdateResult() {
        ResultDAO resultDAO = new ResultDAOImpl();
        Result result = new Result(1, 1, 15, new Date());
        result.setId(1); // assuming there is a result with id=1 in the database
        boolean success = resultDAO.updateResult(result);
        System.out.println("Result updated: " + success);
    }

    // Testing deleteResult() method
    private void testDeleteResult() {
        ResultDAO resultDAO = new ResultDAOImpl();
        boolean success = resultDAO.deleteResult(1); // assuming there is a result with id=1 in the database
        System.out.println("Result deleted: " + success);
    }
}
