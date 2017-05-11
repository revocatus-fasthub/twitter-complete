package tz.co.fasthub.ona.domain.instagram;

import javax.persistence.Column;

/**
 * Created by root on 5/9/17.
 */
public class VideoInsta {
    @Column(name = "low_resolution")
    private MediaData lowResolution;


    @Column(name = "standard_resolution")
    private MediaData standardResolution;

    public VideoInsta(MediaData lowResolution, MediaData standardResolution) {
        this.lowResolution = lowResolution;
        this.standardResolution = standardResolution;
    }

    @Override
    public String toString() {
        return "VideoInsta{" +
                "lowResolution=" + lowResolution +
                ", standardResolution=" + standardResolution +
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
}
