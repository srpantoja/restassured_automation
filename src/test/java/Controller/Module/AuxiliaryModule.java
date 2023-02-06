package Controller.Module;

import static io.restassured.RestAssured.*;

import Serialization.Module.Module;
import Utils.Constants;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class AuxiliaryModule {

  @BeforeClass
  public static void setUp() {
    baseURI = Constants.url + Constants.endpoint_module;
    useRelaxedHTTPSValidation();
  }

  public static void DeleteModuleById(String id) {
    given()
      //.header("Authorization", Constants.token)
      .contentType(ContentType.JSON)
      .pathParam("id", id)
      .when()
      .delete("/{id}")
      .then()
      .statusCode(200);
  }

  public static String CreateModuleAndReturnId(String name) {
    Module module = new Module(name);

    Response response = given() // dado que algo acontece ou o ambiente
      //.header("Authorization", Constants.token)
      .contentType(ContentType.JSON)
      .body(module)
      .when() // quando ocorre isso
      .post("")
      .then() // então faça isso ou espere por isso
      .statusCode(201)
      .extract()
      .response();

    String moduleId = response.path("id");

    return moduleId; 
  }
}
