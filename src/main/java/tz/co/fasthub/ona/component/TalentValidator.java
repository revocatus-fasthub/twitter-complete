package tz.co.fasthub.ona.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import tz.co.fasthub.ona.domain.Talent;
import tz.co.fasthub.ona.service.TalentService;

/**
 * Created by Naamini on 4/6/17.
 */
@Component
public class TalentValidator implements Validator {

    public boolean supports(Class<?> aClass) {
        return Talent.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {

        Talent talent = (Talent)o;
        String password = talent.getPassword();
        String cpassword = talent.getCpassword();

        /*
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fname", "NotEmpty");
        if (user.getFname().length() < 6 || user.getFname().length() > 32) {
            errors.rejectValue("fname", "Size.userForm.fname");
        }
*/
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        /*if (user.getPassword().length() < 5 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }*/

        if (!password.equals(cpassword)) {
            errors.rejectValue("cpassword", "talent.password.mismatch");
        }
    }
}