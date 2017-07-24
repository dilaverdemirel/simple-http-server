package com.dilaverdemirel.http.server.processor;

import com.dilaverdemirel.http.server.constant.ConstantOfHeader;
import com.dilaverdemirel.http.server.constant.Environment;
import com.dilaverdemirel.http.server.operation.Header;
import com.dilaverdemirel.http.server.operation.Response;
import com.dilaverdemirel.http.server.util.StreamUtils;
import com.dilaverdemirel.http.server.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @author dilaverd on 7/14/2017.
 */
public class StaticResourceProcessor implements ResponseProcessor {
    private static final Log logger = LogFactory.getLog(Response.class);

    @Override
    public OutputStream createResponse(Response response) {
        response.getHeaderCollection().addHeader(new Header(ConstantOfHeader.X_POWERED_BY, Environment.SERVER_NAME));
        try{
            int responsecode = sendStaticResource(response);
            if (responsecode!=0){
                // an error occurred in the resource request, output appropriate error message
                returnError(responsecode,response);
            }
        }
        catch(IOException e){
            logger.error(e.getMessage(),e);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return response.getOutput();
    }

    private int sendStaticResource(Response response) throws IOException{
        // Set up the standard return code, 0 if processed OK.
        // Any other value means an error has occurred.
        int returncode = 0;

        try{
            String fileName = determineFileName(response);
            response.setContentType(StreamUtils.getContentMimeType(fileName));
            File requestedfile  = new File(response.getRequest().getApplicationContext().getDocRoot(), fileName);

            if (requestedfile.exists()){
                response.getOutput().write(Files.readAllBytes(requestedfile.toPath()));

            }else{
                // file not found
                returncode = 404;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }


        return returncode;
    }

    private String determineFileName(Response response) {
        String fileName = "";
        if(response.getRequest().getURI().indexOf("?")!= -1){
            String[] parsedURI = response.getRequest().getURI().split("\\?");
            fileName = parsedURI[0];
        } else {
            fileName = response.getRequest().getURI();
        }
        return fileName;
    }

    private void returnError(int errornum,Response response) throws Exception{
        String errorNumber;
        String errorDetail;
        switch (errornum){
            case 404:
                errorNumber = "HTTP/1.1 404 File Not Found\r\n";
                errorDetail = "<h1>SimpleHttpServer is reporting an error with your request.</h1><h2>Error 404 File Not Found.</h2>";
                break;
            case 501:
                errorNumber = "HTTP/1.1 501 Method Not Supported\r\n";
                errorDetail = "<h1>SimpleHttpServer is reporting an error with your request..</h1><h2>Error 501 - Requested Method is not supported by this HTTPServer.</h2>";
                break;
            default:
                errorNumber = "HTTP/1.1 Unknown Error Number\r\n";
                errorDetail = "<h1>SimpleHttpServer is reporting an error with your request.</h1><h2>Sorry, Server has encountered an unexpected error.</h2>";
                break;
        }
        String errorMessage = StringUtils.concat(String.valueOf(errorNumber),
                "Content-Type: text/html\r\n",
                "Content-Length: ",
                String.valueOf(errorDetail.length()),
                "\r\n",
                errorDetail);
        response.getOutput().write(errorMessage.getBytes());
    }
}
