package API;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetOtherPostsTests extends AbstractTest{

    // ASC позитивный тест
    @Test
    public void getOtherPostsAscPositive(){
        JsonPath response =given()
                .header("X-Auth-Token", getToken())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.nextPage"), equalTo("2"));
        assertThat(response.getString("data[0].title"), equalTo("fired sausages"));
    }

    // DESC позитивный тест
    @Test
    public void getOtherPostsDescPositive(){
        JsonPath response =given()
                .header("X-Auth-Token", getToken())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.nextPage"), equalTo("2"));
    }

    // ALL позитивный тест
    @Test
    public void getOtherPostsALLPositive(){
        JsonPath response =given()
                .header("X-Auth-Token", getToken())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ALL")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.nextPage"), equalTo("2"));
    }
 // Без сортировки позитивный тест
    @Test
    public void getOtherPostsWithoutSortPositive(){
        JsonPath response =given()
                .header("X-Auth-Token", getToken())
                .queryParam("owner", "notMe")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(response.getString("meta.nextPage"), equalTo("2"));
    }
    // Query негативный тест
    @Test
    public void getOtherPostsBedRequestQueryNegative(){
        JsonPath response =given()
                .header("X-Auth-Token", getToken())
                .queryParam("owner", "notMettt")
                .when()
                .get(getBaseUrl()+getPathGetPosts())
                .then()
                .statusCode(400)
                .extract().body().jsonPath();
        assertThat(response.getString("message"), equalTo("Bad request"));
    }

}