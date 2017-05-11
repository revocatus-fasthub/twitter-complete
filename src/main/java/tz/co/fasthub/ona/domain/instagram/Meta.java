package tz.co.fasthub.ona.domain.instagram;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by root on 5/9/17.
 */
public class Meta {
    @Id
    @GeneratedValue
    private Long id;

    private int code;

    public Meta(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "id=" + id +
                ", code=" + code +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
