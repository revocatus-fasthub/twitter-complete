package tz.co.fasthub.ona.domain;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tz.co.fasthub.ona.component.TalentValidator;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * Talent entity.
 */
@Entity
public class Talent implements Validator{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "talent_id")
    private Integer id;

    @Version
    private Integer version;

    @Column(name = "first_name")
    @NotEmpty(message = "*Please provide your first name")
    private String fname;

    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lname;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Password does not match")
    @Transient
    private String password;

    @NotNull(message = "*Password do not match")
    @Transient
   // @Constraint(validatedBy = TalentValidator.class)
    private String Cpassword;


    public boolean supports(Class<?> aClass) {
        return Talent.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        //checkPassword();
    }

    public String getCpassword() {
        return Cpassword;
    }

    public void setCpassword(String Cpassword) {
     //   if(!password.equals(this.password))
       // this.password = null;
        this.Cpassword=Cpassword;
        checkPassword();
    }


    private void checkPassword() {
        Talent user = null; Errors errors = null;
        if(this.password == null || this.Cpassword == null){
            return;
        }else if (!Cpassword.equals(password)) {
            errors.rejectValue("Cpassword", "Diff.userForm.cpassword");
        }
    }

}
