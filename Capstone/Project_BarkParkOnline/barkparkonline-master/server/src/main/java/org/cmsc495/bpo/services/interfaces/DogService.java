package org.cmsc495.bpo.services.interfaces;

import java.util.Collection;

import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.exceptions.DogNotFoundException;
import org.cmsc495.bpo.exceptions.DuplicateDogException;

/**
 * A DogService that performs basic tasks related to Dog Management or the like.
 */
public interface DogService {
    
    /**
     * Add a Dog Profile (Add to User Profile)
     * 
     * @param user
     * @param dog
     */
    public Collection<DogProfile> addDogs(BasicUser user, DogProfile... dog) throws DuplicateDogException;

    /**
     * Remove a Dog Profile that matches the given name
     * (Remove them from User Profile)
     * 
     * @param user
     * @param dog
     * @return
     */
    public Collection<DogProfile> removeDog(BasicUser user, String dogName) throws DogNotFoundException ;
    
    /**
     * Returns all Dogs belonging to the given user(s)
     * 
     * @return
     */
    public Collection<DogProfile> getDogProfiles(String... usernames);

    /**
     * Edit a Dog Profile that matches the given name
     * 
     * @param user
     * @param dogName
     * @param newDogProfile
     * @return Updated Dog Profile
     */
    public Collection<DogProfile> updateDogProfile(BasicUser user, String dogName, 
        DogProfile newDogProfile) throws DogNotFoundException;
}
