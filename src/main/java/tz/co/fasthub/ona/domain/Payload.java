package tz.co.fasthub.ona.domain;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by root on 3/3/17.
 */
@Entity
public class Payload {

    @Id
    @GeneratedValue
    private Long payload_id;
    //String twitterScreenName;
    private String message;
    //private MultipartFile file;

    private Payload(){}

    public Payload(String message, MultipartFile file) {
        this.message = message;
  //      this.file = file;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "payload_id=" + payload_id +
                ", message='" + message + '\'' +
    //            ", file='" + file + '\'' +
                '}';
    }

    public Long getpayload_id() {
        return payload_id;
    }

    public void setpayload_id(Long payload_id) {
        this.payload_id = payload_id;
    }

    public String getMessage(String message) {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /*

     */
  }