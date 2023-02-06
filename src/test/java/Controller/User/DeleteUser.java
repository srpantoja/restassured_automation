package Controller.User;

import Serialization.User.User;
import Utils.Constants;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.*;

public class DeleteUser {
    @BeforeClass
    public static void setUp() {
        baseURI = Constants.url + Constants.endpoint_user; }
    public static void DeleteUser(User user, int id){
        user.setActive(false);
        int idE = AuxiliaryCreateUser.PutUser(user, id);
        AuxiliaryCreateUser.deleteUser(idE);

    }
    @Test
    @DisplayName("Delete user by id")
    public void DeleteUserById(){
        int id = AuxiliaryCreateUser.addCreateGetID(false);
        Response response =	given()
                .header("Authorization", Constants.token)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete("/{id}")
                .then()
                .log().all()
                .statusCode(200).extract().response();
        Assert.assertEquals("true", response.asString());
    }
    @Test
    @DisplayName("Try to delete user with non-existent id")
    public void TryToDeleteUserWithNonexistentId(){
        	given()
                .header("Authorization", Constants.token)
                .contentType(ContentType.JSON)
                .pathParam("id", 100)
                .when()
                .delete("/{id}")
                .then()
                .log().all()
                .statusCode(404).extract().response();

    }
    @Test
    @DisplayName("Try to delete matrix with negative id")
    public void TryToDeleteUserWithNegativeId(){
        	given()
                .header("Authorization", Constants.token)
                .contentType(ContentType.JSON)
                .pathParam("id", -100)
                .when()
                .delete("/{id}")
                .then()
                .log().all()
                .statusCode(404).extract().response();

    }
    @Test
    @DisplayName("Try to delete user with active status")
    public void TryToDeleteUserWithActiveStatus(){
        User userAuxiliary = AuxiliaryCreateUser.CreateUser(true, 1, "Administrator");
        int id = AuxiliaryCreateUser.PostUser(userAuxiliary);

         given()
                .header("Authorization", Constants.token)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete("/{id}")
                .then()
                .log().all()
                .statusCode(400).extract().response();
        userAuxiliary.setActive(false);
        DeleteUser(userAuxiliary, id);
    }
}
