package com.javaquiz.dao;

import com.javaquiz.beans.Option;
import com.javaquiz.db.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionDAOImpl implements OptionDAO {

    private final DatabaseManager dbManager;

    public OptionDAOImpl() {
        this.dbManager = DatabaseManager.getInstance();
    }

    @Override
    public List<Option> getAllOptionsByQuestionId(int questionId) {
        List<Option> optionList = new ArrayList<>();
        try {
            System.out.println("Executing query: SELECT * FROM Options WHERE question_id=" + questionId);
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Options WHERE question_id=?", questionId);
            while (resultSet.next()) {
                Option option = createOptionFromResultSet(resultSet);
                optionList.add(option);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Options WHERE question_id=" + questionId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return optionList;
    }

    @Override
    public Option getOptionById(int optionId) {
        try {
            System.out.println("Executing query: SELECT * FROM Options WHERE id=" + optionId);
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Options WHERE id=?", optionId);
            if (resultSet.next()) {
                return createOptionFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: SELECT * FROM Options WHERE id=" + optionId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addOption(Option option) {
        try {
            System.out.println("Executing query: INSERT INTO Options (question_id, option_text, is_correct) VALUES (" +
                               option.getQuestionId() + ", '" + option.getOptionText() + "', " + option.isCorrect() + ")");
            int resultCount = dbManager.executeUpdate("INSERT INTO Options (question_id, option_text, is_correct) VALUES (?, ?, ?)",
                    option.getQuestionId(), option.getOptionText(), option.isCorrect());
            return resultCount == 1;
        } catch (Exception e) {
            System.out.println("Error executing query: INSERT INTO Options (question_id, option_text, is_correct) VALUES (" +
                               option.getQuestionId() + ", '" + option.getOptionText() + "', " + option.isCorrect() + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateOption(Option option) {
        try {
            System.out.println("Executing query: UPDATE Options SET question_id=" + option.getQuestionId() +
                               ", option_text='" + option.getOptionText() + "', is_correct=" + option.isCorrect() +
                               " WHERE id=" + option.getId());
            int resultCount = dbManager.executeUpdate("UPDATE Options SET question_id=?, option_text=?, is_correct=? WHERE id=?",
                    option.getQuestionId(), option.getOptionText(), option.isCorrect(), option.getId());
            return resultCount == 1;
        } catch (Exception e) {
            System.out.println("Error executing query: UPDATE Options SET question_id=" + option.getQuestionId() +
                               ", option_text='" + option.getOptionText() + "', is_correct=" + option.isCorrect() +
                               " WHERE id=" + option.getId() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteOption(int optionId) {
        try {
            System.out.println("Executing query: DELETE FROM Options WHERE id=" + optionId);
            int resultCount = dbManager.executeUpdate("DELETE FROM Options WHERE id=?", optionId);
            return resultCount == 1;
        } catch (Exception e) {
            System.out.println("Error executing query: DELETE FROM Options WHERE id=" + optionId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Option createOptionFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int questionId = resultSet.getInt("question_id");
        String optionText = resultSet.getString("option_text");
        boolean isCorrect = resultSet.getBoolean("is_correct");
        return new Option(id, questionId, optionText, isCorrect);
    }

    // Testing the OptionDAOImpl class
    public static void main(String[] args) {
        OptionDAOImpl optionDAOImpl = new OptionDAOImpl();
        optionDAOImpl.testGetAllOptionsByQuestionId();
        optionDAOImpl.testGetOptionById();
//        optionDAOImpl.testAddOption();
//        optionDAOImpl.testUpdateOption();
//        optionDAOImpl.testDeleteOption();
    }

    // Testing getAllOptionsByQuestionId() method
    private void testGetAllOptionsByQuestionId() {
        OptionDAO optionDAO = new OptionDAOImpl();
        List<Option> optionList = optionDAO.getAllOptionsByQuestionId(1);
        for (Option option : optionList) {
            System.out.println(option);
        }
    }

    // Testing getOptionById() method
    private void testGetOptionById() {
        OptionDAO optionDAO = new OptionDAOImpl();
        Option option = optionDAO.getOptionById(1);
        System.out.println(option);
    }

    // Testing addOption() method
    private void testAddOption() {
        OptionDAO optionDAO = new OptionDAOImpl();
        Option option = new Option(1, "Java", true);
        boolean success = optionDAO.addOption(option);
        System.out.println("Option added: " + success);
    }

    // Testing updateOption() method
    private void testUpdateOption() {
        OptionDAO optionDAO = new OptionDAOImpl();
        Option option = new Option(1, "Python", false);
        option.setId(1); // assuming there is an option with id=1 in the database
        boolean success = optionDAO.updateOption(option);
        System.out.println("Option updated: " + success);
    }

    // Testing deleteOption() method
    private void testDeleteOption() {
        OptionDAO optionDAO = new OptionDAOImpl();
        boolean success = optionDAO.deleteOption(1); // assuming there is an option with id=1 in the database
        System.out.println("Option deleted: " + success);
    }
}
