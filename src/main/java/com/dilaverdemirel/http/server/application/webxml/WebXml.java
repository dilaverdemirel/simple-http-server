package com.dilaverdemirel.http.server.application.webxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * @author dilaverd on 7/14/2017.
 */
@XmlRootElement(name = "web-app",namespace="http://java.sun.com/xml/ns/javaee")
@XmlAccessorType(XmlAccessType.FIELD)
public class WebXml {
    @XmlElement(name = "servlet")
    private Set<ServletDefinition> servletDefinitions;

    @XmlElement(name = "servlet-mapping")
    private Set<ServletMapping> servletMappings;

    @XmlElement(name = "context-param")
    private Set<ContextParameter> contextParameters;

    public ContextParameter findContextParameter(String name){
        return contextParameters.stream()
                                .filter(param -> param.getParamName().equals(name))
                                .findFirst()
                                .orElse(new ContextParameter());
    }

    public ServletDefinition findServlet(String name){
        return servletDefinitions.stream()
                .filter(param -> param.getServletName().equals(name))
                .findFirst()
                .orElse(new ServletDefinition());
    }

    public ServletDefinition findServletByMapping(String pattern){
        ServletMapping servletMapping = servletMappings.stream()
                .filter(param -> param.getUrlPattern().equals(pattern))
                .findFirst()
                .orElse(null);

        if(servletMapping != null){
            return findServlet(servletMapping.getServletName());
        }

        return null;
    }

    public Set<ServletDefinition> getServletDefinitions() {
        return servletDefinitions;
    }

    public Set<ServletMapping> getServletMappings() {
        return servletMappings;
    }

    public Set<ContextParameter> getContextParameters() {
        return contextParameters;
    }


}
