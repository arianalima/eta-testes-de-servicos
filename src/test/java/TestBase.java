import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class TestBase {
    public RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .addHeader("accept", "application/json")
            .setBaseUri(Constants.BASE_URL).build();
}
