package com.edu.services.semantic;

import org.springframework.stereotype.Service;

@Service
public interface SemanticService {

    String stemWord(String word);

}
