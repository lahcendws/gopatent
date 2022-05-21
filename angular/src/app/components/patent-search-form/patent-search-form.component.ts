import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { QueryService } from 'src/app/services/query.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-patent-search-form',
  templateUrl: './patent-search-form.component.html',
  styleUrls: ['./patent-search-form.component.scss']
})

export class PatentSearchFormComponent implements OnInit {

  /* GS: Instance to track the value and validity state of a group of Angular form control instances. */
   searchFormGroup: FormGroup = new FormGroup({
     keywords: new FormControl(''),
     applicant: new FormControl(''),
     publicationDateStart: new FormControl(''),
     publicationDateEnd: new FormControl(''),
   });
   _nbMaxLength: number = 255;
   keywords : any = "";

  /* GS: Injecting FormBuilder to be able to use its instance to handle form */
  constructor(private formBuilder: FormBuilder, private queryService: QueryService, private router: Router) {}

  /*  function validation keywords or applicant is required */
  isRequiredValidator(c:AbstractControl): ValidationErrors | null {
    if(c.get("keywords")?.value || c.get("applicant")?.value || c.get("publicationDateStart")?.value || c.get("publicationDateEnd")?.value){
      return null;
    }else {
      return {'isRequired':true};
    }
  }

  /* Function to limit the publication date in the form */
  minDateValidator(minDate: Date | null): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      // parse control value to Date
      const publicationDate = new Date(control.value);
      // check if control value is superior to date given in parameter
      if (minDate != null && publicationDate.getTime() <= new Date(minDate).getTime()) {
        return null;
      } else {
        // 'min' is the error key, containing 2 values: from input and the min date
        return {'min': {value: control.value, expected: minDate}};
      }
    }
  }

  ngOnInit(): void {
    this.searchFormGroup = this.formBuilder.group({
      keywords: ['', [Validators.maxLength(this._nbMaxLength),this.isRequiredValidator]],
      applicant: ['', [Validators.maxLength(this._nbMaxLength),this.isRequiredValidator]],
      publicationDateEnd: [ null, [this.minDateValidator(new Date()),this.isRequiredValidator]],
      publicationDateStart: [ null, [this.minDateValidator(this.searchFormGroup.value.publicationDateEnd),this.minDateValidator(new Date()),this.isRequiredValidator]],
    },{validators:[this.isRequiredValidator]})
  }

  /* Function to be used after the form submission */
  onsubmit() {
    const{ keywords, applicant, publicationDateStart, publicationDateEnd } = this.searchFormGroup.value;
    this.queryService.queryData(keywords, applicant, publicationDateStart, publicationDateEnd);
    this.router.navigate(['/resultats-recherche']);
  }
}
