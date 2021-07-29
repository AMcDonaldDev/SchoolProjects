import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ApiResponse } from '../api-response';
import { AppService } from '../app-service';
import { BasicUser } from '../sign-up/classes/basic-user';
import { DogProfile } from '../sign-up/classes/dog-profile';
import { ValidationService } from '../validation/validation.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public appService: AppService;
  public validationService: ValidationService;

  public currentDog: DogProfile;
  public dogs: Array<DogProfile>;
  public dogForm: FormGroup;

  public user: BasicUser;

  constructor(
    appService: AppService,
    validationService: ValidationService,
    private formBuilder: FormBuilder,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {
    this.appService = appService;
    this.validationService = validationService;
    this.currentDog = new DogProfile();
    this.dogs = new Array<DogProfile>();
    this.user = new BasicUser();
  }

  ngOnInit(): void {
    this.dogForm = this.formBuilder.group({
      dogName: ['', [Validators.pattern('^[a-zA-Z ]{2,30}$'), Validators.min(1), Validators.max(30)]],
      dogBreed: ['', [Validators.pattern('^[a-zA-Z ]{2,30}$'), Validators.min(1), Validators.max(30)]],
      dogBirthday: [''],
      dogGender: ['Male']
    });

    this.appService.login$.subscribe(
      (user) => {
        this.user = user;
        this.updateDogs(this.user.userProfile.dogs);
      },
      (error) => {
        this.router.navigateByUrl("/");
      }
    )
  }

  private updateDogs(dogs: Array<DogProfile>) {
    this.dogs = this.parseDogs(dogs);
    if (this.dogs.length > 0) {
      this.currentDog = this.dogs[0];
    } else {
      this.currentDog = null;
    }
  }

  public addNewDog() {
    if (this.currentDog) {
      try {
        this.validationService.validateDog(this.currentDog);
        this.validationService.validateDogs(this.dogs);
      }
      catch (error) {
        this.handleError(error);
        return;
      }
    }
    let newDog = new DogProfile();
    this.currentDog = newDog;
  }

  public switchDog(dog: DogProfile) {
    this.currentDog = dog;
  }

  private handleError(error: Error) {
    this.displayMessage("Error: " + error.message);
  }

  private handleSuccess(success: string) {
    this.displayMessage(success);
  }

  public saveDog(dog: DogProfile) {
    try {
      dog.validate();
    } catch (error) {
      this.handleError(error);
      return;
    }
    dog = dog.finalize();
    if (this.currentDogIsNew()) {
      // Add new dog
      this.appService.addDog(dog).subscribe(
        (response) => {
          this.parseDogResponse(response);
        },
        (error) => {
          this.handleError(error)
        }
      );
    } else {
      // Update
      this.appService.updateDog(dog).subscribe(
        (response) => {
          this.parseDogResponse(response);
        },
        (error) => {
          this.handleError(error);
        }
      );
    }
  }

  public deleteDog(dog: DogProfile) {
    this.appService.deleteDog(dog.dogName).subscribe(
      (response) => {
        this.parseDogResponse(response);
        if (this.dogs.length > 0) {
          this.currentDog = this.dogs[0];
        } else {
          this.currentDog = null;
        }
      }, (error) => this.handleError(error)
    );
  }

  private parseDogResponse(response: ApiResponse) {
    if (response.statusCode == 200) {
      this.dogs = this.parseDogs(response.message);
      this.handleSuccess("Success!");
    } else {
      this.handleError(new Error(response.message));
    }
  }

  private parseDogs(dogs: any): Array<DogProfile> {
    let parsedDogs = new Array<DogProfile>();
    for (let dog of dogs) {
      parsedDogs.push(new DogProfile().fromJSON(dog));
    }
    return parsedDogs;
  }

  public cancelAddingDog() {
    if (this.dogs.length > 0) {
      this.currentDog = this.dogs[0];
    } else {
      this.currentDog = null;
    }
  }

  public currentDogIsNew(): boolean {
    if (!this.currentDog) return false;
    for (let dog of this.dogs) {
      if (this.currentDog) {
        if (this.currentDog.dogName === dog.dogName) {
          return false;
        }
      }
    }
    return true;
  }

  public displayMessage(msg: string) {
    this._snackBar.open(msg, null, { duration: 2000 });
  }
}
