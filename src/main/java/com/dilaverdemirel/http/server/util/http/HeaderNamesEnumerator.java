package com.dilaverdemirel.http.server.util.http;

import com.dilaverdemirel.http.server.operation.HeaderCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dilaverd on 7/17/2017.
 */
public class HeaderNamesEnumerator extends StringCollectionEnumerator {

    public HeaderNamesEnumerator(HeaderCollection collection) {
        List<String> headerNames = new ArrayList<>();
        collection.getHeaders().forEach(header ->{
            headerNames.add(header.getName());
        });
        super.setIterator(headerNames.iterator());
    }
}
