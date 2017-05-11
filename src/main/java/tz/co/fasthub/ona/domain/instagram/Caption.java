package tz.co.fasthub.ona.domain.instagram;

import org.jinstagram.entity.common.FromTagData;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by root on 5/9/17.
 */
public class Caption {

    @Id
    @GeneratedValue
    private String id;

    @Column(name = "created_time")
    private String createdTime;

    @Column(name = "from")
    private FromTagData from;

    @Column(name = "text")
    private String text;

    public Caption(String createdTime, FromTagData from, String text) {
        this.createdTime = createdTime;
        this.from = from;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Caption{" +
                "id='" + id + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", from=" + from +
                ", text='" + text + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public FromTagData getFrom() {
        return from;
    }

    public void setFrom(FromTagData from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
