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
    private Long media_id;

    private String name;

    private String size;

    private String path;

    @OneToOne(mappedBy = "video")
    private Payload payload;

    private Video(){}

    public Video(String name) {
        this.name = name;
    }

    public Long getMedia_id() {
        return media_id;
    }

    public void setMedia_id(Long media_id) {
        this.media_id = media_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}