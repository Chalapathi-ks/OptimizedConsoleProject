package lib.api;

import java.io.InputStream;
import java.util.Map;

public class UnbxdResponse {



    private int statusCode;
    private String responseBody;
    private InputStream responseAsStream;
    private String statusLine;
    private Map<String, String> cookies;
    private Map<String, String> headers;


    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public InputStream getResponseAsStream() {
        return responseAsStream;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }


    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setResponseAsStream(InputStream responseAsStream) {
        this.responseAsStream = responseAsStream;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
