package lib.api;

public enum ContentType {


    APPLICATION_JSON("application/json"),
    FORM_URL_ENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    TEXT_HTML("text/html");
    private final String contentType;

    ContentType(String contentType){
        this.contentType = contentType;
    }

    public String toString(){
        return contentType;
    }
}
