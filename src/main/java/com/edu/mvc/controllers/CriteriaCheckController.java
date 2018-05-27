package com.edu.mvc.controllers;

import com.edu.mvc.models.Criterion;
import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.criteriaCheck.Criteria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CriteriaCheckController {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    PageRepository pageRepository;

    static final Logger logger = LogManager.getLogger(CriteriaCheckController.class);

    private List<Criterion> criteria = Criteria.getCriteria();

    @RequestMapping(value = "/check/{siteId}")
    public ModelAndView comparePageIndexBySite(@PathVariable("siteId") int siteId) {
        ModelAndView mav = new ModelAndView("/check");
        Site site = siteRepository.getById(siteId);
        site.setPages(pageRepository.getPagesBySiteId(siteId));
        for(Page page:
                site.getPages()) {
            for (Criterion criterion :
                    Criteria.STRUCTURE_CRITERIA) {
                String cssQuery = compileCssQuery(criterion.getDictionary());
                Elements searchResult = page.getDocument().select(":matchesOwn((" + cssQuery + "))");
                if(searchResult.size() != 0) {
                    logger.debug("match found on criterion={}\telement={}\tquery={}\t" +
                            "page={}", criterion.getShortDescription(), searchResult.get(0), ":matchesOwn((" + cssQuery + "))", page.getUrl());
                }
            }
        }
        return mav;
    }

    private String compileCssQuery(String dictionary) {
        return String.join("|", dictionary.split(";")).replace("(","\\(").replace(")","\\)").replace('ё', 'е');
    }

}
