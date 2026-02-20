package lib.config;

public class Site {


    private String siteKey;
    private String apiKey;
    private String secretKey;
    private Long siteId;
    private String statusById;
    private int id;
    private String name;
    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getStatusById()
    {
        return  statusById;
    }

    public void setStatusById(String statusById)
    {
        this.statusById = statusById;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){

        this.name=name;
    }

    public String getName(){
        return name;
    }
}
