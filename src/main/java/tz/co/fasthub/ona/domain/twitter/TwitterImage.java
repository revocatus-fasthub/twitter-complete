package tz.co.fasthub.ona.domain.twitter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by root on 3/28/17.
 */

public class TwitterImage {

    private int w;
    private int h;
    private String image_type;


    public TwitterImage(int w, int h, String image_type) {
        this.w = w;
        this.h = h;
        this.image_type = image_type;
    }


    @Override
    public String toString() {
        return "Image{" +
                "w=" + w +
                ", h=" + h +
                ", image_type='" + image_type + '\'' +
                '}';
    }


    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }
}
