package tz.co.fasthub.ona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by root on 3/10/17.
 */

@Entity
public class Video {

    @Id
    @GeneratedValue
    private long mediaId;

    private int imageWidth;
    private int imageHeight;
    private String imageType;
    private long size;
    private String processingState;
    private int processingCheckAfterSecs;
    private String name;
    private String path;

    @OneToOne(mappedBy = "video")
    private Payload payload;

    private Video(){}


    public Video(int imageWidth, int imageHeight, String imageType, String name) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageType = imageType;
        this.name = name;
    }

    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getProcessingState() {
        return processingState;
    }

    public void setProcessingState(String processingState) {
        this.processingState = processingState;
    }

    public int getProcessingCheckAfterSecs() {
        return processingCheckAfterSecs;
    }

    public void setProcessingCheckAfterSecs(int processingCheckAfterSecs) {
        this.processingCheckAfterSecs = processingCheckAfterSecs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}