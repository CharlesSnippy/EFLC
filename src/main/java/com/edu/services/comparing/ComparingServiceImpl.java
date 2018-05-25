package com.edu.services.comparing;

import com.edu.services.parsing.DiffResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComparingServiceImpl implements ComparingService{

    static final Logger logger = LogManager.getLogger(ComparingServiceImpl.class);

    @Override
    public List<DiffResult> getDifferences(List<String> a, List<String> b) {
        logger.info("getDifferences:");
        List<DiffResult> results = new ArrayList<>();
        int[][] m = prepareMatrix(a, b);
        getDiff(m, a, b, b.size(), a.size(), results);
        return results;
    }

    private int[][] prepareMatrix(List<String> a, List<String> b) {
        int[][] m = new int[a.size() + 1][b.size() + 1];

        for (int y = 0; y < m.length; y++) {
            for (int x = 0; x < m[y].length; x++) {
                m[y][x] = 0;
            }
        }

        for (int y = 1; y < m.length; y++) {
            for (int x = 1; x < m[y].length; x++) {
                if (a.get(y - 1).equals(b.get(x - 1))) {
                    m[y][x] = 1 + m[y - 1][x - 1];
                } else {
                    m[y][x] = Math.max(m[y - 1][x], m[y][x - 1]);
                }
            }
        }
        return m;
    }

    private void getDiff(int[][] m, List<String> a, List<String> b, int x, int y, List<DiffResult> result) {
        if ((x > 0) && (y > 0) && (a.get(y - 1).equals(b.get(x - 1)))) {
            getDiff(m, a, b, x - 1, y - 1, result);
            DiffResult diffResult = new DiffResult();
            diffResult.setFirstIndex(String.valueOf(x));
            diffResult.setSecondIndex(String.valueOf(y));
            diffResult.setRow(a.get(y - 1));
            diffResult.setType(TYPE_EQUAL);
            result.add(diffResult);
        } else {
            if ((x > 0) && ((y == 0) || (m[y][x - 1] >= m[y - 1][x]))) {
                getDiff(m, a, b, x - 1, y, result);
                DiffResult diffResult = new DiffResult();
                diffResult.setFirstIndex(String.valueOf(x));
                diffResult.setSecondIndex("");
                diffResult.setRow(b.get(x - 1));
                diffResult.setType(TYPE_ADD);
                result.add(diffResult);
            } else {
                if ((y > 0) && ((x == 0) || m[y][x - 1] < m[y - 1][x])) {
                    getDiff(m, a, b, x, y - 1, result);
                    DiffResult diffResult = new DiffResult();
                    diffResult.setFirstIndex("");
                    diffResult.setSecondIndex(String.valueOf(y));
                    diffResult.setRow(a.get(y - 1));
                    diffResult.setType(TYPE_DELETE);
                    result.add(diffResult);
                }
            }
        }
    }


}
