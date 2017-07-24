package com.dilaverdemirel.http.server.util.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dilaverd on 7/17/2017.
 */
public class ServletContextHeaderNamesEnumerator extends StringCollectionEnumerator {

    public ServletContextHeaderNamesEnumerator(Map<String,Object> attributes ) {
        List<String> values = new ArrayList<>();
        attributes.forEach( (k,v) -> {
            values.add(k);
        });
        super.setName(null);
        super.setIterator(values.iterator());
    }
}
