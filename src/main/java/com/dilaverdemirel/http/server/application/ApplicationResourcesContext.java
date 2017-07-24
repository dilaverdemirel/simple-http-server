package com.dilaverdemirel.http.server.application;

import com.dilaverdemirel.http.server.util.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author dilaverd on 7/20/2017.
 */
public class ApplicationResourcesContext {
    public String docRoot;
    private List<ApplicationResource> resourceList = new ArrayList<>();

    public ApplicationResourcesContext(String docRoot) {
        this.docRoot = docRoot;
    }

    public void init() throws IOException {
        prepareResources();
    }

    private void prepareResources() throws IOException {
        Files.find(Paths.get(this.docRoot),
                Integer.MAX_VALUE,
                (filePath, fileAttr) -> {
                    if (filePath.toString().indexOf("WEB-INF") != -1) {
                        return false;
                    }
                    if (filePath.toString().equalsIgnoreCase(this.docRoot)) {
                        return false;
                    }
                    return fileAttr.isRegularFile() || fileAttr.isDirectory();
                })
                .forEach(path -> {
                    ApplicationResource resource = new ApplicationResource();
                    File tempFile = path.toFile();
                    resource.setName(tempFile.getName());
                    resource.setFile(tempFile.isFile());
                    resource.setParentPath(StringUtils.concat(path.getParent().toString(), File.separator));
                    resource.setFullPath(path.toString());
                    String strPath = path.toString().replace(this.docRoot, "");
                    if (tempFile.isDirectory()) {
                        strPath = StringUtils.concat(strPath, File.separator);
                    }
                    resource.setPath(strPath);
                    resourceList.add(resource);
                });
    }

    public Set<String> getResourcePaths(String path){
        Set<String> resourcesSet = new HashSet<>();
        resourceList.forEach(resource ->{
            boolean add = false;
            if((path.length() == 1 && path.charAt(0) == File.separatorChar && StringUtils.concat(this.docRoot,path).equals(resource.getParentPath()))){
                add = true;
            } else if (path.length() != 1 && StringUtils.concat(this.docRoot,path,File.separator).equals(resource.getParentPath())){
                add = true;
            }
            if(add) {
                resourcesSet.add(resource.getPath());
            }
        });

        return resourcesSet;
    }

    public URL getResource(String path) throws MalformedURLException {
        ApplicationResource applicationResource = findResource(path);
        if(applicationResource.getPath() != null){
            return new URL(null,null,-1,applicationResource.getFullPath());
        }
        return null;
    }

    public ApplicationResource findResource(String path) {
        return resourceList.stream()
                            .filter(res -> res.getPath().equals(path))
                            .findFirst()
                            .orElse(new ApplicationResource());
    }

    public InputStream getResourceAsStream(String path) throws FileNotFoundException {
        ApplicationResource resource = findResource(path);

        if(resource.getPath() != null){
            return new FileInputStream(resource.getFullPath());
        }

        return null;
    }
}
