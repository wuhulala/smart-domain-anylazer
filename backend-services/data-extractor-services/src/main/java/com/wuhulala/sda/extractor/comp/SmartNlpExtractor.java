package com.wuhulala.sda.extractor.comp;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.wuhulala.sda.model.DomainContent;
import com.wuhulala.sda.model.DomainContentWord;
import com.wuhulala.sda.model.DomainContentWord.WordTerm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class SmartNlpExtractor implements SmartExtractor {
    @Override
    public DomainContentWord extractWord(DomainContent data) {

        long start = System.currentTimeMillis();

        List<WordTerm> titleTermList = StandardTokenizer.segment(data.getTitle())
                .stream()
                .map(term -> WordTerm.of(term.word, term.nature.toString()))
                .collect(Collectors.toList());

        Map<String, Integer> titleWf = new HashMap<>();
        titleTermList.stream().filter(NounPredicate.getInstance()).forEach(term -> {
            titleWf.merge(term.getWord(), 1, Integer::sum);
        });

        Map<String, Integer> contentWf = new HashMap<>();
        List<WordTerm> contentTermList = StandardTokenizer.segment(data.getContent())
                .stream()
                .map(term -> WordTerm.of(term.word, term.nature.toString()))
                .collect(Collectors.toList());
        ;
        contentTermList.stream().filter(NounPredicate.getInstance()).forEach(term -> {
            contentWf.merge(term.getWord(), 1, Integer::sum);
        });

        long end = System.currentTimeMillis();
        log.info(">>>>>>>>> 抽取#{} 共使用了 {}ms.", data.getTitle(), end - start);
        return DomainContentWord.builder()
                .domainId(data.getDomainId())
                .domainName(data.getDomainName())
                .domainContentId(data.getId())
                .title(data.getTitle())
                .titleTermList(titleTermList)
                .titleWordFrequency(titleWf)
                .contentWordTermList(contentTermList)
                .contentWordFrequency(contentWf)
                .creator("SmartNlpExtractor")
                .gmtCreate(new Date())
                .build();
    }

    /**
     * 名词过滤
     */
    public static class NounPredicate implements Predicate<WordTerm> {

        public static NounPredicate getInstance() {
            return new NounPredicate();
        }

        @Override
        public boolean test(WordTerm term) {
            return term.getNature() != null && term.getNature().startsWith("n");
        }
    }

}
