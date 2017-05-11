package tz.co.fasthub.ona.domain.instagram;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

/**
 * Created by root on 5/9/17.
 */

public class InstagramTalentAccount {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "profile_picture")
    private String profilePictureUrl;

    @Column(name = "username")
    private String userName;

    private Map<String,Integer> counts;

    @Column(name = "full_name")
    private String fullName;

    private String bio;

    @Column(name = "website")
    private String websiteUrl;

    public InstagramTalentAccount(String profilePictureUrl, String userName, Map<String, Integer> counts, String fullName) {
        this.profilePictureUrl = profilePictureUrl;
        this.userName = userName;
        this.counts = counts;
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "InstagramTalentAccount{" +
                "id=" + id +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", counts=" + counts +
                ", fullName='" + fullName + '\'' +
                ", bio='" + bio + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, Integer> getCounts() {
        return counts;
    }

    public void setCounts(Map<String, Integer> counts) {
        this.counts = counts;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
