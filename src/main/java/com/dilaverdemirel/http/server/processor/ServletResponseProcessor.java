package com.dilaverdemirel.http.server.processor;

import com.dilaverdemirel.http.server.constant.ConstantOfHeader;
import com.dilaverdemirel.http.server.constant.Environment;
import com.dilaverdemirel.http.server.operation.Header;
import com.dilaverdemirel.http.server.operation.Response;
import com.dilaverdemirel.http.server.servlet.ServletMethods;
import com.dilaverdemirel.http.server.servlet.ServletRequest;
import com.dilaverdemirel.http.server.servlet.ServletResponse;
import com.dilaverdemirel.http.server.servlet.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;

/**
 * @author dilaverd on 7/14/2017.
 */
public class ServletResponseProcessor implements ResponseProcessor {
    private static final Log logger = LogFactory.getLog(Response.class);

    @Override
    public OutputStream createResponse(Response response) {
        ServletRequest servletRequest = new ServletRequest();
        servletRequest.setRequest(response.getRequest());

        ServletResponse servletResponse = new ServletResponse();
        servletResponse.setRequest(response.getRequest());
        servletResponse.setSimpleResponse(response);

        response.getHeaderCollection().addHeader(new Header(ConstantOfHeader.X_POWERED_BY, Environment.POWERED_BY));

        String servletClass = response.getRequest().getServletDefinition().getServletClass();

        try {
            Class<?> clazz = response.getRequest().getApplicationContext().getClassLoader().loadClass(servletClass);
            Class[] argTypes = {HttpServletRequest.class, HttpServletResponse.class};
            String methodName = ServletMethods.valueOf(servletRequest.getMethod()).getMethodName();
            Method method = clazz.getDeclaredMethod(methodName, argTypes);
            method.invoke(clazz.newInstance(),servletRequest,servletResponse);

            addSessionCookie(servletRequest, servletResponse);

            servletResponse.getSimpleResponse().getCookies().forEach((name,cookieStr) ->{
                servletResponse.addHeader(ConstantOfHeader.SET_COOKIE, cookieStr);
            });

        }catch (Exception e){
            logger.error(e.getMessage(),e);
            response.setStatus(500);
        }

        return response.getOutput();
    }

    private void addSessionCookie(ServletRequest servletRequest, ServletResponse servletResponse) {
        Session session = (Session)servletRequest.getSession();
        if(session != null){
            Cookie cookie = new Cookie(ConstantOfHeader.DEFAULT_SESSION_COOKIE_NAME, session.getId());
            servletResponse.addCookie(cookie);
            session.setNew(false);
        }
    }
}
