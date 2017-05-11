package tz.co.fasthub.ona.domain.instagram;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by root on 5/9/17.
 */
public class Likes {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "count")
    private int count;

    @Column(name = "data")
    private List<InstagramTalentAccount> likesInstagramTalentAccountList;

    public Likes(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "id=" + id +
                ", count=" + count +
                ", likesInstagramTalentAccountList=" + likesInstagramTalentAccountList +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<InstagramTalentAccount> getLikesInstagramTalentAccountList() {
        return likesInstagramTalentAccountList;
    }

    public void setLikesInstagramTalentAccountList(List<InstagramTalentAccount> likesInstagramTalentAccountList) {
        this.likesInstagramTalentAccountList = likesInstagramTalentAccountList;
    }
}
