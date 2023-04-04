package API;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetMyPostsTests extends AbstractTest {
    //Первая страница ASC позитивный тест
    @Test
    void getMyPostsASCFirstPostPositiveTest(){
        JsonPath response = given()
                .header("X-Auth-Token", getToken())
                .queryParam("sort", "createdAt")
                .queryParam("order","ASC")
                .queryParam("page","1")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("data[0].title"), equalTo("First Post"));
        if (Integer.parseInt(response.getString("meta.count")) > 4) {
            assertThat(response.getString("meta.nextPage"),equalTo("2"));
        } else {
            assertThat(response.getString("meta.nextPage"),isEmptyOrNullString());
        }
    }
// Последняя страница DESC позитивный тест
    @Test
    void getMyPostsDESCLastPostPositiveTest() {
        JsonPath response = given()
                .header("X-Auth-Token", getToken())
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl() + getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("data[0].title"), equalTo("Last Post"));
        if (Integer.parseInt(response.getString("meta.count")) > 4) {
            assertThat(response.getString("meta.nextPage"), equalTo("2"));
        } else {
            assertThat(response.getString("meta.nextPage"), isEmptyOrNullString());
        }
    }
    // Страница 2 позитивный тест
    @Test
    void getMyPostsPage2PositiveTest() {
        JsonPath response = given()
                .header("X-Auth-Token", getToken())
                .queryParam("sort", "createdAt")
                .queryParam("page", "2")
                .when()
                .get(getBaseUrl() + getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.prevPage"), equalTo("1"));
        if (Integer.parseInt(response.getString("meta.count")) > 8) {
            assertThat(response.getString("meta.nextPage"), equalTo("3"));
        } else {
            assertThat(response.getString("meta.nextPage"),  equalTo("null"));
        }
    }
    // Query негатвный тест
    @Test
    void getMyPostsBedRequestQueryNegativeTest() {
        JsonPath response = given()
                .header("X-Auth-Token", getToken())
                .queryParam("order", "desk")
                .when()
                .get(getBaseUrl() + getPathGetPosts())
                .then()
                .statusCode(400)
                .extract().body().jsonPath();
        assertThat(response.getString("message"), equalTo("Bad request"));
    }

    // Невалидный токен негативный тест
    @Test
    void getMyPostsInvalidTokenNegativeTest(){
        JsonPath response = given()
                .header("X-Auth-Token", "1")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .assertThat()
                .statusCode(401)
                .extract().jsonPath();
        assertThat(response.getString("message"), equalTo("No API token provided or is not valid"));

    }

    // Без токена
    @Test
    void getMyPostsWithoutTokenTest(){
        JsonPath response = given()
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .assertThat()
                .statusCode(401)
                .extract().jsonPath();
        assertThat(response.getString("message"), equalTo("Auth header required X-Auth-Token"));
    }


}

