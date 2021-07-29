package org.cmsc495.bpo.controllers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.result.DeleteResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cmsc495.bpo.dao.Credentials;
import org.cmsc495.bpo.dao.DogProfile;
import org.cmsc495.bpo.Application;
import org.cmsc495.bpo.dao.BasicUser;
import org.cmsc495.bpo.dao.UserProfile;
import org.cmsc495.bpo.dao.interfaces.User;
import org.cmsc495.bpo.repositories.BasicUserRepository;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

/**
 * Test with: gradle e2e --tests UserControllerWebTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UserControllerWebTest {

    protected static final Logger log = LoggerFactory.getLogger(UserControllerWebTest.class);

    private final static String USERNAME = "powpowpow";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BasicUserRepository userRepo;

    StatusResultMatchers status = MockMvcResultMatchers.status();

    private static BasicUser getDummyUser() {
        Set<DogProfile> dogs = new HashSet<DogProfile>();
            dogs.add(new DogProfile(
                "Barky",
                "German Shephard",
                LocalDate.of(2018, 1, 1),
                DogProfile.Gender.MALE
            ));
        UserProfile profile = new UserProfile(
            "thisthis", 
            "fakestring", 
            "123-456-7890", 
            dogs, 
            "http://localhost:8080/file.png",
            new HashSet<>()
        );
        Credentials credentials = new Credentials("tenpowpowpow", USERNAME, "thispow@example.com", "lastone");
        credentials.setPassword("secretpassword");
        BasicUser user = new BasicUser();
        user.setUserProfile(profile);
        user.setCredentials(credentials);
        return user;
    }

    private ObjectWriter getWriter() {
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("  ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);
        return mapper.writer(printer);
    }

    private String toJSON(Object o) throws JsonProcessingException {
        return getWriter().writeValueAsString(o);
    }

    private MockHttpServletRequestBuilder post(String url) {
        return MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder put(String url) {
        return MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder get(String url) {
        return MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder withUserAuth(MockHttpServletRequestBuilder builder, User user) {
        String auth = String.format("%s:%s", user.getCredentials().getUsername(), user.getCredentials().getPassword());
        Base64.Encoder enc = Base64.getEncoder();
        String b64Auth = enc.encodeToString(auth.getBytes());
        return builder.header("Authorization", "Basic " + b64Auth);
    }

    @Before
    public void init() {
        DeleteResult result = userRepo.delete(USERNAME);
        log.info("BEFORE {}", result.toString());
    }

    @Test
    public void testSignUp_httpValidUser() throws Exception {
        BasicUser user = getDummyUser();
        String url = "/user/signup";
        String json = toJSON(user);

        log.info("POST {} {}", url, json);
        MvcResult res = mvc.perform(post(url).content(json)).andExpect(status.isOk()).andReturn();
        String body = res.getResponse().getContentAsString();
        log.info("USER {}", body);
    }

    @Test
    public void duplicateUserSignup() throws Exception {

        BasicUser user = getDummyUser();

        String url = "/user/signup";
        String json = toJSON(user);

        log.info("POST {} {}", url, json);
        mvc.perform(post(url).content(json)).andExpect(status.isOk());
        mvc.perform(post(url).content(json)).andExpect(status.isConflict());
    }

    @Test
    public void testUser_loginDummyUser() throws Exception {
        BasicUser user = getDummyUser();
        String username = user.getCredentials().getUsername();

        mvc.perform(post("/user/signup").content(toJSON(user))).andExpect(status.isOk());

        mvc.perform(withUserAuth(get("/user/login"), user).param("username", username))
            .andExpect(status.isOk());
    }

    @Test
    public void testUser_updatingAnotherUser() throws Exception {
        BasicUser user = getDummyUser();
        String username = user.getCredentials().getUsername();

        mvc.perform(post("/user/signup").content(toJSON(user))).andExpect(status.isOk());

        mvc.perform(withUserAuth(get("/user/login"), user).param("username", username))
            .andExpect(status.isOk());

        String url = "/user/someOtherUsername/update";
        String res = mvc.perform(withUserAuth(put(url), user).content(toJSON(user)) )
            .andExpect(status.isUnauthorized()).andReturn().getResponse().getContentAsString();

        // Basic auth check doesn't get to the user lookup failed check unless admin user
        log.info("401: {}", res);
        // assertEquals(res, "User someOtherUsername not found.");
    }

    @Test
    public void testUser_updateUserCredentials() throws Exception {
        BasicUser user = getDummyUser();
        String username = user.getCredentials().getUsername();
        String url = String.format("/user/%s/update", username);

        mvc.perform(post("/user/signup").content(toJSON(user))).andExpect(status.isOk());

        mvc.perform(withUserAuth(get("/user/login"), user).param("username", username))
            .andExpect(status.isOk());

        String newPassword = "newPassWord";
        String oldPassword = user.getCredentials().getPassword();

        // Set to new password so we can make some JSON to PUT
        user.getCredentials().setPassword(newPassword);
        String userJson = toJSON(user);

        // Reset password to make an authorized request
        user.getCredentials().setPassword(oldPassword);

        // PUT update
        mvc.perform(withUserAuth(put(url), user).content(userJson)).andExpect(status.isOk());

        log.info("Logging in with OLD password ...");
        // Check that old credentials are not unauthorized
        mvc.perform(withUserAuth(get("/user/login"), user).param("username", username))
          .andExpect(status.isUnauthorized());

        // Set to updated password to make an authorized request
        user.getCredentials().setPassword(newPassword);

        log.info("Logging in with NEW password ...");
        // Make an authorized request with new credentials to check that the update worked
        mvc.perform(withUserAuth(get("/user/login"), user).param("username", username))
          .andExpect(status.isOk());
    }

    @Test
    public void testUser_updateSomeUserProfileFields() throws Exception {
        BasicUser user = getDummyUser();
        String username = user.getCredentials().getUsername();
        String url = String.format("/user/%s/update", username);

        mvc.perform(post("/user/signup").content(toJSON(user))).andExpect(status.isOk());

        mvc.perform(withUserAuth(get("/user/login"), user).param("username", username))
            .andExpect(status.isOk());

        String newFirstName = "newFristName";
        String newLastName = "newLastName";
        String newProfilePhotoUrl = "http://newProfilePhotoUrl.com/image.jpg";
        String json = new JSONObject()
            .put("userProfile", new JSONObject()
                .put("firstName", newFirstName)
                .put("lastName", newLastName)
                .put("profilePhotoUrl", newProfilePhotoUrl))
            .toString();
        // PUT update - change several profile fields
        mvc.perform(withUserAuth(put(url), user).content(json)).andExpect(status.isOk());
        UserProfile profile = userRepo.retrieve(username).getUserProfile();
        assertEquals(newFirstName, profile.getFirstName());
        assertEquals(newLastName, profile.getLastName());
        assertEquals(newProfilePhotoUrl, profile.getProfilePhotoUrl());
    }

    @After
    public void cleanup() {
        DeleteResult result = userRepo.delete(USERNAME);
        log.info("AFTER {}", result.toString());
    }
}
