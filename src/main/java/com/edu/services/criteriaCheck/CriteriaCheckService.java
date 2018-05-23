package com.edu.services.criteriaCheck;

import com.edu.mvc.models.CheckResult;

import java.util.List;

public interface CriteriaCheckService {

    /**
     * Gets result of block checking with criteria
     *
     * @param block
     * @param dictionary
     * @return
     */
    CheckResult checkBlockByDictionary(List<String> block, List<String> dictionary);

}
