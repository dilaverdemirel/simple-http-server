# simple-http-server

Project is a simple servlet container application.

### Features
  - Easy to understand
  - Supports servlet 2.5 standard
  - Configurable thread pool
  - Supports static resources
  - Supports HTTP Cookies

### Tech
  - Java 8
  - commons-logging

### TODO
  - Session Support
  - Servlet Filter Support
  - Servlet Listener Support

### How To Use
```sh
$ git clone [git-repo-url] simple-http-server
$ mvn clean install
```
#### Maven
```xml
<dependency>
    <groupId>com.dilaverdemirel</groupId>
    <artifactId>simple-http-server</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

#### Project Structure
>src
-----main
---------java
------------com.dilaverdemirel.servlet.TestServlet
------------com.dilaverdemirel.SimpleServer
----resources
--------simple-http-server.properties
----webapp
--------WEB-INF
------------web.xml
--------index.html

#### Starter
```java
public class SimpleServer {
    public static void main(String[] args) throws MalformedURLException {
        Container.newInstance();
    }
}
```

#### web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         id="WebApp_ID" version="2.5">
    <display-name>Simple Project</display-name>

    <servlet>
        <servlet-name>TestServlet</servlet-name>
        <servlet-class>com.dilaverdemirel.servlet.TestServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>TestServlet</servlet-name>
        <url-pattern>/test/testServlet</url-pattern>
    </servlet-mapping>
</web-app>
```
#### simple-http-server.properties
```properties 
doc-root=${project.build.directory}\\simple-http-server-doc-root-1.0-SNAPSHOT
port=9090
thread-count=3
```

#### pom.xml
```xml
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```