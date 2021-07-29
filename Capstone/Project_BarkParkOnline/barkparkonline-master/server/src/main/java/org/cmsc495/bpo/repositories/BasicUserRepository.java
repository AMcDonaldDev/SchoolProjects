package org.cmsc495.bpo.repositories;

import com.mongodb.client.result.DeleteResult;

import org.cmsc495.bpo.dao.BasicUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class BasicUserRepository implements CrudRepository<BasicUser> {
    protected static final Logger log = LoggerFactory.getLogger(BasicUserRepository.class);

    private MongoTemplate mongo;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public BasicUserRepository(MongoTemplate mongo, PasswordEncoder passwordEncoder) {
        this.mongo = mongo;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Creates a BASIC USER within the BasicUser Repository. If the BasicUser passed into
     * this argument is not of type BASIC USER, then this method will return null
     * and the BasicUser will not be saved to the database.
     * 
     * @param user
     * @return
     */
    @Override
    public BasicUser create(BasicUser user) {
        if (retrieve(user.getUsername()) != null) {
            throw new IllegalArgumentException("A User with the username " + 
                user.getUsername() + " already exists. If you wish to override " + 
                "this User's database object, use update() instead.");
        }

        return finalizeAndSaveUser(user);
    }

    @Override
    public BasicUser retrieve(String username) {
        BasicUser user = mongo.findOne(queryByUsername(username), BasicUser.class);
        if (user == null) {
            // Try email
            user = mongo.findOne(queryByEmail(username), BasicUser.class);
        }
        return user;
    }

    @Override
    public void update(BasicUser user) {
        mongo.save(user);
    }

    @Override
    public DeleteResult delete(BasicUser user) {
        return mongo.remove(user);
    }

    @Override
    public DeleteResult delete(String username) {
        return mongo.remove(queryByUsername(username), BasicUser.class);
    }

    protected Query queryByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("credentials.username").is(username));
        return query;
    }

    protected Query queryByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("credentials.email").is(email));
        return query;
    }

    /**
     * Finalize by encoding the password, then saving to the Mongo Database
     * 
     * @param user
     */
    protected BasicUser finalizeAndSaveUser(BasicUser user) {
        String rawPassword = user.getCredentials().getPassword();
        user.getCredentials().setPassword(passwordEncoder.encode(rawPassword));
        return mongo.save(user);
    }
}
