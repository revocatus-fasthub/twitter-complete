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

    private String screenName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id")
    private Video video;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "talentTalentAccount_id")
    private TwitterTalentAccount twitterTalentAccount;

    private Payload(){}

    public Payload(String message,String screenName) {
        this.message = message;
        this.screenName = screenName;
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

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public TwitterTalentAccount getTwitterTalentAccount() {
        return twitterTalentAccount;
    }

    public void setTwitterTalentAccount(TwitterTalentAccount twitterTalentAccount) {
        this.twitterTalentAccount = twitterTalentAccount;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}