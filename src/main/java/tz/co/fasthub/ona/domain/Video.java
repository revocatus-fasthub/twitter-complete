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
    private Long id;

    private String name;

    private String path;

    @OneToOne(mappedBy = "video")
    private Payload payload;

    private Video(){}

    public Video(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
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
}