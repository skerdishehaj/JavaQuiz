package com.javaquiz.dao;

import com.javaquiz.beans.Option;

import java.util.List;

public interface OptionDAO {
    List<Option> getAllOptionsByQuestionId(int questionId);

    Option getOptionById(int optionId);

    boolean addOption(Option option);

    boolean updateOption(Option option);

    boolean deleteOption(int optionId);

}
