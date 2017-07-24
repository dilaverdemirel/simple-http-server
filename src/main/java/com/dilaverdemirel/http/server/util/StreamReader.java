package com.dilaverdemirel.http.server.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author dilaverd on 7/13/2017.
 */
public class StreamReader {
    private InputStream input;
    private StringBuffer stringBuffer;

    public StreamReader(InputStream input, StringBuffer stringBuffer) {
        this.input = input;
        this.stringBuffer = stringBuffer;
    }

    public void readRequest() {
        int i;
        byte[] buffer = new byte[2048];
        try {
            // Read the data coming in on the socket into a buffer
            i = input.read(buffer);
            for (int j = 0; j < i; j++) {
                stringBuffer.append((char) buffer[j]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
    }
}
