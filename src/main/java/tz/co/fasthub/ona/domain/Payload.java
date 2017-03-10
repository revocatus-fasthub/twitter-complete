package tz.co.fasthub.ona.domain;

import javax.persistence.*;

/**
 * Created by root on 3/3/17.
 */
@Entity
public class Payload {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    private Payload(){}

    public Payload(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    private void setId(Long payload_id) {
        this.id = payload_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}