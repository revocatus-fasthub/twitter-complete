package tz.co.fasthub.ona.domain.twitter;

/**
 * Created by daniel on 3/29/17.
 */
public class TwitterResponse {
    private  String media_id;
    private String media_id_string;
    private String expires_after_secs;

    public TwitterResponse() {
    }

    public TwitterResponse(String media_id, String media_id_string, String expires_after_secs) {
        this.media_id = media_id;
        this.media_id_string = media_id_string;
        this.expires_after_secs = expires_after_secs;
    }


    @Override
    public String toString() {
        return "TwitterResponse{" +
                "media_id='" + media_id + '\'' +
                ", media_id_string='" + media_id_string + '\'' +
                ", expires_after_secs='" + expires_after_secs + '\'' +
                '}';
    }


    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_id_string() {
        return media_id_string;
    }

    public void setMedia_id_string(String media_id_string) {
        this.media_id_string = media_id_string;
    }

    public String getExpires_after_secs() {
        return expires_after_secs;
    }

    public void setExpires_after_secs(String expires_after_secs) {
        this.expires_after_secs = expires_after_secs;
    }
}
