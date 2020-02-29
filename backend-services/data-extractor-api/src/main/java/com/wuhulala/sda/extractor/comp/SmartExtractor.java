package com.wuhulala.sda.extractor.comp;

import com.wuhulala.sda.model.DomainContent;
import com.wuhulala.sda.model.DomainContentWord;

public interface SmartExtractor {

    DomainContentWord extractWord(DomainContent data);

}
