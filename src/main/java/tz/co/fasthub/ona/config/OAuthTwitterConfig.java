package tz.co.fasthub.ona.config;

/**
 * Created by Naamini on 4/24/17.
 */

public class OAuthTwitterConfig {

    private String consumerKey;

    private String consumerSecret;

    private String callbackUrl;

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

}