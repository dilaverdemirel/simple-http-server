package com.dilaverdemirel.http.server.operation;

import com.dilaverdemirel.http.server.TestData;
import com.dilaverdemirel.http.server.constant.ConstantOfHeader;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author dilaverd on 7/12/2017.
 */

public class HeaderCollectionTest {

    private HeaderCollection headerCollection;

    @Test
    public void testFindHeader() {
        headerCollection = new HeaderCollection();
        headerCollection.addHeader(new Header("name", "value"));
        headerCollection.addHeader(new Header("header", "val"));

        Header header = headerCollection.findHeader("header");
        Assert.assertNotNull("Header not found!", header);
    }

    @Test
    public void testFindHeaderNotFound() {
        headerCollection = new HeaderCollection();
        headerCollection.addHeader(new Header("name", "value"));
        headerCollection.addHeader(new Header("header", "val"));

        Header header = headerCollection.findHeader("test");
        Assert.assertNull("Header found!", header);
    }

    @Test
    public void testParseHeaders() {
        headerCollection = new HeaderCollection();
        headerCollection.parseHeaders(TestData.requestHeaderString);
        Header cookie = headerCollection.findHeader(ConstantOfHeader.COOKIE);
        Header method = headerCollection.findHeader(ConstantOfHeader.METHOD);
        Header uri = headerCollection.findHeader(ConstantOfHeader.URI);
        Header protocol = headerCollection.findHeader(ConstantOfHeader.PROTOCOL);
        Header queryString = headerCollection.findHeader(ConstantOfHeader.QUERY_STRING);
        Header host = headerCollection.findHeader(ConstantOfHeader.HOST);
        Assert.assertNotNull("Cookie not found!", cookie);
        Assert.assertNotNull("Method not found!", method);
        Assert.assertNotNull("URI not found!", uri);
        Assert.assertNotNull("Protocol not found!", protocol);
        Assert.assertNotNull("Query String not found!", queryString);
        Assert.assertNotNull("Host not found!", host);
    }

}
