package tz.co.fasthub.ona.domain.twitter;

/**
 * Created by daniel on 3/29/17.
 */
public class TwitterResponse {
    private  String media_id;
    private String media_id_string;
    private String expires_after_secs;
    private String size;
    private ProcessingInfo processing_info;
    private Video video;

    public TwitterResponse() {
    }


    public TwitterResponse(String media_id, String media_id_string, String expires_after_secs, String size, ProcessingInfo processing_info, Video video) {
        this.media_id = media_id;
        this.media_id_string = media_id_string;
        this.expires_after_secs = expires_after_secs;
        this.size = size;
        this.processing_info = processing_info;
        this.video = video;
    }

    @Override
    public String toString() {
        return "TwitterResponse{" +
                "media_id='" + media_id + '\'' +
                ", media_id_string='" + media_id_string + '\'' +
                ", expires_after_secs='" + expires_after_secs + '\'' +
                ", size='" + size + '\'' +
                ", processing_info=" + processing_info +
                ", video=" + video +
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ProcessingInfo getProcessing_info() {
        return processing_info;
    }

    public void setProcessing_info(ProcessingInfo processing_info) {
        this.processing_info = processing_info;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
