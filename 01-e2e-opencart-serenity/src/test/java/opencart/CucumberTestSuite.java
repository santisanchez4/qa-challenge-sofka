package opencart;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/** Runner: ejecuta todos los .feature de src/test/resources/features. */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/features")
public class CucumberTestSuite {
}
