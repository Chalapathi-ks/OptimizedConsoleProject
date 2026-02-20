package lib.constants;

import java.io.File;

public class UnbxdConstants {

    public static final int SELENIUM_MAXTIMEOUT= 30000;
    public static final int SELENIUM_MINTIMEOUT=3000;
    public static final int SELENIUM_PAGE_MAXTIMEOUT=6000;
    public static final int SELENIUM_PAGE_MINTIMEOUT=3000;

    /** Timeouts in seconds for WebDriverWait. */
    public static final int WAIT_LOADER_SECONDS = 30;
    public static final int WAIT_ELEMENT_APPEAR_SECONDS = 20;
    public static final int WAIT_ELEMENT_LOAD_SECONDS = 15;
    public static final int WAIT_ELEMENT_DISAPPEAR_SECONDS = 15;

    public static final String IMPORT_FILE_DATE_FORMAT="dd/MM/yyyy HH:mm:ss";


    public static final String TEST_DATA_PATH = "target" + File.separator+"test-classes"+File.separator+"testData"+File.separator;
    public static final String BULK_IMPORT_FILES_PATH="target" + File.separator+"test-classes"+File.separator+"testData"+File.separator+"bulkUploadFiles"+File.separator;


}
