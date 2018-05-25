package com.edu.mvc.models;

import java.util.List;

public class CheckResult {

    private int resultId;
    private Criterion criterion;
    private Page page;
    private String answer;
    private List<String> block;


    public int getResultId() {
        return resultId;
    }

    public Criterion getCriterion() {
        return criterion;
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

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
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
