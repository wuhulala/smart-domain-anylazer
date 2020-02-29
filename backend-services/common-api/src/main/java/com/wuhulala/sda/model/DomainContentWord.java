package com.wuhulala.sda.model;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder()
@NoArgsConstructor
@AllArgsConstructor
@JSONType(naming= PropertyNamingStrategy.SnakeCase)
public class DomainContentWord {

    private String domainId;

    private String domainName;

    private String domainContentId;

    private String title;

    private List<WordTerm> titleTermList;
    private Map<String, Integer> titleWordFrequency;

    private List<WordTerm> contentWordTermList;
    private Map<String, Integer> contentWordFrequency;

    private Date gmtCreate;

    private String creator;

    @Data
    public static class WordTerm {
        private String word;
        private String nature;

        public static WordTerm of(String word, String nature) {
            WordTerm wordTerm = new WordTerm();
            if (word.startsWith("$")){
                word = word.replace("$", "");
            }
            wordTerm.word = word;
            wordTerm.nature = nature;
            return wordTerm;
        }
    }

}
