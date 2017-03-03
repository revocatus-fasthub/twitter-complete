package tz.co.fasthub.ona.domain;

/**
 * Created by root on 3/3/17.
 */
public class PayLoad {
    String twitterScreenName;
    String message;

    private PayLoad(){}

    public PayLoad(String twitterScreenName, String message) {
        this.twitterScreenName = twitterScreenName;
        this.message = message;
    }

    @Override
    public String toString() {
        return "PayLoad{" +
                "twitterScreenName='" + twitterScreenName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getTwitterScreenName() {
        return twitterScreenName;
    }

    public void setTwitterScreenName(String twitterScreenName) {
        this.twitterScreenName = twitterScreenName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
