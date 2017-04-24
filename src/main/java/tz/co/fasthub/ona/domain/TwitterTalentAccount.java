package tz.co.fasthub.ona.domain;

import javax.persistence.*;

/**
 * Created by root on 3/3/17.
 */
@Entity
public class TwitterTalentAccount {

    @Id
    @GeneratedValue
    private Long id;
    private String profileUrl;
    private String username;
    private String password;
    private String accessToken;
    private String imageUrl;

    //@OneToOne(mappedBy = "twitterTalentAccount")
    //private Talent talent;


    public TwitterTalentAccount() {
    }

    public TwitterTalentAccount(String profileUrl, String username, String password, String accessToken, String imageUrl) {
        this.profileUrl = profileUrl;
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "TwitterTalentAccount{" +
                "id=" + id +
                ", profileUrl='" + profileUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
