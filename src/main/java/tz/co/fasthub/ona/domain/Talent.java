package tz.co.fasthub.ona.domain;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by root on 2/23/17.
 */
@Entity
public class Talent {

    @Id
    @GeneratedValue
    private Long talent_id;
    private String talent_name;
    private String talent_email;
    private String talent_phoneNumber;

    private Talent(){

    }

    public Talent(String talent_name, String talent_email, String talent_phoneNumber) {
        this.talent_name = talent_name;
        this.talent_email = talent_email;
        this.talent_phoneNumber = talent_phoneNumber;
    }

    @Override
    public String toString() {
        return "Talent{" +
                "talent_id=" + talent_id +
                ", talent_name='" + talent_name + '\'' +
                ", talent_email='" + talent_email + '\'' +
                ", talent_phoneNumber='" + talent_phoneNumber + '\'' +
                '}';
    }

    public Long getTalent_id() {
        return talent_id;
    }

    public void setTalent_id(Long talent_id) {
        this.talent_id = talent_id;
    }

    public String getTalent_name() {
        return talent_name;
    }

    public void setTalent_name(String talent_name) {
        this.talent_name = talent_name;
    }

    public String getTalent_email() {
        return talent_email;
    }

    public void setTalent_email(String talent_email) {
        this.talent_email = talent_email;
    }

    public String getTalent_phoneNumber() {
        return talent_phoneNumber;
    }

    public void setTalent_phoneNumber(String talent_phoneNumber) {
        this.talent_phoneNumber = talent_phoneNumber;
    }
}
