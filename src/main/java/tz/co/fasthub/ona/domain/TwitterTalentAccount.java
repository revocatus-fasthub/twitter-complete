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
    private String requestTokenSecret;
    private String oauth_verifier;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "talent_id")
    private Talent talent;

    public TwitterTalentAccount() {
    }


    public TwitterTalentAccount(String displayName) {
        this.displayName = displayName;
    }

    public TwitterTalentAccount(String profileUrl, String displayName, String accessToken, String imageUrl,
                                String appsAccessTokenSecret, String requestTokenSecret,
                                String requestTokenValue) {
        this.profileUrl = profileUrl;
        this.displayName = displayName;
        this.accessToken = accessToken;
        this.imageUrl = imageUrl;
        this.requestTokenSecret = requestTokenSecret;
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
                ", requestTokenSecret='" + requestTokenSecret + '\'' +
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

    public String getRequestTokenSecret() {
        return requestTokenSecret;
    }

    public void setRequestTokenSecret(String requestTokenSecret) {
        this.requestTokenSecret = requestTokenSecret;
    }

    public Talent getTalent() {
        return talent;
    }

    public void setTalent(Talent talent) {
        this.talent = talent;
    }

    public String getOauth_verifier() {
        return oauth_verifier;
    }

    public void setOauth_verifier(String oauth_verifier) {
        this.oauth_verifier = oauth_verifier;
    }

}
