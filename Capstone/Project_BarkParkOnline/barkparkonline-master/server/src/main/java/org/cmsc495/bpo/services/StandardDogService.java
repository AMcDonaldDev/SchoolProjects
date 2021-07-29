package org.cmsc495.bpo.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.exceptions.DogNotFoundException;
import org.cmsc495.bpo.exceptions.DuplicateDogException;
import org.cmsc495.bpo.repositories.BasicUserRepository;
import org.cmsc495.bpo.services.interfaces.DogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandardDogService implements DogService {
    protected static final Logger log = LoggerFactory.getLogger(StandardDogService.class);

    private BasicUserRepository repo;

    @Autowired
    public StandardDogService(BasicUserRepository repo) {
        this.repo = repo;
    }

    /**
     * Adds the given dog(s) to the User's Profile. If any duplicate dogs
     * are discovered this method will throw an exception.
     */
    @Override
    public Collection<DogProfile> addDogs(BasicUser user, DogProfile... dogs) throws DuplicateDogException {
        for (DogProfile dog : dogs) {
            if (user.getUserProfile().getDogs().contains(dog)) {
                throw new DuplicateDogException(user.getUsername());
            }
            try {
                validateDog(dog);
                user.getUserProfile().getDogs().add(dog);

            } catch (ValidationException e) {
                log.warn("Dog [{}] was not added to the User [{}]", dog.getDogName(), user.getUsername());
            }
        }
        this.repo.update(user);
        return user.getUserProfile().getDogs();
    }

    /**
     * Remove the Dog with the given name from the User's Profile, then save changes
     * to the DataBase.
     * 
     * @throws DogNotFoundException
     */
    @Override
    public Collection<DogProfile> removeDog(BasicUser user, String dogName) throws DogNotFoundException {
        try {
            Iterator<DogProfile> dogs = user.getUserProfile().getDogs().iterator();
            while(dogs.hasNext()) {
                if (dogs.next().getDogName().equals(dogName)) {
                    dogs.remove();
                    this.repo.update(user);
                    return user.getUserProfile().getDogs();
                }
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        throw new DogNotFoundException(user.getUsername());
    }

    /**
     * Get all Dogs from the given usernames. If there are no Dogs
     * this method will return an empty list, never null.
     */
    @Override
    public Collection<DogProfile> getDogProfiles(String... usernames) {
        List<DogProfile> dogs = new ArrayList<>();
        for (String user : usernames) {
            dogs.addAll(getDogs(user));
        }
        return dogs;
    }

    /**
     * Utility method to get dogs from a User. This method never returns null
     * or throws an exception. If there is an issue getting a User's dogs,
     * this method will log a warning and return an empty list.
     * 
     * @param username
     * @return
     */
    protected Collection<DogProfile> getDogs(String username) {
        try {
            return repo.retrieve(username).getUserProfile().getDogs();
        }
        catch (Exception e) {
            log.warn("USER [{}] doesn't exist or has a corrupted profile", username);
            return List.of(); // Return empty list
        }
    }

    /**
     * Update the Dog Profile that matches the given dog name
     * 
     * @throws DogNotFoundException
     */
    @Override
    public Collection<DogProfile> updateDogProfile(BasicUser user, String dogName, DogProfile newDogProfile)
            throws DogNotFoundException {
        String username = user.getUsername();

        Iterator<DogProfile> iterator = user.getUserProfile().getDogs().iterator(); // *
        while(iterator.hasNext()) {
            DogProfile dog = iterator.next(); // *
            if (dog.getDogName().equals(dogName)) {
                validateDog(newDogProfile);
                dog.setDogName(newDogProfile.getDogName());
                dog.setDogBreed(newDogProfile.getDogBreed());
                dog.setDogDob(newDogProfile.getDogDob());
                dog.setDogGender(newDogProfile.getDogGender());
                repo.update(user);
                return user.getUserProfile().getDogs();
            }
        }
        throw new DogNotFoundException(username);
    }

    /** Stubbing */
    protected void validateDog(DogProfile dog) {
        UserValidator.validate(dog);
    }

    /**
     * Validator for validating a BasicUser
     */
    protected static class UserValidator {
        protected static void validate(DogProfile dog) throws ValidationException {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DogProfile>> violations = validator.validate(dog);
            for (ConstraintViolation<DogProfile> violation : violations) {
                log.warn("DOG [{}] : {}", dog.getDogName(), violation.getMessage());
            }
            if (violations.size() > 0)
                throw new ValidationException("DOG [" + dog.getDogName() + "] had "
                        + violations.size() + " invalid fields");
        }
    }
}
