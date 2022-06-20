import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookstoreAPITest {

    @Test
    void retrieveBookTitleTest(){

        // Logging
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        // Defining the base URI
        RestAssured.baseURI= "https://bookstore.toolsqa.com/BookStore/v1";

        // Passing the resource details
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Content-Type", ContentType.JSON);
        requestSpecification.header("Accept", ContentType.JSON);
        requestSpecification.queryParam("ISBN", "9781449325862");

        // GET
        Response response = requestSpecification.get("/Book");

        // Retrieving the response responseBody using getBody() method
        ResponseBody responseBody = response.body();

        // Converting the response responseBody to string object
        String responseBodyAsString = responseBody.asString();

        // Creating object of JsonPath and passing the string response responseBody as parameter
        JsonPath jsonPath = new JsonPath(responseBodyAsString);

        // Storing publisher name in a string variable
        String title = jsonPath.getString("title");
        assertEquals("Git Pocket Guide", title);
    }
}
