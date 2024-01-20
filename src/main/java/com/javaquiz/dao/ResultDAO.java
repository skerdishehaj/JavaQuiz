package com.javaquiz.dao;

import com.javaquiz.beans.Result;
import com.javaquiz.beans.ResultsReport;

import java.util.List;

public interface ResultDAO {
    Result getResultById(int resultId);

    List<Result> getAllResults();
    List<ResultsReport> getAllResultsByUserId(int userId);

    boolean addResult(Result result);

    boolean updateResult(Result result);

    boolean deleteResult(int resultId);
}
