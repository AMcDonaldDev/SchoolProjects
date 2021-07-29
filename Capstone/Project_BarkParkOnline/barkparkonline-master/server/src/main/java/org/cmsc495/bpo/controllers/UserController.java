package org.cmsc495.bpo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.cmsc495.bpo.controllers.response.ApiResponse;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.exceptions.DuplicateUserException;
import org.cmsc495.bpo.exceptions.DuplicateDogException;
import org.cmsc495.bpo.exceptions.UserNotFoundException;
import org.cmsc495.bpo.security.Permissions;
import org.cmsc495.bpo.services.interfaces.UserService;
import org.cmsc495.bpo.util.StringUtility;
import org.cmsc495.bpo.services.interfaces.DogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Here, we define a REST Controller that handles all HTTP requests related to
 * an End User
 */
@Controller()
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    protected static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private DogService dogService;

    /**
     * Autowire tells Spring Boot to find and automatically import the required
     * class implementations in order to construct this class. For example, Spring
     * Boot will find our implementation of UserService and autowire it to this
     * instance.
     */
    @Autowired
    public UserController(UserService userService, DogService dogService) {
        this.userService = userService;
        this.dogService = dogService;
    }

    /**
     * This endpoint returns a User's Profile when they login.
     */
    @GetMapping(path = "/user/login")
    public ResponseEntity<?> userLogin(BasicUser user, HttpServletRequest request) {
        Object body = cleanse(user);
        userService.login(user);

        // Return a response to the Client
        return ResponseEntity.ok().body(body);
    }

    /**
     * Cleanse the password so that it doesn't return in an HTTP response
     * 
     * @param user
     * @return
     */
    protected BasicUser cleanse(BasicUser user) {
        BasicUser cleansed = user.copy();
        cleansed.getCredentials().setPassword(null);
        return cleansed;
    }

    /**
     * This endpoint accepts a '@Valid' User only. The User is then added to the current 
     * implementation of UserService.
     * 
     * @param user
     * @return
     */
    @PostMapping(path = "/user/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody(required = true) BasicUser user) {

        // Try to add the given User to the User Service
        try {
            userService.addUser(user);
            return ResponseEntity.ok().build();

        // An exception may be thrown if there is a database error
        } catch (DuplicateUserException ex) {
            log.error(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Unknown error occurred. User " + user.getCredentials().getUsername() + " was not signed up");
    }

    @PutMapping(path = "/user/{username}/update")
    public ResponseEntity<?> update(
        @PathVariable String username, 
        BasicUser requester, 
        @RequestBody(required = true) BasicUser updateDefinition
    ) {
        User userToUpdate = requester;
        log.info("{} USER [{}] is attempting to update USER [{}]",
            requester.getType(),
            requester.getUsername(), 
            username);

        // Security check
        try {
            log.info("{} USER [{}] has permissions to EDIT: {}", 
                    requester.getType(), 
                    requester.getUsername(),
                    userHasPermissionsToEdit(requester)
                );
            if ( userHasPermissionsToEdit(requester)&& username.equals(requester.getUsername()) ) {
                // ok
            } else if ( userHasAdminPermissions(requester) ) {
                // ok
                userToUpdate = userService.getUser(username);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch(Exception ex) {
            // Endpoint is authenticated and we should never be able to get here
            log.error(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            // Update Credentials
            if (updateDefinition.getCredentials() != null) {
                userService.updateUserCredentials(userToUpdate, updateDefinition.getCredentials());
                userService.logout(userToUpdate);
                log.info("Credentials for {} USER [{}] was updated by {} USER [{}]", 
                    userToUpdate.getType(),
                    userToUpdate.getUsername(),
                    requester.getType(),
                    requester.getUsername()
                );
            }
            // Update Profile 
            if (updateDefinition.getUserProfile() != null) {
                userService.updateUserProfile(userToUpdate, updateDefinition.getUserProfile());
                log.info("Profile for {} USER [{}] was updated by {} USER [{}]", 
                    userToUpdate.getType(),
                    userToUpdate.getUsername(),
                    requester.getType(),
                    requester.getUsername()
                );
            }
            return ResponseEntity
                .status(HttpStatus.OK).build();
                
        } catch(UserNotFoundException ex) {
            log.warn(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User %s not found.", username));
        
        } catch(ValidationException ex) {
            log.warn("Failed to update {} due to {}", username, ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getLocalizedMessage());
        
        } catch(Exception ex) {
            log.error(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This endpoint accepts a '@Valid' DogProfile only. The DogProfile is then added to the current BasicUser
     * using the DogService. The BasicUser is saved using the UserService.
     * 
     * @param dog
     * @return
     */
    @PostMapping(path = "/user/dog/add")
    public ApiResponse addDog(
        BasicUser user, 
        @Valid @RequestBody(required = true) DogProfile dog
    ) {
        // Try to get the BasicUser for the given user, then return it for use of adding dog
        try {
            ApiResponse response = ApiResponse.ok(dogService.addDogs(user, dog));
            log.info("User [{}] added a new dog named {}", user.getUsername(), dog.getDogName());
            return response;

        } catch (DuplicateDogException e) {
            final String MESSAGE = "User [{}] was unable to add {} because: {}";
            log.error(MESSAGE, 
                user.getUsername(), dog.getDogName(), e.getLocalizedMessage());
            return ApiResponse.error(409, StringUtility.debrace(MESSAGE,
                user.getUsername(), dog.getDogName(), e.getLocalizedMessage()));
        }
    }

    protected boolean userHasPermissionsToEdit(User user) {
        return User.hasPermissions(user, Permissions.EDIT);
    }

    protected boolean userHasAdminPermissions(User user) {
        return User.hasPermissions(user, Permissions.ADMIN);
    }

    /**
     * This endpoint updates a current DogProfile. The DogProfile is then updated to the current BasicUser
     * using the DogService. The BasicUser is updated using the UserService.
     * @param user
     * @param updateDogProfile
     * @return
     */

    @PutMapping(path = "/user/dog/update")
    public ApiResponse updateDog(BasicUser user, @Valid @RequestBody(required = true) DogProfile updateDogProfile) {
        String dogName = updateDogProfile.getDogName();
        try {
            ApiResponse response = ApiResponse.ok(
                dogService.updateDogProfile(user, dogName, updateDogProfile)
            );
            log.info("User [{}] updated {}'s Dog Profile", user.getUsername(), dogName);
            return response;

        } catch (Exception e) {
            final String MESSAGE = "User [{}] was unable to update {} because: {}";
            log.error(MESSAGE, 
                user.getUsername(), dogName, e.getLocalizedMessage());
            return ApiResponse.error(400, StringUtility.debrace(MESSAGE, 
                user.getUsername(), dogName, e.getLocalizedMessage()));
        }
    }

    @DeleteMapping(path = "/user/dog/delete/{dogName}")
    public ApiResponse deleteDog(BasicUser user, @PathVariable String dogName) {
        try {
            ApiResponse response = ApiResponse.ok(dogService.removeDog(user, dogName));
            return response;

        } catch (Exception e) {
            final String MESSAGE = "User [{}] was unable to remove {} because: {}";
            log.error(MESSAGE, 
                user.getUsername(), dogName, e.getLocalizedMessage());
            return ApiResponse.error(400, StringUtility.debrace(MESSAGE,
                user.getUsername(), dogName, e.getLocalizedMessage()));
        }
    }
}
