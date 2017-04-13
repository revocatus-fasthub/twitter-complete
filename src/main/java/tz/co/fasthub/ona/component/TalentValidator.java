package tz.co.fasthub.ona.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import tz.co.fasthub.ona.domain.Talent;
import tz.co.fasthub.ona.service.TalentService;

/**
 * Created by root on 4/6/17.
 */
@Component
public class TalentValidator implements Validator {

    @Autowired
    private TalentService talentService;

    public boolean supports(Class<?> aClass) {
        return Talent.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        Talent user = (Talent) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fname", "NotEmpty");
        if (user.getFname().length() < 6 || user.getFname().length() > 32) {
            errors.rejectValue("fname", "Size.userForm.fname");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getCpassword().equals(user.getPassword())) {
            errors.rejectValue("Cpassword", "Diff.userForm.cpassword");
        }
    }


}
