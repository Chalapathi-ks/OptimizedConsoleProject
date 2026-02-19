package lib.config;

import lib.Config;

public class User {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCookie()
    {
        return deleteSiteSsoIdKeyValue;
    }

    public void setCookie(String cookie)
    {
        this.deleteSiteSsoIdKeyValue=cookie;
    }

    private int id;
    private String email;
    private String password;
    private String deleteSiteSsoIdKeyValue;
}
