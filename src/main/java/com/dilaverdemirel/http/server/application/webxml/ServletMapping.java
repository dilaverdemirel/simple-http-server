package com.dilaverdemirel.http.server.application.webxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author dilaverd on 7/14/2017.
 */
@XmlRootElement(name = "servlet-mapping")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServletMapping {
    @XmlElement(name = "servlet-name")
    private String servletName;
    @XmlElement(name = "url-pattern")
    private String urlPattern;

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }
}
