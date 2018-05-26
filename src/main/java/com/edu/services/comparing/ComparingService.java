package com.edu.services.comparing;

import com.edu.services.parsing.DiffResult;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComparingService {

    char TYPE_EQUAL = '=';
    char TYPE_ADD = '+';
    char TYPE_DELETE = '-';

    /**
     * Get differences between two string arrays
     *
     * @param a first array
     * @param b second array
     * @return data for table as DiffResult
     */
    List<DiffResult> getDifferences(List<String> a, List<String> b);

    /**
     * Get difference between two Documents
     *
     * @param a first Document
     * @param b second Document
     * @return data for table as DiffResult
     */
    List<DiffResult> getDifferences(Document a, Document b);

}
