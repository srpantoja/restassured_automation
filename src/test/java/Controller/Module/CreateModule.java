package Controller.Module;

import static io.restassured.RestAssured.*;

import Serialization.Module.Module;
import Utils.Constants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CreateModule {
  
  @BeforeClass
  public static void setUp() {
    baseURI = Constants.url + Constants.endpoint_module;
    useRelaxedHTTPSValidation();
  }

  @Test
  @DisplayName("Criar modulo com sucesso")
  public void CreateModuleValidData() {

    Module module = new Module("Novo Modulo");

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

    String moduleName = response.path("name");
    String moduleId = response.path("id");

    Assert.assertEquals(moduleName, module.getName());
    AuxiliaryModule.DeleteModuleById(moduleId);
  }

  @Test
  @DisplayName("Criar modulo com nome já existente")
  public void CriarModuloComNomeExistente() {

    String name = "Nome repetido";
    
    String auxIdModule = AuxiliaryModule.CreateModuleAndReturnId(name);

    Module module = new Module(name);

    given() // dado que algo acontece ou o ambiente
      //.header("Authorization", Constants.token)
      .contentType(ContentType.JSON)
      .body(module)
      .when() // quando ocorre isso
      .post("")
      .then() // então faça isso ou espere por isso
      .statusCode(400);

    AuxiliaryModule.DeleteModuleById(auxIdModule);
  }

 

}
