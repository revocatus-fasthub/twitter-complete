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
    private String displayName;
    private String accessToken;//from twitterManualController
    private String imageUrl;
    private String appsAccessToken;
    private String appsAccessTokenSecret;
    private String requestTokenSecret;
    private String requestTokenValue;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "talent_id")
    private Talent talent;

    public TwitterTalentAccount() {
    }


    public TwitterTalentAccount(String displayName) {
        this.displayName = displayName;
    }

    public TwitterTalentAccount(String profileUrl, String displayName, String accessToken, String imageUrl,
                                String appsAccessToken, String appsAccessTokenSecret, String requestTokenSecret,
                                String requestTokenValue) {
        this.profileUrl = profileUrl;
        this.displayName = displayName;
        this.accessToken = accessToken;
        this.imageUrl = imageUrl;
        this.appsAccessToken = appsAccessToken;
        this.appsAccessTokenSecret = appsAccessTokenSecret;
        this.requestTokenSecret = requestTokenSecret;
        this.requestTokenValue = requestTokenValue;
    }

    @Override
    public String
    toString() {
        return "TwitterTalentAccount{" +
                "id=" + id +
                ", profileUrl='" + profileUrl + '\'' +
                ", displayName='" + displayName + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", appsAccessToken='" + appsAccessToken + '\'' +
                ", appsAccessTokenSecret='" + appsAccessTokenSecret + '\'' +
                ", requestTokenSecret='" + requestTokenSecret + '\'' +
                ", requestTokenValue='" + requestTokenValue + '\'' +
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getAppsAccessToken() {
        return appsAccessToken;
    }

    public void setAppsAccessToken(String appsAccessToken) {
        this.appsAccessToken = appsAccessToken;
    }

    public String getAppsAccessTokenSecret() {
        return appsAccessTokenSecret;
    }

    public void setAppsAccessTokenSecret(String appsAccessTokenSecret) {
        this.appsAccessTokenSecret = appsAccessTokenSecret;
    }

    public String getRequestTokenSecret() {
        return requestTokenSecret;
    }

    public void setRequestTokenSecret(String requestTokenSecret) {
        this.requestTokenSecret = requestTokenSecret;
    }

    public String getRequestTokenValue() {
        return requestTokenValue;
    }

    public void setRequestTokenValue(String requestTokenValue) {
        this.requestTokenValue = requestTokenValue;
    }

    public Talent getTalent() {
        return talent;
    }

    public void setTalent(Talent talent) {
        this.talent = talent;
    }
}
