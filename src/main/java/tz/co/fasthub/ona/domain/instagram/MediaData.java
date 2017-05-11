package tz.co.fasthub.ona.domain.instagram;

import javax.persistence.Column;

/**
 * Created by root on 5/9/17.
 */
public class MediaData {
    @Column(name = "url")
    private String url;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    public MediaData(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "MediaData{" +
                "url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
