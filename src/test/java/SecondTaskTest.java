import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.closeTo;

public class SecondTaskTest {

    @Test
    public void test() {
        RestAssured.baseURI = "http://api.ipstack.com";

       RestAssured.given()
                    .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                    .param("access_key", "9eeb9c3aa1713377df3f434a13d7ff8f")
                    .param("format", "1")
               .when()
                    .get("/212.58.120.57")
               .then()
                    .statusCode(200)
                    .body("latitude", closeTo(new BigDecimal(41.69411087036133), new BigDecimal(0.01)))
                    .body("longitude", closeTo(new BigDecimal(44.83367919921875), new BigDecimal(0.01)));

    }


}
