package com.dilaverdemirel.http.server.util.http;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author dilaverd on 7/17/2017.
 */
public class StringCollectionEnumerator implements Enumeration<String> {

    private String name;
    private Iterator<String> iterator;

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public String nextElement() {
        return iterator.next();
    }

    public Iterator<String> getIterator() {
        return iterator;
    }

    public void setIterator(Iterator<String> iterator) {
        this.iterator = iterator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
