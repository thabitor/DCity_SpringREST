package be.digitalcity.springrestbxl.validation.validator;

import be.digitalcity.springrestbxl.model.forms.ReservationRequestForm;
import be.digitalcity.springrestbxl.validation.constraint.ReservationValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReservationValidator implements ConstraintValidator<ReservationValid, ReservationRequestForm> {
    @Override
    public boolean isValid(ReservationRequestForm value, ConstraintValidatorContext context) {

        boolean isValid = true;
        if(value.getArrive().isAfter(value.getDepart())) {
            context.buildConstraintViolationWithTemplate("arrive doit etre après le depart")
                    .addConstraintViolation();
            isValid = false;
        }

        if ( !value.getArrive().toLocalDate().isEqual( value.getDepart().toLocalDate() )){
            context.buildConstraintViolationWithTemplate("arrive et depart le même jour")
                    .addConstraintViolation();
            isValid = false;
        }

        if(!isValid)
            context.disableDefaultConstraintViolation();

        return isValid;
    }
}
