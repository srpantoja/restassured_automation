package Controller.User;

import Serialization.User.Roles;
import Serialization.User.User;
import Utils.Constants;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;

import java.util.ArrayList;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class AuxiliaryCreateUser {
    @BeforeClass
    public static void setUp() {
        baseURI = Constants.url + Constants.endpoint_user;
    }

    String token = Constants.token;


    public static int addCreateGetID(Boolean status) {
        Roles r1 = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(status, Constants.getCurrentDate() + "user45@test.com", Constants.getCurrentDate() + "User45", roles);
        int idDelete = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .log().all()
                .statusCode(201).extract().path("id");

        return idDelete;
    }

    public static User CreateUser(Boolean status, int id_role, String role) {
        Roles r1 = new Roles(id_role, role);
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(status, Constants.getCurrentDate() + "user@dellteam.com", Constants.getCurrentDate() + "User", roles);
        return user;
    }

    public static int PostUser(User user) {
        int id = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(201).extract().path("id");
        return id;
    }


    public static int PutUser(User user, int id) {
        int idE = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user).pathParam("id", id)
                .when().put("/{id}")
                .then()
                .statusCode(200).extract().path("id");
        return idE;
    }

    public static void deleteUser(int id){
        given().header("Authorization", Constants.token).pathParam("id", id)
                .when().delete("/{id}")
                .then().statusCode(200);
    }
    public static int createBasicUser(){
        User user = CreateUser(true, 1, "Administrator");
        int id = AuxiliaryCreateUser.PostUser(user);
        return id;
    }
}
