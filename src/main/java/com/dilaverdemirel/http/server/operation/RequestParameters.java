package com.dilaverdemirel.http.server.operation;

import java.util.*;

/**
 * @author dilaverd on 7/24/2017.
 */
public class RequestParameters {
    private Map<String,ArrayList<String>> parameters;

    private Map<String,String[]> parameterMap;

    public Map<String, ArrayList<String>> getParameters() {
        return parameters;
    }

    public String getRequestParameter(String name) {
        ArrayList<String> parameters = this.parameters.get(name);
        if(parameters != null) {
            return parameters.get(0);
        } else {
            return null;
        }
    }

    public Enumeration getParameterNames() {
        return Collections.enumeration(parameters.keySet());
    }

    public void setParameters(Map<String, ArrayList<String>> parameters) {
        this.parameters = parameters;
    }

    public String[] getParameterValues(String name) {
        ArrayList<String> values = parameters.get(name);
        if (values == null) {
            return null;
        }
        return values.toArray(new String[values.size()]);
    }

    public Map<String, String[]> getParameterMap() {
        if(parameterMap == null){
            parameterMap = new LinkedHashMap<>();

            Enumeration<String> enumeration = getParameterNames();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement();
                String[] values = getParameterValues(name);
                parameterMap.put(name, values);
            }
        }

        return parameterMap;

    }
}
