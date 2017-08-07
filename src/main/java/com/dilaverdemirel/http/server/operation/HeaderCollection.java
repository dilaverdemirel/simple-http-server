package com.dilaverdemirel.http.server.operation;

import com.dilaverdemirel.http.server.constant.ConstantOfHeader;
import com.dilaverdemirel.http.server.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dilaverd on 7/12/2017.
 */
public class HeaderCollection {
    private List<Header> headers = new ArrayList<>();
    private List<RequestParameter> requestParameterTempList = new ArrayList<>();
    private Map<String, ArrayList<String>> requestParameters = new LinkedHashMap<>();

    public void addHeader(Header header) {
        headers.add(header);
    }

    public Header findHeader(String name) {
        return headers.stream()
                .filter(item -> item.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public boolean contains(String name) {
        return findHeader(name) != null;
    }

    public void parseHeaders(String requestString) {
        char headerSeperator = ':';

        String[] lines = requestString.split("\r\n");

        if (lines.length > 0) {
            parseRequestHeaders(lines[0]);

            boolean requestParameterLine = false;
            for (String line : lines) {
                int seperatorPos = line.indexOf(headerSeperator);
                if (seperatorPos != -1) {
                    String name = line.substring(0, seperatorPos);
                    String value = line.substring(seperatorPos + 1, line.length());
                    addHeader(new Header(name, value));
                }

                if (line.equals("")) {
                    requestParameterLine = true;
                    continue;
                }

                //Detect parameters in request string(POST)
                if (requestParameterLine) {
                    prepareRequestParametersHeader(line);
                }
            }

            prepareRequestParameters();
        }
    }

    private void prepareRequestParameters() {
        Header params = findHeader(ConstantOfHeader.REQUEST_PARAMETERS);
        if (params != null && params.getValue() != null) {
            String[] splittendRequestParameters = params.getValue().split("&");

            for (String splittendRequestParameter : splittendRequestParameters) {
                String pair[] = splittendRequestParameter.split("=");
                if (pair.length >= 2) {
                    requestParameterTempList.add(new RequestParameter(pair[0], pair[1]));
                } else if (pair.length == 1) {
                    requestParameterTempList.add(new RequestParameter(pair[0], null));
                }
            }

            requestParameterTempList.forEach(item -> {
                ArrayList<String> param = requestParameters.get(item.getName());
                if (param == null) {
                    ArrayList<String> strings = new ArrayList<>();
                    strings.add((String) item.getValue());
                    requestParameters.put(item.getName(), strings);
                } else {
                    param.add((String) item.getValue());
                }
            });
        }
    }

    private void parseRequestHeaders(String line) {
        String[] infos = line.split(" ");
        if (infos.length >= 3) {
            addHeader(new Header(ConstantOfHeader.METHOD, infos[0]));
            addHeader(new Header(ConstantOfHeader.URI, infos[1]));

            //Detect parameters in request string(GET)
            if (infos[1] != null && infos[1].contains("?")) {
                String[] queryString = infos[1].split("\\?");
                //Add QueryString and RequestParameters data, parse
                if (queryString.length >= 2) {
                    prepareRequestParametersHeader(queryString[1]);
                    addHeader(new Header(ConstantOfHeader.QUERY_STRING, queryString[1]));
                }
            } else {
                addHeader(new Header(ConstantOfHeader.QUERY_STRING, null));
            }
            addHeader(new Header(ConstantOfHeader.PROTOCOL, infos[2]));
        }
    }

    private void prepareRequestParametersHeader(String value) {
        Header header = findHeader(ConstantOfHeader.REQUEST_PARAMETERS);
        if (header == null) {
            addHeader(new Header(ConstantOfHeader.REQUEST_PARAMETERS, value));
        } else {
            getHeaders().remove(getHeaders().indexOf(header));
            String newString = StringUtils.concat(header.getValue(), "&", value);
            addHeader(new Header(ConstantOfHeader.REQUEST_PARAMETERS, newString));
        }
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public Map<String, ArrayList<String>> getRequestParameters() {
        return requestParameters;
    }
}
