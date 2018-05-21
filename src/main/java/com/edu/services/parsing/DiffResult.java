package com.edu.services.parsing;

public class DiffResult {

    private String firstIndex;
    private String secondIndex;
    private String row;
    private char type = ParsingService.TYPE_EQUAL;



    public String getFirstIndex() {
        return firstIndex;
    }

    public String getSecondIndex() {
        return secondIndex;
    }

    public String getRow() {
        return row;
    }

    public char getType() {
        return type;
    }



    public void setFirstIndex(String firstIndex) {
        this.firstIndex = firstIndex;
    }

    public void setSecondIndex(String secondIndex) {
        this.secondIndex = secondIndex;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setType(char type) {
        this.type = type;
    }
}
