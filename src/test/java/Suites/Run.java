package Suites;

import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

@RunWith(Suite.class)
@SuiteClasses({RunModule.class})

public class Run{

}
