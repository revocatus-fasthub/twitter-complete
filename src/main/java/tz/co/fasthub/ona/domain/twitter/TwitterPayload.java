package tz.co.fasthub.ona.domain.twitter;

/**
 * Created by root on 3/28/17.
 */
public class TwitterPayload {

    private TwitterMedia media;
    private Image image;

    public TwitterPayload() {
    }

    public TwitterPayload(TwitterMedia media, Image image) {
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
