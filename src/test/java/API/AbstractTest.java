package API;

import io.restassured.path.json.JsonPath;
import lombok.Getter;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public abstract class AbstractTest {
    static Properties myProp = new Properties();
    private  static InputStream  config;
    private  static String baseUrl;
    private  static String pathLogin;
    private  static String pathGetPosts;



    private static String login;
    private static String pass;

    @BeforeAll
    static void unitTest() throws IOException {
        config = new FileInputStream("./src/main/resources/my.properties");
        myProp.load(config);
        baseUrl=myProp.getProperty("baseUrl");
        pathLogin=myProp.getProperty("pathLogin");
        pathGetPosts=myProp.getProperty("pathGetPosts");
        login=myProp.getProperty("login");
        pass=myProp.getProperty("pass");


    }

    public static String  getToken(){
        String  token = given()
                .contentType("multipart/form-data")
                .multiPart("username", login)
                .multiPart("password", pass)
                .when()
                .post(getBaseUrl()+getPathLogin())
                .then()
                .extract().body().jsonPath().getString("token");
        return token;

    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getPathLogin() {
        return pathLogin;
    }

    public static String getPathGetPosts() {
        return pathGetPosts;
    }
    public static String getLogin() {return login;}
    public static String getPass() {return pass;}

}

