import { Injectable } from '@angular/core';
import { DogProfile } from '../sign-up/classes/dog-profile';

@Injectable({
  providedIn: 'root',
})
export class ValidationService {

  constructor() {}

  public validateDog(dog: DogProfile) {
    dog.validate();
  }

  public validateDogs(dogs: DogProfile []) {

    for (let i = 0; i < dogs.length; i++) {
      try {
        this.validateDog(dogs[i]);

      } catch (error) {
        throw error;
      }
    }
    return true;
  }

}
