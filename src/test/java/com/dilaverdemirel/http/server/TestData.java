package com.dilaverdemirel.http.server;

import com.dilaverdemirel.http.server.config.ApplicationContext;
import com.dilaverdemirel.http.server.util.StreamUtils;

import java.net.MalformedURLException;

/**
 * @author dilaverd on 7/12/2017.
 */
public class TestData {
    public static String contextPath = "test-app";
    public static String requestHeaderString =
            "GET /test-app/listsourcefiles?file=repository.springsource.com@org.apache.coyote$com.springsource.org.apache.coyote@6.0.24&path=/org/ HTTP/1.1\r\n" +
                    "Host: grepcode.com\r\n" +
                    "Connection: keep-alive\r\n" +
                    "X-Requested-With: XMLHttpRequest\r\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36 OPR/46.0.2597.39\r\n" +
                    "Accept: */*\r\n" +
                    "Referer: http://grepcode.com/file/repository.springsource.com/org.apache.coyote/com.springsource.org.apache.coyote/6.0.24/org/apache/tomcat/util/http/MimeHeaders.java\r\n" +
                    "Accept-Encoding: gzip, deflate\r\n" +
                    "Accept-Language: tr-TR,tr;q=0.8,en-US;q=0.6,en;q=0.4\r\n" +
                    "Cookie: __utmt=1; __utma=232318793.1527542697.1495721152.1499781633.1499855865.3; __utmb=232318793.5.10.1499855865; __utmc=232318793; __utmz=232318793.1499781633.2.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided)\r\n" +
                    "\r\n" +
                    "field1=111&field2=222&action=send\n\n";

    public static String testClassesDirectory = StreamUtils.findRootDirectory()+"\\target\\test-data\\test-web-app-root";

    public static ApplicationContext getApplicationContext(String docRoot) throws MalformedURLException {
        ApplicationContext applicationContext = new ApplicationContext(docRoot);
        return  applicationContext;
    }

    public static ApplicationContext getApplicationContext() throws MalformedURLException {
        return  getApplicationContext(testClassesDirectory);
    }
}
