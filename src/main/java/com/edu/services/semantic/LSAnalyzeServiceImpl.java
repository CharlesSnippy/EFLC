package com.edu.services.semantic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LSAnalyzeServiceImpl implements SemanticService {

    private static final Pattern PERFECTIVEGROUND = Pattern.compile("((ив|ивши|ившись|ыв|ывши|ывшись)|((?<=[ая])(в|вши|вшись)))$");
    private static final Pattern REFLEXIVE = Pattern.compile("(с[яь])$");
    private static final Pattern ADJECTIVE = Pattern.compile("(ее|ие|ые|ое|ими|ыми|ей|ий|ый|ой|ем|им|ым|ом|его|ого|ему|ому|их|ых|ую|юю|ая|яя|ою|ею)$");
    private static final Pattern PARTICIPLE = Pattern.compile("((ивш|ывш|ующ)|((?<=[ая])(ем|нн|вш|ющ|щ)))$");
    private static final Pattern VERB = Pattern.compile("((ила|ыла|ена|ейте|уйте|ите|или|ыли|ей|уй|ил|ыл|им|ым|ен|ило|ыло|ено|ят|ует|уют|ит|ыт|ены|ить|ыть|ишь|ую|ю)|((?<=[ая])(ла|на|ете|йте|ли|й|л|ем|н|ло|но|ет|ют|ны|ть|ешь|нно)))$");
    private static final Pattern NOUN = Pattern.compile("(а|ев|ов|ие|ье|е|иями|ями|ами|еи|ии|и|ией|ей|ой|ий|й|иям|ям|ием|ем|ам|ом|о|у|ах|иях|ях|ы|ь|ию|ью|ю|ия|ья|я)$");
    private static final Pattern RVRE = Pattern.compile("^(.*?[аеиоуыэюя])(.*)$");
    private static final Pattern DERIVATIONAL = Pattern.compile(".*[^аеиоуыэюя]+[аеиоуыэюя].*ость?$");
    private static final Pattern DER = Pattern.compile("ость?$");
    private static final Pattern SUPERLATIVE = Pattern.compile("(ейше|ейш)$");
    private static final Pattern I = Pattern.compile("и$");
    private static final Pattern P = Pattern.compile("ь$");
    private static final Pattern NN = Pattern.compile("нн$");
    private static final List<String> STOP_WORDS = Arrays.asList("и, в, во, не, что , он , на, я, с, со, как, а, то, все, она, так, его, но, да, ты, к, у, же, вы, за, бы, по, только, ее, мне, было, вот, от, меня, еще, нет, о, из, ему, теперь, когда, даже, ну, вдруг, ли, если, уже, или, ни, быть, был, него, до, вас, нибудь, опять, уж, вам, сказал, ведь, там, потом, себя, ничего, ей, может, они, тут, где, есть, надо, ней, для, мы, тебя, их, чем, была, сам, чтоб, без, будто, человек, чего, раз, тоже, себе, под, жизнь, будет, ж, тогда, кто, этот, говорил, того, потому, этого, какой, совсем, ним, здесь, этом, один, почти, мой, тем, чтобы, нее, кажется, сейчас, были, куда, зачем, сказать, всех, никогда, сегодня, можно, при, наконец, два, об, другой, хоть, после, над, больше, тот, через, эти, нас, про, всего, них, какая, много, разве, сказала, три, эту, моя, впрочем, хорошо, свою, этой, перед, иногда, лучше, чуть, том, нельзя, такой, им, более, всегда, конечно, всю, между".split(", "));
    private static final List<String> SYMBOLS = Arrays.asList("-, :, ;, ".split(", "));
    static final Logger logger = LogManager.getLogger(LSAnalyzeServiceImpl.class);

    @Override
    public String stemWord(String word) {
        logger.info("stemWord({})", word);
        word = word.toLowerCase();
        word = word.replace('ё', 'е');
        Matcher m = RVRE.matcher(word);
        if (m.matches()) {
            String pre = m.group(1);
            String rv = m.group(2);
            String temp = PERFECTIVEGROUND.matcher(rv).replaceFirst("");
            if (temp.equals(rv)) {
                rv = REFLEXIVE.matcher(rv).replaceFirst("");
                temp = ADJECTIVE.matcher(rv).replaceFirst("");
                if (!temp.equals(rv)) {
                    rv = temp;
                    rv = PARTICIPLE.matcher(rv).replaceFirst("");
                } else {
                    temp = VERB.matcher(rv).replaceFirst("");
                    if (temp.equals(rv)) {
                        rv = NOUN.matcher(rv).replaceFirst("");
                    } else {
                        rv = temp;
                    }
                }
            } else {
                rv = temp;
            }
            rv = I.matcher(rv).replaceFirst("");
            if (DERIVATIONAL.matcher(rv).matches()) {
                rv = DER.matcher(rv).replaceFirst("");
            }
            temp = P.matcher(rv).replaceFirst("");
            if (temp.equals(rv)) {
                rv = SUPERLATIVE.matcher(rv).replaceFirst("");
                rv = NN.matcher(rv).replaceFirst("н");
            } else {
                rv = temp;
            }
            word = pre + rv;
        }
        return word;
    }

    @Override
    public List<String> getStopWords() {
        return STOP_WORDS;
    }

    @Override
    public String removeSymbols(String word) {
        return null;
    }

    @Override
    public LSAResult analyze(List<String> documents) {
        LSAResult lsaResult = new LSAResult();
        documents = formatDocuments(documents);
        lsaResult.setDocuments(documents);
        List<String> words = getNotUniqueWords(documents);
        lsaResult.setWords(words);
        lsaResult.setFrequencyMatrix(calculateFrequencyMatrix(documents, words));
        return lsaResult;
    }

    private double[][] calculateFrequencyMatrix(List<String> documents, List<String> words) {
        double[][] result = new double[words.size()][documents.size()];
        int col = 0;
        for (String document :
                documents) {
            int row = 0;
            for (String word :
                    words) {
                for (String docWord :
                        document.split(" ")) {
                    if(word.equals(docWord)) {
                        result[row][col]++;
                    }
                }
                row++;
            }
            col++;
        }
        return result;
    }

    private List<String> formatDocuments(List<String> documents) {
        List<String> formattedDocuments = new ArrayList<>();
        for (String document :
                documents) {
            String formattedDocument = document.replaceAll("[^а-яА-Я ]", "");
            StringBuilder stringBuilder = new StringBuilder();
            for (String word :
                    formattedDocument.split(" ")) {
                String stemWord = stemWord(word);
                if(!STOP_WORDS.contains(stemWord) && !stemWord.equals("")) {
                    stringBuilder.append(stemWord);
                    stringBuilder.append(" ");
                }
            }
            formattedDocument = stringBuilder.toString().replaceAll(" $","");
            formattedDocuments.add(formattedDocument);
        }
        return formattedDocuments;
    }

    private List<String> getNotUniqueWords(List<String> documents) {
        List<String> words = new ArrayList<>();
//        for (String testingDocument :
//                documents) {
//            for (String testingWord :
//                    testingDocument.split(" ")) {
//                boolean toAdd = false;
//                for (String container :
//                        documents) {
//                    if (!testingDocument.equals(container) && container.contains(testingWord)) {
//                        toAdd = true;
//                    }
//                }
//                if (toAdd && !words.contains(testingWord)) {
//                    words.add(testingWord);
//                }
//            }
//        }
        for (String testingDocument :
                documents) {
            for (String testingWord :
                    testingDocument.split(" ")) {
                if (!words.contains(testingWord)) {
                    words.add(testingWord);
                }
            }
        }
        return words;
    }

}
