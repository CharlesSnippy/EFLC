package com.edu.services.criteriaCheck;

import com.edu.mvc.models.CheckResult;
import com.edu.mvc.models.Criterion;
import com.edu.repositories.CriterionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CriteriaCheckServiceImpl implements CriteriaCheckService {

    @Autowired
    CriterionRepository criterionRepository;

    static final Logger logger = LogManager.getLogger(CriteriaCheckServiceImpl.class);

    @Override
    public CheckResult checkBlockByCriterion(List<String> block, Criterion criterion) {
        logger.info("checkBlockByDictionary({}, {})", block, criterion);
        CheckResult checkResult = new CheckResult();
        checkResult.setCriterion(criterion);
        Map<String, Object> options = criterion.getOptions();
        return checkResult;
    }
}
