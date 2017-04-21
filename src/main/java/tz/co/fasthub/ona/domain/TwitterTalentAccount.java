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
    private String profileImageUrl;
    private String username;
    private String password;
    private String accessToken;

    //@OneToOne(mappedBy = "twitterTalentAccount")
    //private Talent talent;


    public TwitterTalentAccount() {
    }

    public TwitterTalentAccount(String profileImageUrl, String username, String password, String accessToken) {
        this.profileImageUrl = profileImageUrl;
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "TwitterTalentAccount{" +
                "id=" + id +
                ", profile_url='" + profileImageUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUsername(String screenName) {
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

    public String getAccessToken(String accessToken) {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
