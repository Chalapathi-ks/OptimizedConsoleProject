package lib;

import lib.config.Configuration;
import lib.config.Context;
import lib.config.UnbxdService;
import org.testng.Assert;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class EnvironmentConfig {


    public  static String configFile;
    private static Configuration configuration;
    private static final String SITE_CONFIG_FILE="siteConfig.yml";

    private static ThreadLocal<Context> context=new ThreadLocal<Context>();

    public static void loadConfig() throws Exception {
        if(configuration==null)
        {
            Yaml yaml = new Yaml();
            // Resolve profile: system property first, then config.properties, then default for IDE runs.
            String resolvedProfile = System.getProperty("env.profile");
            if (resolvedProfile == null || resolvedProfile.trim().isEmpty()) {
                resolvedProfile = Config.getEnvironmentProfile();
            }
            if (resolvedProfile == null || resolvedProfile.trim().isEmpty() || resolvedProfile.contains("${")) {
                resolvedProfile = "ProdUK";
                System.out.println("[INFO] Environment profile not set; using default: " + resolvedProfile + " (for Maven use -P<ProfileName> e.g. -PProdUK, or -Denv.profile=<ProfileName>)");
            }
            String file= resolvedProfile + ".yaml";
            try(InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file)) {
                if (in == null) {
                    throw new Exception("Yaml file not found on classpath: " + file);
                }
                configuration = yaml.loadAs(in, Configuration.class);
                configuration.setLoginUrl(configuration.getApplicationUrl());
            } catch(Exception ex) {
                ex.printStackTrace();
                throw new Exception(" Exception while loading the Yaml File "+file);
            }
        }
    }

    public static String getApplicationUrl()
    {
        return configuration.getApplicationUrl();
    }

    public static String getBaseUrl()
    {
        return configuration.getBaseUrl();
    }

    public static void setApplicationUrl(String url) {

        configuration.setApplicationUrl(url);
    }

    public static String getSiteKey()
    {
        int siteId=context.get().getSiteId();
        return configuration.getSiteById(siteId).getSiteKey();
    }

    public static String getStatusById()
    {
        int siteId=context.get().getSiteId();
        return configuration.getSiteById(siteId).getStatusById();
    }

    public static String getRegion()
    {
        int siteId = context.get().getSiteId();
        return configuration.getSiteById(siteId).getRegion();
    }

    public static String getApiKey()
    {
        int siteId=context.get().getSiteId();
        return configuration.getSiteById(siteId).getApiKey();

    }

    public static String getSecreteKey()
    {
        int siteId=context.get().getSiteId();
        return configuration.getSiteById(siteId).getSecretKey();
    }

    public static String getSiteName()
    {
        int siteId=context.get().getSiteId();

        return configuration.getSiteById(siteId).getName();
    }

    public static String getEmail()
    {
        int userId=context.get().getUserId();
        return configuration.getUserById(userId).getEmail();
    }

    public static String getDeleteCookie()
    {
        int userId=context.get().getUserId();
        return configuration.getUserById(userId).getCookie();
    }

    public static String getServiceUrl(String service){

        for(UnbxdService unbxdService:configuration.getServices()){

            if(unbxdService.getName().equalsIgnoreCase(service)){
                return unbxdService.getHost();
            }
        }
        return "";
    }

    public static String getSiteId()
    {
        int siteId=context.get().getSiteId();
        return String.valueOf(configuration.getSiteById(siteId).getSiteId());
    }

    public static String getPassword()
    {
        int userId=context.get().getUserId();
        return configuration.getUserById(userId).getPassword();
    }

    public static String getLoginUrl(){
        return configuration.getLoginUrl();
    }

    // public static void setContext(int userId, int siteId){

    //     System.out.println("Setting the Context UserId:"+userId+ " , siteId : "+siteId);
    //     if(context.get()!=null)
    //         Assert.fail("Context is already Set , cant recreate it");
    //     Context newContext=getNewContext(userId,siteId);
    //     context.set(newContext);
    // }

    public static void setContext(int userId, int siteId) {
    if (context.get() != null) {
        System.out.println("Context already set. Skipping reinitialization.");
        return;
    }

    System.out.println("Setting the Context UserId:" + userId + " , siteId : " + siteId);
    Context newContext = getNewContext(userId, siteId);
    context.set(newContext);
}

    public static void unSetContext(){
        if (context.get() != null) {
        System.out.print("Removing the Context userId: "+context.get().getUserId()+" , SiteId: "+context.get().getSiteId());
        context.remove();
        } else {
            System.out.println("No context to remove - context is already null");
        }
    }

    private static Context getNewContext(int userId, int siteId){
        Context context=new Context();
        context.setUserId(userId);
        context.setSiteId(siteId);
        return context;
    }

}
