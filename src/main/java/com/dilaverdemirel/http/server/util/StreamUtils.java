package com.dilaverdemirel.http.server.util;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.regex.Pattern;

/**
 * @author dilaverd on 7/13/2017.
 */
public class StreamUtils {
    public static synchronized void writeDataToOutputStream(OutputStream output, String data) throws IOException {
        if(data != null) {
            output.write(data.getBytes());
        }
    }

    public static String getContentMimeType(String filename){
        String contentType = URLConnection.guessContentTypeFromName(filename);
        if( contentType == null){
            contentType = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(filename);
        }
        return contentType;
    }

    /**
     *
     * @param classLoader getClass().getClassLoader()
     * @param fileName Test resources icin; test-data/data.xml
     * @return
     */
    public static File loadFileFromResources(ClassLoader classLoader,String fileName){
        return new File(classLoader.getResource(fileName).getFile());
    }

    public static String findRootDirectory(){
        String moduleName = "simple-http-server";
        String temp = System.getProperty("user.dir");
        String[] directories = temp.split(Pattern.quote(File.separator));
        String pathname = null;
        for (int i = 0; i<directories.length;i++){
            pathname = preparePath(directories, i) + File.separator + moduleName;
            if(new File(pathname).exists()){
                break;
            }
        }

        return pathname;
    }

    private static String preparePath(String[] directories,int parentIntex){
        String path = "";
        for(int i = 0;i < directories.length - parentIntex;i++){
            if(path != null && !path.equals("")) {
                path += File.separator;
            }

            path += directories[i];
        }

        return path;
    }
}
