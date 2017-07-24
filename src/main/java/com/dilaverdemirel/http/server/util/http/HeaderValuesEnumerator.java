package com.dilaverdemirel.http.server.util.http;

import com.dilaverdemirel.http.server.operation.HeaderCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dilaverd on 7/17/2017.
 */
public class HeaderValuesEnumerator extends StringCollectionEnumerator {

    public HeaderValuesEnumerator(HeaderCollection collection, String name) {
        List<String> headerValues = new ArrayList<>();
        collection.getHeaders().forEach(header ->{
            headerValues.add(header.getValue());
        });
        super.setName(name);
        super.setIterator(headerValues.iterator());
    }
}
