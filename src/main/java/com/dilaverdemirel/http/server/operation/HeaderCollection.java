package com.dilaverdemirel.http.server.operation;

import com.dilaverdemirel.http.server.constant.ConstantOfHeader;

import java.util.*;

/**
 * @author dilaverd on 7/12/2017.
 */
public class HeaderCollection {
    private Set<Header> headers = new HashSet<>();
    private List<RequestParameter> requestParameterTempList = new ArrayList<>();
    private Map<String,ArrayList<String>> requestParameters = new LinkedHashMap<>();

    public void addHeader(Header header){
        headers.add(header);
    }

    public Header findHeader(String name){
        return headers.stream()
               .filter(item -> item.getName().equalsIgnoreCase(name))
               .findFirst()
               .orElse(null);
    }

    public boolean contains(String name){
        return findHeader(name) != null;
    }

    public void parseHeaders(String requestString){
        char headerSeperator = ':';

        String[] lines = requestString.toString().split("\r\n");

        if(lines != null && lines.length > 0) {
            parseRequestHeaders(lines[0]);

            boolean requestParameterLine = false;
            for (String line : lines) {
                int seperatorPos = line.indexOf(headerSeperator);
                if (seperatorPos != -1) {
                    String name = line.substring(0, seperatorPos);
                    String value = line.substring(seperatorPos + 1, line.length());
                    addHeader(new Header(name, value));
                }

                if(line == null || (line != null && line.equals(""))){
                    requestParameterLine = true;
                    continue;
                }

                if(requestParameterLine){
                    String[] splittendRequestParameters = line.split("&");
                    if(splittendRequestParameters!= null){
                        for (String splittendRequestParameter : splittendRequestParameters) {
                            String pair[] = splittendRequestParameter.split("=");
                            if(pair != null && pair.length>=2) {
                                requestParameterTempList.add(new RequestParameter(pair[0], pair[1]));
                            } else if(pair.length == 1){
                                requestParameterTempList.add(new RequestParameter(pair[0], null));
                            }
                        }
                    }
                }
            }

            prepareRequestParameters();
        }
    }

    private void prepareRequestParameters() {
        requestParameterTempList.forEach( item -> {
            ArrayList<String> param = requestParameters.get(item.getName());
            if(param == null){
                ArrayList<String> strings = new ArrayList<>();
                strings.add((String) item.getValue());
                requestParameters.put(item.getName(), strings);
            } else {
                param.add((String) item.getValue());
            }
        });
    }

    private void parseRequestHeaders(String line) {
        String[] infos = line.split(" ");
        if(infos != null && infos.length >= 3) {
            addHeader(new Header(ConstantOfHeader.METHOD, infos[0]));
            addHeader(new Header(ConstantOfHeader.URI, infos[1]));
            if (infos[1] != null && infos[1].indexOf("?") != -1) {
                String[] queryString = infos[1].split("\\?");
                addHeader(new Header(ConstantOfHeader.QUERY_STRING, queryString[1]));
            } else {
                addHeader(new Header(ConstantOfHeader.QUERY_STRING, null));
            }
            addHeader(new Header(ConstantOfHeader.PROTOCOL, infos[2]));
        }
    }

    public Set<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(Set<Header> headers) {
        this.headers = headers;
    }

    public Map<String, ArrayList<String>> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(Map<String, ArrayList<String>> requestParameters) {
        this.requestParameters = requestParameters;
    }
}
