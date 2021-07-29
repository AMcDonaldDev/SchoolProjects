import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AppService } from '../app-service';
import { environment as env } from '../../environments/environment';
import { Credentials } from './classes/credentials';
import { UserProfile } from './classes/user-profile';
import { DogProfile } from './classes/dog-profile';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { passwordsMatch } from '../validation/validators';
import { BasicUser } from './classes/basic-user';
import { setInputFilter } from './phone-number-input/phone-number-input.component'
import { ValidationService } from '../validation/validation.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  public appService: AppService;
  public validationService: ValidationService;

  public dogs = new Array<DogProfile>();
  public errorMessage: string = "";
  public currentDog = new DogProfile();

  public firstFormGroup: FormGroup;
  public secondFormGroup: FormGroup;
  public thirdFormGroup: FormGroup;

  // Hide or show errors
  public showErrors: boolean = false;

  // Hide Password
  public hide: boolean = true;

  public user: BasicUser;

  constructor(
    private formBuilder: FormBuilder,
    appService: AppService,
    validationService: ValidationService,
    private router: Router
    ) {
    this.appService = appService;
    this.validationService = validationService;
    this.user = new BasicUser();
    this.dogs.push(this.currentDog);
  }

  ngOnInit(): void {
    this.appService.login$.subscribe(user => this.user = user);

    this.firstFormGroup = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(60)]],
      passwordCheck: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(60)]]
    },
    {
      validator: passwordsMatch('password', 'passwordCheck')
    });
    this.secondFormGroup = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.pattern("^[a-zA-Z ]{2,30}$"), Validators.min(1), Validators.max(30)]],
      lastName: ['', [Validators.required, Validators.pattern("^[a-zA-Z ]{2,30}$"), Validators.min(1), Validators.max(30)]],
      phoneNumber: [null],
    });
    this.thirdFormGroup = this.formBuilder.group({
      dogName: ['', [Validators.pattern('^[a-zA-Z ]{2,30}$'), Validators.min(1), Validators.max(30)]],
      dogBreed: ['', [Validators.pattern('^[a-zA-Z ]{2,30}$'), Validators.min(1), Validators.max(30)]],
      dogBirthday: [''],
      dogGender: ['Male']
    });
  }

  public firstGroupFinished(): boolean {
    return this.firstFormGroup.get('username').touched &&
      this.firstFormGroup.get('email').touched &&
      this.firstFormGroup.get('password').touched &&
      this.firstFormGroup.get('passwordCheck').touched
  }

  public secondGroupFinished(): boolean {
    return this.secondFormGroup.get('firstName').touched &&
      this.secondFormGroup.get('lastName').touched
  }

  public thirdGroupFinished(): boolean {
    return this.thirdFormGroup.get('dogName').touched &&
      this.thirdFormGroup.get('dogBreed').touched &&
      this.thirdFormGroup.get('dogBirthday').touched
  }

  public keyPressed(event: any) {
    this.showErrors = false;
    if (event.keyCode == 13) {
      this.signUp();
    }
  }

  public signUp() {
    let profile = this.user.userProfile;
    let credentials = this.user.credentials;

    if (credentials.password != credentials.passwordCheck) {
      this.errorMessage = "Passwords don't match";
      return;
    }
    let dogsCopy = new Array<DogProfile>();
    // Finalize/format all components
    for (let dog of this.dogs) {
      try {
        dog.validate();
      } catch (error) {
        this.handleError(error);
        return;
      }
      dogsCopy.push(dog.finalize());
    }

    // Make a copy so that none of the fields are messed up
    // on the form
    let userCopy = new BasicUser();
    userCopy.credentials = credentials.finalize();
    userCopy.userProfile = profile.finalize();
    userCopy.userProfile.dogs = dogsCopy;

    try {
      this.appService.signUp(userCopy);
    } catch (error) {
      this.handleError(error);
    }
    this.appService.login(credentials);
    this.router.navigate(['/calendar']);
  }

  public addNewDog() {
    try {
      this.validationService.validateDog(this.currentDog);
      this.validationService.validateDogs(this.dogs);
    }
    catch (error) {
      this.handleError(error);
      return;
    }
    let newDog = new DogProfile();
    this.dogs.push(newDog);
    this.currentDog = newDog;
  }

  public switchDog(dogName: string) {
    for (let dogSearch of this.dogs) {
      if (dogSearch.dogName == dogName) {
        this.currentDog = new DogProfile().fromJSON(dogSearch);
      }
    }
  }

  private handleError(error: any) {
    this.errorMessage = error.toString().replace("Error:", "");
    this.showErrors = true;
  }

}
