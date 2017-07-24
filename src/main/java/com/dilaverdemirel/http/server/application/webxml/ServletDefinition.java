package com.dilaverdemirel.http.server.application.webxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author dilaverd on 7/14/2017.
 */
@XmlRootElement(name = "servlet")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServletDefinition {
    @XmlElement(name = "servlet-name")
    private String servletName;
    @XmlElement(name = "servlet-class")
    private String servletClass;

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }
}
