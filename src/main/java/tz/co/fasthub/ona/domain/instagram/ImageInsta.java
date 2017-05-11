package tz.co.fasthub.ona.domain.instagram;

import javax.persistence.Column;

/**
 * Created by root on 5/9/17.
 */
public class ImageInsta {
    @Column(name = "low_resolution")
    private MediaData lowResolution;

    @Column(name = "standard_resolution")
    private MediaData standardResolution;

    @Column(name = "thumbnail")
    private MediaData thumbnail;

    public ImageInsta(MediaData lowResolution, MediaData standardResolution, MediaData thumbnail) {
        this.lowResolution = lowResolution;
        this.standardResolution = standardResolution;
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "ImageInsta{" +
                "lowResolution=" + lowResolution +
                ", standardResolution=" + standardResolution +
                ", thumbnail=" + thumbnail +
                '}';
    }

    public MediaData getLowResolution() {
        return lowResolution;
    }

    public void setLowResolution(MediaData lowResolution) {
        this.lowResolution = lowResolution;
    }

    public MediaData getStandardResolution() {
        return standardResolution;
    }

    public void setStandardResolution(MediaData standardResolution) {
        this.standardResolution = standardResolution;
    }

    public MediaData getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MediaData thumbnail) {
        this.thumbnail = thumbnail;
    }
}
