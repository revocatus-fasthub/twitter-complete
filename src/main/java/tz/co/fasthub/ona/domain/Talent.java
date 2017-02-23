package tz.co.fasthub.ona.domain;


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
    private String name;
    private String email;
    private String phoneNumber;

    private Talent(){}

    public Talent(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Talent{" +
                "talent_id=" + talent_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public Long getTalent_id() {
        return talent_id;
    }

    public void setTalent_id(Long talent_id) {
        this.talent_id = talent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
