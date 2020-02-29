package com.wuhulala.sda.aggreate;

import com.wuhulala.sda.model.DomainWordResult;

import java.util.List;

public interface DomainWordAggregator {

    List<DomainWordResult> aggregate() throws Exception;

}
