package tz.co.fasthub.ona.domain.instagram;

import org.jinstagram.entity.comments.CommentData;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by root on 5/9/17.
 */
public class Comments {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "data")
    private List<CommentData> comments;

    @Column(name = "count")
    private int count;

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", comments=" + comments +
                ", count=" + count +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CommentData> getComments() {
        return comments;
    }

    public void setComments(List<CommentData> comments) {
        this.comments = comments;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
