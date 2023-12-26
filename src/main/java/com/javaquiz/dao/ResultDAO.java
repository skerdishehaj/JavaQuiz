package com.javaquiz.dao;

import com.javaquiz.beans.Result;

import java.util.List;

public interface ResultDAO {
    Result getResultById(int resultId);

    List<Result> getAllResults();

    boolean addResult(Result result);

    boolean updateResult(Result result);

    boolean deleteResult(int resultId);
}
