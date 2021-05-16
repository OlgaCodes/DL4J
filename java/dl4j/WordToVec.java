package dl4j;

import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;


public class WordToVec {

    private Logger log = LoggerFactory.getLogger(WordToVec.class);
    private String word;

    public WordToVec(String word) {
        this.word=word;
    }

    public List<String> search() throws Exception {

        log.info("--Load data--");
        String filePath = new ClassPathResource("titles.txt").getFile().getAbsolutePath();

        SentenceIterator iter = new BasicLineIterator(filePath);
        // SentenceIterator iter = new LineSentenceIterator(new File(""));
        iter.setPreProcessor(new SentencePreProcessor() {
            @Override
            public String preProcess(String sentence) {
                return sentence.toLowerCase();
            }
        });
        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        log.info("---Building model----");
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(1)
                .layerSize(100)
                .seed(42)
                .windowSize(8)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        log.info("Fitting Word2Vec model....");
        vec.fit();


       // WordVectorSerializer.writeWordVectors(vec, "");

        log.info("Closest Words:");
        Collection<String> lst = vec.wordsNearest(word, 10);
        List<String> similarWordsInVocabTo=vec.similarWordsInVocabTo(word, 0.8);
        // System.out.println(lst);
       // System.out.println(similarWordsInVocabTo);

        return similarWordsInVocabTo;
        //  UiServer server = UiServer.getInstance();
        //System.out.println("Started on port " + server.getPort());
    }
}

