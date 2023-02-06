package Controller.User;

import Serialization.User.Roles;
import Serialization.User.User;
import Utils.Constants;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;

import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;


public class EditUser {
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

    @DisplayName("DSCOPE-T-458 : edit a user's status to active successfully")
    @Test
    public void editUserToActive(){
      int id = AuxiliaryCreateUser.createBasicUser();
       User userEdit = AuxiliaryCreateUser.CreateUser(true, 1, "Administrator");
       Response response = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
               .when().put("/{id}")
               .then().assertThat().statusCode(200).extract().response();
       JsonPath json = response.jsonPath();
       Assert.assertTrue(json.getBoolean("active"));
       DeleteUser(userEdit, json.getInt("id"));

    }
    @DisplayName("DSCOPE-T-459 : edit a user's status to disable successfully")
    @Test
    public void editUserToDisabled(){
        int id = AuxiliaryCreateUser.createBasicUser();
        User userEdit = AuxiliaryCreateUser.CreateUser(false, 1, "Administrator");
        Response response = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(200).extract().response();
        JsonPath json = response.jsonPath();
        Assert.assertFalse(json.getBoolean("active"));
        DeleteUser(userEdit, json.getInt("id"));

    }

    @DisplayName("DSCOPE-T-460 : edit user email successfully ")
    @Test
    public void editUserEmailSuccessfully(){
        int id = AuxiliaryCreateUser.createBasicUser();
        User userEdit = AuxiliaryCreateUser.CreateUser(false, 1, "Administrator");
        String email = Constants.getCurrentDate() +"@dellteam.com";
        userEdit.setEmail(email);
        Response response = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(200).extract().response();
        JsonPath json = response.jsonPath();
        Assert.assertEquals(json.getString("email"), email);
        DeleteUser(userEdit, json.getInt("id"));

    }

    @DisplayName("DSCOPE-T-461 : edit nt username successfully")
    @Test
    public void editUserNTNameSuccessfully(){
        int id = AuxiliaryCreateUser.createBasicUser();
        User userEdit = AuxiliaryCreateUser.CreateUser(false, 1, "Administrator");
        String name = Constants.getCurrentDate() +"Name";
        userEdit.setName(name);
        Response response = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(200).extract().response();
        JsonPath json = response.jsonPath();
        Assert.assertEquals(json.getString("name"), name);
        DeleteUser(userEdit, json.getInt("id"));

    }

    @DisplayName("DSCOPE-T-462 : edit user role successfully")
    @Test
    public void editUserRoleSuccessfully(){
        int id = AuxiliaryCreateUser.createBasicUser();
        User userEdit = AuxiliaryCreateUser.CreateUser(false, 2, "User");
        Response response = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(200).extract().response();
        JsonPath json = response.jsonPath();
        Assert.assertEquals(json.getString("roles[0].name"), "User");
        DeleteUser(userEdit, json.getInt("id"));

    }
@Ignore
    @DisplayName("DSCOPE-T-463 : try editing user status to empty ")
    @Test
    public void tryEditStatusToEmpty(){
        int id = AuxiliaryCreateUser.createBasicUser();
        Roles role = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(role);
        User userEdit = new User(Constants.getCurrentDate() + "@dellteam.com", Constants.getCurrentDate() + "User", roles);
       Response response = given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().extract().response();
        userEdit.setActive(false);
        DeleteUser(userEdit, id);
        Assert.assertEquals(400,response.statusCode());
    }

    @DisplayName("DSCOPE-T-464 : try editing user email to empty ")
    @Test
    public void tryEditEmailToEmpty(){
        int id = AuxiliaryCreateUser.createBasicUser();
        Roles role = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(role);
        User userEdit = new User(true, "",  Constants.getCurrentDate() + "User", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(400);
        userEdit.setEmail(Constants.getCurrentDate() + "@dellteam.com");
        DeleteUser(userEdit, id);
    }

    @DisplayName("DSCOPE-T-465 : try editing nt username to empty ")
    @Test
    public void tryEditNTNameToEmpty(){
        int id = AuxiliaryCreateUser.createBasicUser();
        Roles role = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(role);
        User userEdit = new User(true, Constants.getCurrentDate() + "@dellteam.com",  "", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(400);
        userEdit.setName(Constants.getCurrentDate() + "User");
        DeleteUser(userEdit, id);
    }
    @DisplayName("DSCOPE-T-466 : try editing user role to empty")
    @Test
    public void tryEditRoleToEmpty(){
        int id = AuxiliaryCreateUser.createBasicUser();
        ArrayList<Roles> roles = new ArrayList<>();
        User userEdit = new User(true, Constants.getCurrentDate() + "@dellteam.com",  Constants.getCurrentDate() + "User", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(400);
        Roles role = new Roles(2, "User");
        roles.add(role);
        DeleteUser(userEdit, id);
    }

    @DisplayName("DSCOPE-T-467 : try editing ntname to an ntname longer than 255 characters")
    @Test
    public void tryEditName255(){
        int id = AuxiliaryCreateUser.createBasicUser();
        Roles role = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(role);
        User userEdit = new User(true, Constants.getCurrentDate() + "@dellteam.com",  "put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters put 256 caracters putt", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(400);
        userEdit.setName(Constants.getCurrentDate() + "User");
        DeleteUser(userEdit, id);
    }

    @Ignore
    @DisplayName("DSCOPE-T-468 : try editing the email to an invalid email")
    @Test
    public void tryEditInvalidEmail(){
        int id = AuxiliaryCreateUser.createBasicUser();
        Roles role = new Roles(1, "Administrator");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(role);
        User userEdit = new User(true, "user.name.com",   Constants.getCurrentDate() + "User", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(400);
        userEdit.setEmail(Constants.getCurrentDate() + "@dellteam.com");
        DeleteUser(userEdit, id);
    }

    @DisplayName("DSCOPE-T-469 : try editing user role to non-existent role")
    @Test
    public void tryEditNonExistentRole(){
        int id = AuxiliaryCreateUser.createBasicUser();
        Roles role = new Roles(10, "Non existent Role");
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(role);
        User userEdit = new User(true,Constants.getCurrentDate() + "@dellteam.com",   Constants.getCurrentDate() + "User", roles);
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit).pathParam("id", id)
                .when().put("/{id}")
                .then().assertThat().statusCode(400);
        roles.remove(role);
        Roles role1 = new Roles(2, "User");
        roles.add(role1);
        DeleteUser(userEdit, id);
    }

    @DisplayName("DSCOPE-T-470 : try to edit a user with non-existent id")
    @Test
    public void tryEditNonExistentID(){
        User userEdit = AuxiliaryCreateUser.CreateUser(false, 2, "User");
        given().header("Authorization", Constants.token).contentType(ContentType.JSON).body(userEdit)
                .when().put("/10199")
                .then().assertThat().statusCode(404);

    }
}

