package Controller.User;

import Serialization.User.Roles;
import Serialization.User.User;
import Utils.Constants;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;

import static io.restassured.RestAssured.*;

public class CreateUser {
    @BeforeClass
    public static void setUp() {
        baseURI = Constants.url + Constants.endpoint_user;
        useRelaxedHTTPSValidation();
    }
    public static void DeleteUser(User user, int id){
        user.setActive(false);
        int idE = AuxiliaryCreateUser.PutUser(user, id);
        AuxiliaryCreateUser.deleteUser(idE);

    }
    @Test
    @DisplayName("Create user with valid data")
    public void CreateUserValidData() {
        Roles r1 = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(true, Constants.getCurrentDate() + "user45@test.com", Constants.getCurrentDate() + "User45", roles);
      Integer id = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(201).extract().path("id");
        user.setActive(false);
        DeleteUser(user, id);
    }

    @Test
    @DisplayName("Create user with field active false")
    public void CreateUserWithFalseActive() {
        Roles r1 = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(false, Constants.getCurrentDate() + "user48@test.com", Constants.getCurrentDate() + "User48", roles);
        Integer id =   given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(201).extract().path("id");
        DeleteUser(user, id);
    }

    @Test
    @DisplayName("Create user with more than one role")
    public void CreateUserWithMoreThanOneRole() {
        Roles r1 = new Roles(1, "Administrator");
        Roles r2 = new Roles(2, "User");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        roles.add(r2);
        User user = new User(false, Constants.getCurrentDate() + "user49@test.com", Constants.getCurrentDate() + "User49", roles);
        Integer id =    given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(201).extract().path("id");
        user.setActive(false);
        DeleteUser(user, id);
    }

    @Test
    @DisplayName("Try to create user with empty name field")
    public void TryToCreateUserWithEmptyName() {
        Roles r1 = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(false, "user48@test.com", "", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Try to create user with empty email field")
    public void TryToCreateUserWithEmptyEmail() {
        Roles r1 = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(false, "", "Nme123", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(400);
    }
    @Ignore
    @Test
    @DisplayName("DSCOPE-T-431 : Try to create user with invalid email format field")
    public void TryToCreateUserWithEmailFormatInvalidEmail() {
        Roles r1 = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(false, "Name@test.com", "Nme123", roles);
      Response response=  given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then().assertThat().extract().response();
        JsonPath json = response.jsonPath();
        DeleteUser(user, response.<Integer>path("id"));
        Assert.assertEquals(400,response.statusCode());
    }

    @Test
    @DisplayName("Try to create user with non-existent role id")
    public void TryToCreateUserWithNonexistentRoleId() {
        Roles r1 = new Roles(100, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(false, "Name123@teste.com", "Name123", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Try to create user with empty request")
    public void TryToCreateUserWithEmptyRequest() {
        given().
                header("Authorization", Constants.token).
                body("{}").
                contentType(ContentType.JSON).
                when().
                post("").
                then().
                assertThat().
                statusCode(400);
    }

    @Test
    @DisplayName("Try to create a user with all the fields already existing")
    public void CreateUserWithFieldsAlreadyExisting() {
        User userAuxiliary = AuxiliaryCreateUser.CreateUser(false, 1, "Administrator");
        int id = AuxiliaryCreateUser.PostUser(userAuxiliary);
        String email=userAuxiliary.getEmail();
        String ntname=userAuxiliary.getName();

        Roles r1 = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(true, email, ntname, roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(400);
        user.setActive(false);
        DeleteUser(user, id);
    }

    @Test
    @DisplayName("Try to create a user with the name field already existing")
    public void CreateUserWithFieldNameAlreadyExisting() {
        User userAuxiliary = AuxiliaryCreateUser.CreateUser(false, 1, "Administrator");
        int id = AuxiliaryCreateUser.PostUser(userAuxiliary);
        String ntname=userAuxiliary.getName();


        Roles r1 = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(true, "user46@test.com", ntname, roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(400);
        user.setActive(false);
        DeleteUser(user, id);

    }

    @Test
    @DisplayName("Try to create a user with the email field already existing")
    public void CreateUserWithFieldEmailAlreadyExisting() {
        User userAuxiliary = AuxiliaryCreateUser.CreateUser(false, 1, "Administrator");
        int id = AuxiliaryCreateUser.PostUser(userAuxiliary);
        String email=userAuxiliary.getEmail();

        Roles r1 = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(r1);
        User user = new User(true, email, "User49", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(user)
                .when().post("")
                .then()
                .statusCode(400);
        user.setActive(false);
        DeleteUser(user, id);
    }


}
