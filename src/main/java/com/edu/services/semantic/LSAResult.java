package com.edu.services.semantic;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

import java.util.List;
import java.util.Map;

public class LSAResult {

    List<String> words;
    List<String> documents;
    Map<String, Double> documentMap;
    Matrix base;
    SingularValueDecomposition singularValueDecomposition;
    Matrix leftSingularVectors;
    Matrix diagonalMatrix;
    Matrix rightSingularVectors;
    double[] singularValues;
    double[][] frequencyMatrix;

    public List<String> getDocuments() {
        return documents;
    }

    public List<String> getWords() {
        return words;
    }

    public double[][] getFrequencyMatrix() {
        return frequencyMatrix;
    }


    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public void setFrequencyMatrix(double[][] frequencyMatrix) {
        this.frequencyMatrix = frequencyMatrix;
    }
}
