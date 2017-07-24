package com.dilaverdemirel.http.server.application;

/**
 * @author dilaverd on 7/20/2017.
 */
public class ApplicationResource {
    private boolean file;
    private String path;
    private String fullPath;
    private String parentPath;
    private String name;

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "AppResource{" +
                "file=" + file +
                ", path='" + path + '\'' +
                ", fullPath='" + fullPath + '\'' +
                ", parentPath='" + parentPath + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
