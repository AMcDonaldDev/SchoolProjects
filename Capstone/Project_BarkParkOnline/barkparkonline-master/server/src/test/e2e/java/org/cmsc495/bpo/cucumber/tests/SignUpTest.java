package org.cmsc495.bpo.cucumber.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.cmsc495.bpo.cucumber.glue.SpringIntegrationGlue;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber.json"},
        features = "src/test/resources/features/SignUpTest.feature",
        glue = {"org.cmsc495.bpo.cucumber.glue"}
)
public class SignUpTest extends SpringIntegrationGlue {
    
}
