package ch.allgeier.maven.restassured.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookingTest {

    private String baseUrl;

    private String username;

    private String password;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(is);
        }
        baseUrl = props.getProperty("base.url");
        username = props.getProperty("username");
        password = props.getProperty("password");

        RestAssured.baseURI = baseUrl;

        Thread.sleep(1500);
    }

    @Test
    void testAuthentication() {
        String requestBody = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/auth");

        assertEquals(201, response.getStatusCode(),
                "Erwartet HTTP 201 für POST /auth");

        String token = response.jsonPath().getString("token");
        assertNotNull(token, "Authentifizierungs-Token darf nicht null sein");
    }

    @Test
    void testGetBookingList() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/booking");

        assertEquals(200, response.getStatusCode(),
                "Erwartet HTTP 200 für GET /booking");

        String firstId = response.jsonPath().getString("[0].id");
        assertNotNull(firstId, "Buchungsliste soll 'id'-Feld enthalten, war: " + firstId);
    }

    @Test
    void testCreateBooking() {
        Response response = createTestBooking();

        String firstname = response.jsonPath().getString("booking.firstname");
        assertNotNull(firstname, "Feld 'firstname' soll in der Antwort vorhanden sein");
    }

    private Response createTestBooking() {
        String requestBody = """
                {
                  "firstname": "Jim",
                  "lastname": "Brown",
                  "totalprice": 111,
                  "depositpaid": true,
                  "bookingdates": {
                    "checkin": "2026-06-01",
                    "checkout": "2026-06-02"
                  },
                  "additionalneeds": "Breakfast"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/booking");

        assertEquals(200, response.getStatusCode(),
                "Erwartet HTTP 200 für POST /booking");

        return response;
    }
}
