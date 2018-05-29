package com.edu.services.criteriaCheck;

import com.edu.mvc.models.CheckResult;
import com.edu.mvc.models.Criterion;
import com.edu.repositories.CriterionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class CriteriaCheckServiceImpl implements CriteriaCheckService {

    @Autowired
    CriterionRepository criterionRepository;

    static final Logger logger = LogManager.getLogger(CriteriaCheckServiceImpl.class);

    @PostConstruct
    public void init() {
        logger.info("init()");
        initCriteria();
    }

    @Override
    public void initCriteria() {
        logger.info("initCriteria()");
        if(criterionRepository.getAll().isEmpty()) {
            for (Criterion criterion :
                    Criteria.getCriteria()) {
                criterionRepository.create(criterion);
            }
        } else {
            if(criterionRepository.getAll().size() == Criteria.getCriteria().size()) {
                for (Criterion criterion :
                        Criteria.getCriteria()) {
                    criterionRepository.update(criterion);
                }
            } else {
                logger.error("initCriteria(): size of criteria is different in Criteria and DB");
            }
        }
    }

    @Override
    public CheckResult checkBlockByCriterion(List<String> block, Criterion criterion) {
        logger.info("checkBlockByDictionary({}, {})", block, criterion);
        CheckResult checkResult = new CheckResult();
        checkResult.setCriterion(criterion);
        Map<String, Object> options = criterion.getOptions();
        return checkResult;
    }
}
