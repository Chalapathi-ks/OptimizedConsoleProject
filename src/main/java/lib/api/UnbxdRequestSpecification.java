package lib.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UnbxdRequestSpecification {


    private String contentType;
    private String baseUrl;
    private String content;
    private Map<String, String> cookies;
    private Map<String, String> headers;
    private Map<String, Object> queryParams;
    private Object contentObj;
    private File file;



    public UnbxdRequestSpecification setContentType(ContentType contentType) {
        this.contentType = contentType.toString();
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public UnbxdRequestSpecification addBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
    
    public String getBaseUrl() {
        return baseUrl;
    }

    public UnbxdRequestSpecification addContent(String content) {
        this.content = content;
        return this;
    }



    public UnbxdRequestSpecification addContent(Object content) {
        this.contentObj = content;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Object getContentObj() {
        return contentObj;
    }

    public UnbxdRequestSpecification addCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public UnbxdRequestSpecification addHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public UnbxdRequestSpecification addQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    public UnbxdRequestSpecification build(){
        return this;
    }

    public UnbxdRequestSpecification addQueryParam(String key, Object value){
        if(this.queryParams == null) {
            queryParams = new HashMap<String, Object>();
        }
        this.queryParams.put(key,value);
        return this;
    }

    public UnbxdRequestSpecification addHeader(String key, String value){
        if(this.headers == null) {
            headers = new HashMap<String, String>();
        }
        this.headers.put(key,value);
        return this;
    }

    public UnbxdRequestSpecification addFile(File file)
    {
        this.file=file;
        return this;
    }

    public File getFile() {
        return file;
    }
}
