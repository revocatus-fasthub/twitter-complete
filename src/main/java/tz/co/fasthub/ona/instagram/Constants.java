package tz.co.fasthub.ona.instagram;

public final class Constants {

    /**
     * Config Properties
     */
    public static final String CLIENT_ID = "f53a95ed9ba34e4bab51fdd2d588b984";
    public static final String CLIENT_SECRET = "db2aea5867bb4d97a48dc21e94dc5dfb";
    public static final String REDIRECT_URI = "http://127.0.0.1:8080/instagram/callback";
    public static final String AUTHORIZATION_URL = "https://api.instagram.com/oauth/authorize/?client_id=%s&redirect_uri=%s&state=%s&grant_type=authorization_code&response_type=code";
    public static final String SCOPE = "scope";
    public static final String GRANT_TYPE = "grant_type";
    public static final String CODE = "code";
    public static final String DISPLAY = "display";

    /**
     * Http Session Attributes
     */
    public static final String INSTAGRAM_OBJECT = "igObject";
    public static final String INSTAGRAM_SERVICE = "igService";

    public static final int MAX_PAGE_SIZE = 5;
}
