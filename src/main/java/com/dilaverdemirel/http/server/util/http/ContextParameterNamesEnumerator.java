package com.dilaverdemirel.http.server.util.http;

import com.dilaverdemirel.http.server.application.webxml.ContextParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author dilaverd on 7/17/2017.
 */
public class ContextParameterNamesEnumerator extends StringCollectionEnumerator {

    public ContextParameterNamesEnumerator(Set<ContextParameter> contextParameters) {
        List<String> headerNames = new ArrayList<>();
        contextParameters.forEach(header -> {
            headerNames.add(header.getParamName());
        });
        super.setIterator(headerNames.iterator());
    }
}
