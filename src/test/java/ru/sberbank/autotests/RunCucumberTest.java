package ru.sberbank.autotests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
        features = "src/test/resources/features",
        glue = "ru.sberbank.autotests.glue")
public class RunCucumberTest {
}
