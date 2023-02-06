package Controller.User;

import Utils.Constants;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GetAllUsers {

    private static String token;

    @BeforeClass
    public static void setUp() {
        baseURI = Constants.url + Constants.endpoint_user;
        token = Constants.token;
    }

    @Test
    public void GetAllUsersSuccessfully(){
        given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .get("")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void GetAllUsersNotAuthorized(){
        given()
                .header("Authorization", "Invalid Token")
                .contentType(ContentType.JSON)
                .when()
                .get("")
                .then()
                .assertThat()
                .statusCode(401);
    }
}
