package API;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class POSTAuthTests {
 // Получение токена позитивнй тест
    @Test
    void getXAuthToken1 () {
        Object response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "lena_moseichuk@mail.ru")
                .formParam("password", "73f993467c")
                .when()
                .post("https://test-stand.gb.ru/gateway/login")
                .then().extract()
                .jsonPath()
                .get("token")
                .toString();
        System.out.println("API.Token: " + response);
    }
    // Получение токена с невалидным логином
    @Test
    void noGetXAuthTokenWithInvalidLogin(){
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username","lena_moseichuk@mail.ru22")
                .formParam("password","73f993467c")
                .when()
                .post("https://test-stand.gb.ru/gateway/login")
                .then()
                .statusCode(401);
    }
    // Без токена с невалидным паролем
    @Test
    void noGetXAuthTokenWithInvalidPassword (){
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username","lena_moseichuk@mail.ru")
                .formParam("password","1234")
                .when()
                .post("https://test-stand.gb.ru/gateway/login")
                .then()
                .statusCode(401);
    }
}
