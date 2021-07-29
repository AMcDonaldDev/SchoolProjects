package org.cmsc495.bpo.cucumber.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.cmsc495.bpo.cucumber.glue.SpringIntegrationGlue;
import org.junit.runner.RunWith;

/**
 * This is the driver for running End to End (E2E) tests with Cucumber.
 * 
 * All Feature files from the resources directory will be loaded and tested
 * against the available Step Definitions within CucumberStepDefinitions.java
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber.json"},
        features = "src/test/resources/features/LoginTest.feature",
        glue = {"org.cmsc495.bpo.cucumber.glue"}
)
public class LoginTest extends SpringIntegrationGlue {
}