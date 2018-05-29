package com.edu.mvc.controllers;

import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.comparing.ComparingService;
import com.edu.services.parsing.DiffResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class ComparatorController {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    ComparingService comparingService;

    static final Logger logger = LogManager.getLogger(ComparatorController.class);

    @RequestMapping(value = "/compare")
    public ModelAndView compareIndex() {
        logger.info("compareIndex()");
        return new ModelAndView("tool/compare");
    }

    @RequestMapping(value = "/compare/{siteId}/{idA}/{idB}")
    public ModelAndView comparePages(@PathVariable("siteId") int siteId, @PathVariable("idA") int pageIdA, @PathVariable("idB") int pageIdB) {
        logger.info("comparePages({}, {}, {})", siteId, pageIdA, pageIdB);

        ModelAndView mav = comparePageIndexBySite(siteId);

        Page a = pageRepository.getById(pageIdA);
        Page b = pageRepository.getById(pageIdB);

        mav.addObject("firstPage", a);
        mav.addObject("secondPage", b);
        mav.addObject("compareResult", comparingService.getDifferences(a.getDocument(), b.getDocument()));

        return mav;
    }

    @RequestMapping(value = "/compare/{siteId}")
    public ModelAndView comparePageIndexBySite(@PathVariable("siteId") int siteId) {
        logger.info("comparePageIndexBySite({})", siteId);

        ModelAndView mav = new ModelAndView("tool/compare");

        Map<Integer, String> pageOptions = new LinkedHashMap<Integer, String>();

        Site site = siteRepository.getById(siteId);
        site.setPages(pageRepository.getPagesBySiteId(siteId));
        for (Page page :
                site.getPages()) {
            if(page.getUrl().equals(site.getUrl())) {
                pageOptions.put(page.getPageId(), page.getTitle() + " | " + page.getUrl());
                break;
            }
        }

        for (Page page :
                site.getPages()) {
            if(!page.getUrl().equals(site.getUrl())) {
                pageOptions.put(page.getPageId(), page.getTitle() + " | " + page.getUrl());
            }
        }

        mav.addObject("site", site);
        mav.addObject("diffResult", new DiffResult());
        mav.addObject("pageOptions", pageOptions);

        return mav;
    }

    @RequestMapping(value = "/compare/{siteId}/getDiff")
    public ModelAndView comparePageIndexBySite(@PathVariable("siteId") int siteId, @ModelAttribute("diffResult") DiffResult diffResult) {
        logger.info("comparePageIndexBySite({}, {})", siteId, diffResult);
        return new ModelAndView("redirect:/compare/" + siteId + "/" + diffResult.getFirstIndex() + "/" + diffResult.getSecondIndex());
    }

}
