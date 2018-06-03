package com.edu.services.semantic;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SemanticService {

    String stemWord(String word);

    List<String> getStopWords();

    String removeSymbols(String word);

    LSAResult analyze(List<String> documents);
}
