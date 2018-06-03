package com.edu.mvc.controllers;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import com.edu.mvc.models.Criterion;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.comparing.ComparingService;
import com.edu.services.criteriaCheck.Criteria;
import com.edu.services.criteriaCheck.CriteriaCheckService;
import com.edu.services.parsing.ParsingService;
import com.edu.services.semantic.LSAResult;
import com.edu.services.semantic.SemanticService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    ParsingService parsingService;

    @Autowired
    ComparingService comparingService;

    @Autowired
    CriteriaCheckService criteriaCheckService;

    @Autowired
    SemanticService semanticService;

    static final Logger logger = LogManager.getLogger(TestController.class);

    @RequestMapping(value = "/test")
    public ModelAndView test() {
        logger.info("test()");

        List<Object> differences = new ArrayList<>();
        List<Object> differencesPos = new ArrayList<>();
        List<Object> differencesNeg = new ArrayList<>();
        List<Object> differencesEq = new ArrayList<>();
        List<Object> singles = new ArrayList<>();

        ModelAndView mav = new ModelAndView("/test/test");

        // TESTS

        String url = "http://sch10spb.ru/sveden/common/";
        String urlBase = "http://sch10spb.ru/school_life/";
        singles.add(url);

        Document doc = parsingService.parsePage(url);
        Document docBase = parsingService.parsePage(urlBase);
        singles.add(doc);
        singles.add(docBase);
        Document diff = comparingService.getDocumentFromDiff(comparingService.getDifferences(docBase, doc), comparingService.TYPE_ADD);
        Document diff2 = comparingService.getDocumentFromDiff(comparingService.getDifferences(docBase, doc), comparingService.TYPE_DELETE);
        singles.add(diff);
        singles.add(diff2);
        List<String> stopWords = semanticService.getStopWords();
        singles.add(stopWords);
        String text = diff.text();
        String text2 = diff2.text();
        List<String> documents = new ArrayList<>();
        documents.add(text.replaceAll("[^а-яА-Я ]", ""));
//        documents.add(text2.replaceAll("[^а-яА-Я ]", ""));
        singles.add(text);
        singles.add(text2);
        for (Criterion criterion :
                Criteria.CONTENT_CRITERIA) {
            String critText = criterion.getLongDescription().replaceAll("[^а-яА-Я ]", "");
            documents.add(critText);
            singles.add(critText);
        }


        LSAResult lsaResult = semanticService.analyze(documents);

        Matrix A = new Matrix(lsaResult.getFrequencyMatrix());
        singles.add(matrixToString(A));
        SingularValueDecomposition svd = A.svd();
        Matrix u = svd.getU();
        singles.add(matrixToString(u));
        Matrix s = svd.getS();
        singles.add(matrixToString(s));
        Matrix v = svd.getV();
        singles.add(matrixToString(v));
        double[] sV = svd.getSingularValues();

        double[][] coordinates = u.getArray();
        mav.addObject("coordinates", coordinates);



        // ENDTESTS

        mav.addObject("differences", differences);
        mav.addObject("differencesPos", differencesPos);
        mav.addObject("differencesNeg", differencesNeg);
        mav.addObject("differencesEq", differencesEq);
        mav.addObject("singles", singles);

        return mav;
    }

    private String matrixToString(Matrix matrix) {
        String result;
        StringBuilder stringBuilder = new StringBuilder();
        for (double[] row :
                matrix.getArray()) {
            stringBuilder.append("[\t");
            for (double cell :
                    row) {
                stringBuilder.append(cell);
                stringBuilder.append("\t");
            }
            stringBuilder.append("\t]\n");
        }
        result = stringBuilder.toString();
        return result;
    }

}
