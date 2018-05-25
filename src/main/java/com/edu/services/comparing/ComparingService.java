package com.edu.services.comparing;

import com.edu.services.parsing.DiffResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComparingService {

    char TYPE_EQUAL = '=';
    char TYPE_ADD = '+';
    char TYPE_DELETE = '-';

    List<DiffResult> getDifferences(List<String> a, List<String> b);

}
