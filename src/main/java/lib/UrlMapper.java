package lib;

public enum UrlMapper {

    LOGIN("/login"),
    SIGN_UP("/signup"),

    WELCOME_PAGE("/ss/newuserlanding"),
    CREATE_SITE_PAGE("/ss/onboarding"),

    LIBRARIES("/ss/search/algorithms/%s/content/synonyms"),
    PHRASES("/ss/search/algorithms/%s/content/phrases"),
    STEMMING("/ss/search/algorithms/%s/content/stemming"),
    CONCEPTS("/ss/search/algorithms/%s/content/concepts"),
    STOP_WORD("/ss/search/algorithms/%s/content/stopwords"),
    RELEVANCY_PAGE("/ss/onboarding/%s"),
    SEARCH_PAGE("/ss/search/merchandising/commerce-search/%s/promotions"),

    SEARCH_AUTOSUGGEST_PAGE("/ss/search/manage-autosuggest/%s/desktop/configure"),
    SEARCH_FACETS_FIELDS("/ss/search/manage-search/%s/search"),

    BROWSE_PAGE("/ss/category-pages/merchandising/commerce-browse/%S/promotions"),

    SEGMENT_PAGE("/ss/category-pages/merchandising/segments/%S"),

    BROWSE_SEGMENT_PAGE("/ss/category-pages/merchandising/segments/%S"),

    COLLECTION_PAGE("/ss/search/merchandising/collections/%S"),

    CATALOG_SEARCH_PAGE("/ss/search/catalog/%S/mapping"),

    MANAGE_SEARCH_PAGE("/ss/search/manage-search/%S/facets"),

    BROWSE_FIELD_PROPERTIES_PAGE("/ss/category-pages/catalog/%S/field-properties"),

    PROMOTEDSUGGESTIONS_PAGE("/ss/search/merchandising/typeahead/%S/promoted_suggestions"),

   BLACKLISTEDSUGGESTIONS_PAGE("/ss/search/merchandising/typeahead/%S/stop_suggestions"),


    SEARCH_FIELD_PROPERTIES_PAGE("/ss/search/catalog/%S/field-properties"),

    MANAGE_BROWSE_PAGE("/ss/category-pages/manage-browse/%S/facets"),
    CONFIGURE_SITE_SEARCH_PAGE("/search/sites/%S/keys"),
    // MEASUREMENT SEARCH URL:
    MEASUREMENT_SEARCH("/ss/search/algorithms/%s/intent/measurement-search"),
    DIMENSION_MAPPING("/ss/search/catalog/%s/mapping"),
    VECTOR_SEARCH("/ss/search/algorithms/%s/intent/vector-search"),
    PRODUCT_CARD_MAPPING("/ss/search/catalog/%s/pcm"),
    SYNONYMS("/ss/search/algorithms/%s/content/synonyms"),

    // RECS URL:
    EXPERIENCES("/ss/search/algorithms/%s/content/experiences"),

    PREVIEW_PAGE("/ss/search/preview/%s?q=*");

    private final String urlPath;
    UrlMapper(String url) {
        this.urlPath=url;
    }

    public String getUrlPath(String... args)
    {
        String result=String.format(this.urlPath,args);
        return EnvironmentConfig.getApplicationUrl()+result;
    }

   /* public String getBaseUrlPath(String... args)
    {
        String result=String.format(this.urlPath,args);
        return EnvironmentConfig.getBaseUrl()+result;
    }*/

    public String getUrlPathFromAppUrl(String applicationUrl, String... args){
        String result=String.format(this.urlPath,args);
        return applicationUrl+result;
    }

    public String getBaseUrl(String... args)
    {
        String result=String.format(this.urlPath,args);
        return EnvironmentConfig.getBaseUrl()+result;
    }

}
