package tests.hooks;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import org.junit.AssumptionViolatedException;

import java.util.ArrayList;

public class CucumberScenariosHook {
    @Before
    public void before(final Scenario scenario) throws Exception {
        final ArrayList<String> scenarioTags = new ArrayList<>();
        scenarioTags.addAll(scenario.getSourceTagNames());

        if (checkForSkipScenario(scenarioTags)) {
            throw new AssumptionViolatedException("This scenario marked as @skipped");
        }
    }

    private boolean checkForSkipScenario(final ArrayList<String> scenarioTags) {
        // I use a tag "@Feature-01AXX" on the scenarios which needs to be run when the feature is enabled on the appliance/application
        if (scenarioTags.contains("@skipped")) { // if feature is not enabled, then we need to skip the scenario.
            return true;
        }
        return false;
    }
}
