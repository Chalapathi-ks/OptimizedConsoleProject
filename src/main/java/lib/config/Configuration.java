package lib.config;

import lib.model.Campaign;

import java.util.List;

public class Configuration {


    private String applicationUrl;
    private String baseUrl;
    private List<User> users;
    private List<Site> sites;
    private List<Campaign> campaign;
    private List<UnbxdService> services;

    private String loginUrl;


    public String getApplicationUrl() {
        return applicationUrl;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public List<UnbxdService> getServices() {
        return services;
    }

    public void setServices(List<UnbxdService> services) {
        this.services = services;
    }


    public Site getSiteById(int id){

        for(Site site:sites){

            if(site.getId()==id){
                return site;
            }
        }

        return null;
    }

    public User getUserById(int id){

        for(User user:users){

            if(user.getId()==id){
                return user;
            }
        }
        return null;
    }

    public UnbxdService getService(String name){

        for(UnbxdService service:services){
            if(service.getName().equalsIgnoreCase(name))
                return service;
        }
        return null;
    }


    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }



    public Configuration() {
    }
}
