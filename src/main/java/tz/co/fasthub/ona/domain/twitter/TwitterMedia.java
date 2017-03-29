package tz.co.fasthub.ona.domain.twitter;


import javax.persistence.*;

/**
 * Created by root on 3/28/17.
 */

public class TwitterMedia {

    private long media_id;
    private String media_id_string;
    private long size;
    private long expires_after_secs;

    private String processingState;


    public TwitterMedia(long media_id, String media_id_string, long size, long expires_after_secs, String processingState) {
        this.media_id = media_id;
        this.media_id_string = media_id_string;
        this.size = size;
        this.expires_after_secs = expires_after_secs;
        this.processingState = processingState;
    }

    @Override
    public String toString() {
        return "TwitterMedia{" +
                "media_id=" + media_id +
                ", media_id_string='" + media_id_string + '\'' +
                ", size=" + size +
                ", expires_after_secs=" + expires_after_secs +
                ", processingState='" + processingState + '\'' +
                '}';
    }

    public long getMedia_id() {
        return media_id;
    }

    public void setMedia_id(long media_id) {
        this.media_id = media_id;
    }

    public String getMedia_id_string() {
        return media_id_string;
    }

    public void setMedia_id_string(String media_id_string) {
        this.media_id_string = media_id_string;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getExpires_after_secs() {
        return expires_after_secs;
    }

    public void setExpires_after_secs(long expires_after_secs) {
        this.expires_after_secs = expires_after_secs;
    }

    public String getProcessingState() {
        return processingState;
    }

    public void setProcessingState(String processingState) {
        this.processingState = processingState;
    }
}
