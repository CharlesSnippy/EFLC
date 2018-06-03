package com.edu.mvc.controllers;

import Jama.Matrix;
import com.edu.mvc.models.CheckResult;
import com.edu.mvc.models.Criterion;
import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import com.edu.repositories.CriterionRepository;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.criteriaCheck.Criteria;
import com.edu.services.semantic.LSAResult;
import com.edu.services.semantic.SemanticService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class CriteriaCheckController {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    CriterionRepository criterionRepository;

    @Autowired
    SemanticService semanticService;

    static final Logger logger = LogManager.getLogger(CriteriaCheckController.class);

    private List<Criterion> criteria = Criteria.getCriteria();

    @RequestMapping(value = "/check/{siteId}")
    public ModelAndView comparePageIndexBySite(@PathVariable("siteId") int siteId) {
        ModelAndView mav = new ModelAndView("/check");
        Site site = siteRepository.getById(siteId);
        site.setPages(pageRepository.getPagesBySiteId(siteId));
        List<CheckResult> results = new ArrayList<>();
        Map<Page, Integer> pageRating = new HashMap<>();
        for(Page page:
                site.getPages()) {
            pageRating.put(page, 0);
            for (Criterion criterion :
                    Criteria.STRUCTURE_CRITERIA) {
                String cssQuery = compileCssQuery(criterion.getDictionary());
                Elements searchResult = page.getCutDocument().select(":matchesOwn((" + cssQuery + ")(?i))");
                if(searchResult.size() != 0) {
                    CheckResult checkResult = new CheckResult();
                    checkResult.setCriterion(criterion);
                    checkResult.setPage(page);
                    checkResult.setAnswer("найден");
                    checkResult.setBlock(searchResult.eachText());
                    pageRating.put(page, pageRating.get(page) + 1);
                    results.add(checkResult);
                    logger.debug("match found on criterion={}\telement={}\tquery={}\t" +
                            "page={}", criterion.getShortDescription(), searchResult.get(0), ":matchesOwn((" + cssQuery + "))", page.getUrl());
                }
            }
        }
        mav.addObject("results", results);
        mav.addObject("pageRating", pageRating);
        return mav;
    }

    private String compileCssQuery(String dictionary) {
        return String.join("|", dictionary.split(";")).replace("(","\\(").replace(")","\\)").replace('ё', 'е');
    }

    @RequestMapping(value = "/lsa/{pageId}")
    public ModelAndView checkLSA(@PathVariable("pageId") int pageId) {
        ModelAndView mav = new ModelAndView("/tool/lsa");
        Page page = pageRepository.getById(pageId);
        Document document = page.getCutDocument();
        List<String> documents = new ArrayList<>();
        documents.add(document.text());
        for (Criterion criterion :
                Criteria.CONTENT_CRITERIA) {
            documents.add(criterion.getLongDescription());
        }
        LSAResult lsaResult = semanticService.analyze(documents);
        Matrix A = new Matrix(lsaResult.getFrequencyMatrix());
        double[][] wordCoordinates = A.svd().getU().getArray();
//        double[] themeCoordinates = A.svd().getSingularValues();
        double[][] docCoordinates = A.svd().getV().transpose().getArray();
        mav.addObject("page", page);
        mav.addObject("words", lsaResult.getWords());
        mav.addObject("criteria", Criteria.CONTENT_CRITERIA);
        mav.addObject("wordCoordinates", wordCoordinates);
//        mav.addObject("themeCoordinates", themeCoordinates);
        mav.addObject("docCoordinates", docCoordinates);
        return mav;
    }

    @RequestMapping(value = "/lsa/{pageId}/{critId}")
    public ModelAndView checkLSA(@PathVariable("pageId") int pageId, @PathVariable("critId") int critId) {
        ModelAndView mav = new ModelAndView("/tool/lsa2");
        List<String> documents = new ArrayList<>();

        Page page = pageRepository.getById(pageId);
        Document document = page.getCutDocument();


        Criterion criterion = criterionRepository.getById(critId);

//        documents.add(criterion.getLongDescription());
        List<String> subtexts = Arrays.asList(criterion.getLongDescription().split(" "));
        StringBuilder sb = new StringBuilder();
        int sizeCrit = 0;
        for (String subtext:
                subtexts) {

            sb.append(subtext);
            sb.append(" ");
            if(sb.toString().split(" ").length >= 5 ) {
                documents.add(sb.toString());
                sb = new StringBuilder();
                sizeCrit++;
            }
        }

        List<String> subtexts2 = Arrays.asList(document.text().split(" "));
        StringBuilder sb2 = new StringBuilder();
        for (String subtext:
                subtexts2) {

            sb2.append(subtext);
            sb2.append(" ");
            if(sb2.toString().split(" ").length >= 5 ) {
                documents.add(sb2.toString());
                sb2 = new StringBuilder();
            }
        }
//        documents.add(document.text());

        LSAResult lsaResult = semanticService.analyze(documents);
        Matrix A = new Matrix(lsaResult.getFrequencyMatrix());
        double[][] wordCoordinates = A.svd().getU().getArray();
//        double[] themeCoordinates = A.svd().getSingularValues();
        double[][] docCoordinates = A.svd().getV().transpose().getArray();

        mav.addObject("wordFreqMatrix", A.getArray());


        mav.addObject("sizeCrit", sizeCrit);

        mav.addObject("page", page);
        mav.addObject("documents", documents);
        mav.addObject("criterion", criterion);
        mav.addObject("words", lsaResult.getWords());
        mav.addObject("criteria", Criteria.CONTENT_CRITERIA);
        mav.addObject("wordCoordinates", wordCoordinates);
//        mav.addObject("themeCoordinates", themeCoordinates);
        mav.addObject("docCoordinates", docCoordinates);
        return mav;
    }

}
