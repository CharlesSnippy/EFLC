package com.edu.mvc.controllers;

import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.comparing.ComparingService;
import com.edu.services.comparing.ComparingServiceImpl;
import com.edu.services.parsing.DiffResult;
import org.jsoup.nodes.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ComparatorController {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    PageRepository pageRepository;

    private ComparingService comparingService = new ComparingServiceImpl();

    @RequestMapping(value = "/compare")
    public ModelAndView compareIndex() {
        return new ModelAndView("/compare");
    }

    @RequestMapping(value = "/compare/{siteId}/{idA}/{idB}")
    public ModelAndView comparePages(@PathVariable("siteId") int siteId, @PathVariable("idA") int pageIdA, @PathVariable("idB") int pageIdB) {
        ModelAndView mav = new ModelAndView("/compare");

        Page a = pageRepository.getById(pageIdA);
        Page b = pageRepository.getById(pageIdB);
        List<String> listA = Arrays.asList(a.getDocument().outerHtml().split("\n"));
        List<String> listB = Arrays.asList(b.getDocument().outerHtml().split("\n"));

        for (TextNode tn :
                a.getDocument().textNodes()) {
            listA.add(tn.text());
        }
        for (TextNode tn :
                b.getDocument().textNodes()) {
            listB.add(tn.text());
        }

        Map<Integer, String> pageOptions = new LinkedHashMap<Integer, String>();

        Site site = siteRepository.getById(siteId);
        site.setPages(pageRepository.getPagesBySiteId(siteId));
        for (Page page :
                site.getPages()) {
            pageOptions.put(page.getPageId(), page.getTitle() + " | " + page.getUrl());
        }

        mav.addObject("site", site);
        mav.addObject("firstPage", a);
        mav.addObject("secondPage", b);
        mav.addObject("compareResult", comparingService.getDifferences(listA, listB));
        mav.addObject("pageOptions", pageOptions);
        mav.addObject("diffResult", new DiffResult());

        return mav;
    }

    @RequestMapping(value = "/compare/{siteId}")
    public ModelAndView comparePageIndexBySite(@PathVariable("siteId") int siteId) {
        ModelAndView mav = new ModelAndView("/compare");

        Map<Integer, String> pageOptions = new LinkedHashMap<Integer, String>();

        Site site = siteRepository.getById(siteId);
        site.setPages(pageRepository.getPagesBySiteId(siteId));
        for (Page page :
                site.getPages()) {
            pageOptions.put(page.getPageId(), page.getTitle() + " | " + page.getUrl());
        }

        mav.addObject("site", site);
        mav.addObject("diffResult", new DiffResult());
        mav.addObject("pageOptions", pageOptions);

        return mav;
    }

    @RequestMapping(value = "/compare/{siteId}/getDiff")
    public ModelAndView comparePageIndexBySite(@PathVariable("siteId") int siteId, @ModelAttribute("diffResult") DiffResult diffResult) {
        return new ModelAndView("redirect:/compare/" + siteId + "/" + diffResult.getFirstIndex() + "/" + diffResult.getSecondIndex());
    }

}
