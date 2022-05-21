import { AbstractControl, ValidatorFn } from '@angular/forms';

// ValidationRegistration to check that two password match
export default class ValidationRegistration {
  static match(password: string, confirmedPassword: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const control = controls.get(password);
      const checkControl = controls.get(confirmedPassword);

      // if another validator has already found an error on the matchingControl
      if (checkControl?.errors && !checkControl.errors['matching']) {
        return null;
      }

      // set error if validation fails
      if (control?.value !== checkControl?.value) {
        controls.get(confirmedPassword)?.setErrors({ matching: true });
        return { matching: true };
      } else {
        return null;
      }
    };
  }
}
