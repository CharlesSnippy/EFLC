package com.edu.mvc.models;

public class Criterion {

    private int criteriaId;
    private String name;
    private String shortDescription;
    private String longDescription;
    private String dictionary;

    public int getCriteriaId() {
        return criteriaId;
    }

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getDictionary() {
        return dictionary;
    }



    public void setCriteriaId(int criteriaId) {
        this.criteriaId = criteriaId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

}
