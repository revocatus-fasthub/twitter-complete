package tz.co.fasthub.ona.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tz.co.fasthub.ona.domain.Talent;

@Component
public class TalentValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(TalentValidator.class);

    public boolean supports(Class<?> aClass) {
        return Talent.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {

        Talent talent = (Talent)o;
        String password = talent.getPassword();
        String cpassword = talent.getCpassword();

        if (!password.equals(cpassword)) {
            errors.rejectValue("password", "Diff.userForm.cpassword");
            errors.rejectValue("cpassword", "Diff.userForm.cpassword");
        }
    }
}
