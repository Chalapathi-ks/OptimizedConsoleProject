package lib.enums;

public enum UnbxdEnum {

    SEARCHABLE_FIELD("Seachable field"),
    FACETABLE_FIELD("Facetable field"),
    DICTIONARY("dictionary"),
    AUTO_SUGGEST("AutoSuggest Group"),

    PLATFORM_UPLOAD("Platform upload"),
    PIM_UPLOAD("Api upload"),
    API_UPLOAD("Api upload"),

    PREVIEW_TITLE("Preview Title"),
    PREVIEW_BADGES("Preview Badges"),
    PREVIEW_SKU("Preview Sku"),

    Preview_Size("Preview Size"),

    Preview_TemplateName("Preview TemplateName"),


    TEMPLATE_1("Template 1"),
    TEMPLATE_2("Template 2"),
    TEMPLATE_3("Template 3"),
    TEMPLATE_4("Template 4"),
    TEMPLATE_5("Template 5"),
    TEMPLATE_6("Template 6"),
    TEMPLATE_7("Template 7"),
    TEMPLATE_8("Template 8"),


    MAGENTO_TAB("Magento"),
    HYBRIS_TAB("Hybris"),
    SHOPIFY_TAB("Shopify"),

    KEYWORD("Keyword section"),
    INFIELD("Infield section"),
    TOP_SEARCH("Top search section"),
    PROMOTED_SUGGESTION("Promoted suggestion tab"),
    POPULAR_PRODUCTS("Popular products"),

    CATEGORY_PAGE("Category Pages"),
    SEARCH("Search"),

    QUERY_RULE("Query Rule"),
    FIELD_RULE("Field Rule"),
    SITE_RULE("Site Rule"),

    CONFIGURE_SITE("Configure Site"),
    CONFIGURE_SEARCH("Configure Search"),

    LIBRARIES("Libraries"),

    FACET_DISPLAY_NAME("Display Name"),
    FACET_ATTRIBUTE_NAME("Select Attribute"),
    FACET_TYPE("Facet Type"),
    FACET_LENGTH("Facet Length"),
    FACET_SORT_ORDER("Sort Order"),
    FACET_RANGE_START("Range Start"),
    FACET_RAGNE_END("Range End"),
    FACEET_RANGE_GAP("Range Gap"),

    BOOST("Boost"),
    SORT("Sort"),

    SLOT("Slot"),
    PIN("Pin"),
    FILTER("Filter"),
    FRESH("Fresh"),

    LOCATION("location"),
    USERTYPE("usertype"),
    DEVICETYPE("devicetype"),

    PROMOTION("Promotion"),
    BANNER("Banner"),
    FACETS("Facets"),
    REDIRECT("Redirect"),

    EDIT("edit"),
    PREVIEW("Preview"),
    DELETE("Delete"),
    STOPPED("Stopped"),
    DUPLICATE("Duplicate"),
    MORE("More"),

    LEFTMORE("Left More"),

    UNBXDDEFAULT("Unbxd Default"),
    VARIATIONA ("Variation A"),
    VARIATIONB("Variation B"),

    COMMERCE_SEARCH("Commerce Search"),
    TYPE_AHEAD("Typeahead"),
    STOP_WORDS("Blacklisted Suggestions"),

    SEGMENT_LOCATION("location"),

    BROWSE("Browse"),
    ABTEST("AbTest"),
    SEGMENTS("Segmentation"),
    ACCOUNT_SETTING("Account Settings"),
    RECOMMENDATION("Recommendation"),

    GLOBALBOOST ("Boost"),
    GLOBALFILTER("Filter");


    public String label;

    UnbxdEnum(String s) {
        this.label=s;
    }

    public String getLabel()
    {
        return this.label;
    }
}
