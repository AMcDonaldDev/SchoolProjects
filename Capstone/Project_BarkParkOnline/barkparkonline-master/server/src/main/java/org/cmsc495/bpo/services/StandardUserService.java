package org.cmsc495.bpo.services;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.mongodb.client.result.DeleteResult;

import org.cmsc495.bpo.dao.GuestUser;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.exceptions.DuplicateUserException;
import org.cmsc495.bpo.exceptions.UserNotFoundException;
import org.cmsc495.bpo.repositories.BasicUserRepository;
import org.cmsc495.bpo.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StandardUserService implements UserService {
    protected static final Logger log = LoggerFactory.getLogger(StandardUserService.class);

    private Set<User> currentUsers;

    private BasicUserRepository repo;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public StandardUserService(
        BasicUserRepository repo,
        PasswordEncoder passwordEncoder
    ) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        currentUsers = new HashSet<>();
    }

    @Override
    public UserProfile getProfile(String username) throws UserNotFoundException {
        BasicUser user = repo.retrieve(username);
        try {
            user.getUserProfile().getFullName();
            return user.getUserProfile();

        } catch (NullPointerException npe) {}
        log.warn("USER [{}] does not have a profile or is not a USER", username);
        throw new UserNotFoundException(username);
    }

    @Override
    public User getUser(String username) throws UserNotFoundException {
        User user = repo.retrieve(username);
        if (user == null) throw new UserNotFoundException(username);
        return user;
    }

    @Override
    public void addUser(User user) throws DuplicateUserException {
        if (!(user instanceof BasicUser)) {
            log.warn("USER not added. Currently, there is no repository support for {} USERS.", user.getType());
            return;
        }
        try {
            validateUser(user);
            repo.create((BasicUser) user);
            log.info("{} USER [{}] created", user.getType(), user.getUsername());

        } catch (IllegalArgumentException e) { // Duplicate user
            throw new DuplicateUserException(user.getUsername());

        } catch (ValidationException ve) {
            throw ve;
        }   
    }

    @Override
    public boolean removeUser(User user) {
        if (user instanceof BasicUser) {
            DeleteResult result = this.repo.delete((BasicUser) user);
            logOffUser(user);
            return result.getDeletedCount() > 0;
        }
        logOffUser(user);
        return true;
    }

    /**
     * Logs off the user by removing them from currentUsers
     * 
     * @param username
     */
    protected void logOffUser(User user) {
        this.currentUsers.remove(user);
    }

    @Override
    public void login(User user) {
        final String HAS_LOGGED_IN = "has logged in";
        final String WAS_ALREADY_LOGGED_IN = "was already logged in";
        String msg = WAS_ALREADY_LOGGED_IN;

        if (this.currentUsers.add(user)) msg = HAS_LOGGED_IN;

        if (user instanceof GuestUser) {
            log.info("{} USER [{}] {}", user.getType(), user.getUsername(), msg);
        }
        else { 
            log.info("{} USER [{}] {}", user.getType(), user.getUsername(), msg); 
        }
    }

    @Override
    public void logout(User user) {
        final String HAS_LOGGED_IN = "has logged out";
        final String WAS_ALREADY_LOGGED_IN = "was already logged in";
        String msg = WAS_ALREADY_LOGGED_IN;

        if (this.currentUsers.remove(user)) msg = HAS_LOGGED_IN;

        if (user instanceof GuestUser) {
            log.info("{} USER [{}] {}", user.getType(), user.getUsername(), msg);
        }
        else { 
            log.info("{} USER [{}] {}", user.getType(), user.getUsername(), msg); 
        }
        logOffUser(user);
    }

    @Override
    public Set<User> getCurrentUsers() {
        return this.currentUsers;
    }

    @Override
    public User updateUserProfile(User user, UserProfile newProfile) {
        if (!(user instanceof BasicUser)) {
            log.warn("{} USERS do not have Profiles. Therefore, updateUserProfile() has no effect.", 
                user.getType());
                
            return user;
        }
        UserProfile updatedProfile = user.getUserProfile(); // using pointer

        String firstName = newProfile.getFirstName();
        if ( firstName != null ) updatedProfile.setFirstName(firstName);
        String lastName = newProfile.getLastName();
        if ( lastName != null ) updatedProfile.setLastName(lastName);
        String phoneNumber = newProfile.getPhoneNumber(); 
        if ( phoneNumber != null ) updatedProfile.setPhoneNumber(phoneNumber);
        String profilePhotoUrl = newProfile.getProfilePhotoUrl();
        if ( profilePhotoUrl != null ) updatedProfile.setProfilePhotoUrl(profilePhotoUrl);

        validateUser(user);
        repo.update((BasicUser) user);
        return user;
    }

    @Override
    public User updateUserCredentials(User user, Credentials credentials) {
        if (!(user instanceof BasicUser)) {
            log.warn("{} USERS do not have Profiles. Therefore, updateUserProfile() has no effect.", 
                user.getType());
            return user;
        }
        ((BasicUser) user).setCredentials(credentials);
        validateUser(user);
        user.getCredentials().setPassword( // Encode password
            passwordEncoder.encode(user.getCredentials().getPassword())
        );
        repo.update((BasicUser) user);
        return user;
    }

    /** Stubbing */
    protected void validateUser(User user) { UserValidator.validate(user); }

    /**
     * Validator for validating a BasicUser
     */
    protected static class UserValidator {
        protected static void validate(User user) throws ValidationException {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            for (ConstraintViolation<User> violation : violations) {
                log.warn("{} USER [{}] : {} for {}", user.getType(), user.getUsername(), violation.getMessage(), violation.getPropertyPath());
            }
            if (violations.size() > 0) 
                throw new ValidationException(user.getType() + 
                " USER [" + user.getUsername() + "] had " + 
                violations.size() + " invalid fields");
        }
    }
}
