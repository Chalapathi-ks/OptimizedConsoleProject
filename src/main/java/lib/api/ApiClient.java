package lib.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    private boolean followRedirect = true;

    public UnbxdResponse executeRequest(HttpMethod requestType, String baseUrl, Map<String, String> cookies, Map<String, String> headers, Map<String, Object> queryParams,
                                        String contentType, String content){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        RequestSpecification requestSpecification=null;

        Response response;
        if(cookies!=null && !cookies.isEmpty()){
            requestSpecBuilder.addCookies(cookies);
        }
        if(headers!=null && !headers.isEmpty()){
            requestSpecBuilder.addHeaders(headers);
        }
        if(queryParams!=null && !queryParams.isEmpty()){
            requestSpecBuilder.addQueryParams(queryParams);
        }
        if(contentType!=null){
            requestSpecBuilder.setContentType(contentType);
        }
        if(content!=null){
            requestSpecBuilder.setBody(content);
        }

        requestSpecBuilder.setBaseUri(baseUrl);

        requestSpecification = requestSpecBuilder.build();

        response = execute(requestType,requestSpecification);

        UnbxdResponse unbxdResponse = processRestAssuredResponse(response);
        return unbxdResponse;
    }

    public UnbxdResponse executeRequest(HttpMethod delete, String url, String ssoCookie, Object headers, Object queryParams, HttpMethod requestType, UnbxdRequestSpecification requestSpecification){
        if(requestSpecification.getContentObj()!=null){
            if(requestSpecification.getContentType().equalsIgnoreCase(ContentType.APPLICATION_JSON.toString())){
                String content = null;
                if(!(requestSpecification.getContentObj() instanceof String)) {
                    content = new Gson().toJson(requestSpecification.getContentObj());
                }
                else
                    content = (String)requestSpecification.getContentObj();

                requestSpecification.addContent(content);
            }
        }
        return executeRequest(requestType, requestSpecification.getBaseUrl(), requestSpecification.getCookies(),
                requestSpecification.getHeaders(),requestSpecification.getQueryParams(),requestSpecification.getContentType(),requestSpecification.getContent());
    }

    private Response execute(HttpMethod requestType, RequestSpecification specification){
        Response response = null;
        RedirectConfig redirectConfig = new RedirectConfig().followRedirects(followRedirect);
        SSLConfig sslConfig = SSLConfig.sslConfig().relaxedHTTPSValidation();

        RestAssuredConfig config = RestAssured.config()
                .redirect(redirectConfig)
                .sslConfig(sslConfig);

        try {
            response = RestAssured.given().config(config).spec(specification).request(String.valueOf(requestType));

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    private UnbxdResponse processRestAssuredResponse(Response response){

        UnbxdResponse unbxdResponse = new UnbxdResponse();
        if (response != null) {

            if (response.getBody() != null)
                unbxdResponse.setResponseBody(response.getBody().asString());

            if (response.asInputStream() != null)
                unbxdResponse.setResponseAsStream(response.asInputStream());

            unbxdResponse.setStatusCode(response.getStatusCode());
            unbxdResponse.setStatusLine(response.getStatusLine());
            unbxdResponse.setCookies(response.getCookies());

            if (response.getHeaders() != null && response.getHeaders().size() > 0) {
                Map<String, String> responseHeaders = new HashMap<>();
                for (Header header : response.getHeaders())
                    responseHeaders.put(header.getName(), header.getValue());
                unbxdResponse.setHeaders(responseHeaders);
            }
        }
        return unbxdResponse;
    }

    public UnbxdResponse executeRequest(HttpMethod requestType, String baseUrl, Map<String, String> cookies, Map<String, String> headers, Map<String, Object> queryParams,
                                        File file){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        RequestSpecification requestSpecification=null;
        Response response;
        if(cookies!=null && !cookies.isEmpty()){
            requestSpecBuilder.addCookies(cookies);
        }
        if(headers!=null && !headers.isEmpty()){
            requestSpecBuilder.addHeaders(headers);
        }
        if(queryParams!=null && !queryParams.isEmpty()){
            requestSpecBuilder.addQueryParams(queryParams);
        }
        if(file!=null){
            //requestSpecBuilder.addFormParam("file",file);
            requestSpecBuilder.addMultiPart("file",file);
        }

        requestSpecBuilder.setBaseUri(baseUrl);

        requestSpecification = requestSpecBuilder.build();

        response = execute(requestType,requestSpecification);

        UnbxdResponse unbxdResponse = processRestAssuredResponse(response);
        return unbxdResponse;
    }
}
