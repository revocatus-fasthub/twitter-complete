package tz.co.fasthub.ona.domain.twitter;

/**
 * Created by daniel on 5/7/17.
 */
public class Video {
    private String video_type;

    public Video(String video_type) {
        this.video_type = video_type;
    }

    public Video() {
    }

    @Override
    public String toString() {
        return "Video{" +
                "video_type='" + video_type + '\'' +
                '}';
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }
}
