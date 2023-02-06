package Controller.User;

import Serialization.User.Roles;
import Serialization.User.User;
import Utils.Constants;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;

public class FindUser {
    private static String token;

    @BeforeClass
    public static void setUp() {
        baseURI = Constants.url + Constants.endpoint_user;
        token = Constants.token;
    }

    public int createUser() {
        ArrayList<Roles> roles = new ArrayList<Roles>();
        roles.add(new Roles(1, "adm"));
        User root = new User(
                true,
                Constants.getCurrentDate() + "test@Dellteam.com",
                Constants.getCurrentDate() + "test",
                roles
        );
        int id = given()
                .contentType(ContentType.JSON)
                .body(root)
                .header("Authorization", token)
                .when()
                .post("")
                .then().log().all()
                .assertThat().statusCode(201).extract().path("id");
        return id;
    }
    
    public static void DeleteUser(User user, int id){
        user.setActive(false);
        int idE = AuxiliaryCreateUser.PutUser(user, id);
        AuxiliaryCreateUser.deleteUser(idE);

    }


    @Test
    public void findUserWithValidId() {
        User userAuxiliary = AuxiliaryCreateUser.CreateUser(true, 1, "Administrator");
        int id = AuxiliaryCreateUser.PostUser(userAuxiliary);
        given()
                .header("Authorization", Constants.token)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200);
        DeleteUser(userAuxiliary,id);
    }

    @Test
    public void tryToFindUserWithNegativeID(){
        given()
                .header("Authorization", Constants.token)
                .contentType(ContentType.JSON)
                .pathParam("id", -10)
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void tryToFindUserWithLettersID(){
        given()
                .header("Authorization", Constants.token)
                .contentType(ContentType.JSON)
                .pathParam("id", "letters")
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(400);
    }

    @Test
    public void tryToFindUserWithNonExistentID(){
        given()
                .header("Authorization", Constants.token)
                .contentType(ContentType.JSON)
                .pathParam("id", 1000000000)
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void TryToFindUserWithoutAuthorization(){
        given()
                .header("Authorization", "invalid token")
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .contentType(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(401);
    }


}
