package com.edu.services.criteriaCheck;

import com.edu.mvc.models.CheckResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriteriaCheckServiceImpl implements CriteriaCheckService {

    static final Logger logger = LogManager.getLogger(CriteriaCheckServiceImpl.class);

    @Override
    public CheckResult checkBlockByDictionary(List<String> block, List<String> dictionary) {
        logger.info("checkBlockByDictionary({}, {})", block, dictionary);
        CheckResult checkResult = new CheckResult();
//        checkResult.setPage(page);
//        List<String> dictionary = new ArrayList<>();
//        for (String row : Arrays.asList(dictionary.getDictionary().split("\n"))) {
//            dictionary.add(stem(row));
//        }
//        List<String> rows = new ArrayList<>();
//        for (String row : Arrays.asList(page.getDocument().outerHtml().split("\n"))) {
//            rows.add(stem(row));
//        }

        for (String line :
                block) {
            for (String word :
                    dictionary) {
                line.contains(word);
            }
        }
        return checkResult;
    }
}
