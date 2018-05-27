package com.edu.services.criteriaCheck;

import com.edu.mvc.models.CheckResult;
import com.edu.mvc.models.Criterion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CriteriaCheckService {

    /**
     * Gets result of block checking with criteria
     *
     * @param block
     * @param criterion
     * @return
     */
    CheckResult checkBlockByCriterion(List<String> block, Criterion criterion);

}
