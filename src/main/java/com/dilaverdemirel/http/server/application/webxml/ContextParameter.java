package com.dilaverdemirel.http.server.application.webxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author dilaverd on 7/14/2017.
 */
@XmlRootElement(name = "context-param")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContextParameter {
    @XmlElement(name = "param-name")
    private String paramName;
    @XmlElement(name = "param-value")
    private String paramValue;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContextParameter that = (ContextParameter) o;

        if (paramName != null ? !paramName.equals(that.paramName) : that.paramName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return paramName != null ? paramName.hashCode() : 0;
    }
}
