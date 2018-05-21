package com.edu.mvc.models;

import java.util.List;

public class CheckResult {

    int resultId;
    Criteria criteria;
    Page page;
    String answer;
    List<String> block;

    public int getResultId() {
        return resultId;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public Page getPage() {
        return page;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getBlock() {
        return block;
    }



    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setBlock(List<String> block) {
        this.block = block;
    }
}
