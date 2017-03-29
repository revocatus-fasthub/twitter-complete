package tz.co.fasthub.ona.domain.twitter;

/**
 * Created by root on 3/28/17.
 */
public class TwitterPayload {

    private TwitterMedia media;
    private TwitterImage image;

    public TwitterPayload() {
    }

    public TwitterPayload(TwitterMedia media, TwitterImage image) {
        this.media = media;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "media=" + media +
                ", image=" + image +
                '}';
    }

    public TwitterMedia getMedia() {
        return media;
    }

    public void setMedia(TwitterMedia media) {
        this.media = media;
    }

    public TwitterImage getImage() {
        return image;
    }

    public void setImage(TwitterImage image) {
        this.image = image;
    }
}
