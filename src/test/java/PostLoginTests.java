import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static requests.UserEndpoint.*;

public class PostLoginTests extends TestBase{
    private static User validUser;
    private static User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Ari", "ari@email.com", "senha", true);
        register(SPECIFICATION, validUser);
        invalidUser = new User("dummy", "ari@email.com", "ghjgk", true);
    }

    @Test
    public void login_validUser_status200(){
        Response response = login(SPECIFICATION, validUser);
        response.
                then().
                statusCode(200).
                body("message", equalTo(Constants.SUCCESS_LOGIN_MESSAGE)).
                body("authorization", startsWith("Bearer "));
    }

    @Test
    public void login_invalidUser_status401(){
        Response response = login(SPECIFICATION, invalidUser);
        response.
                then().
                    assertThat().
                    statusCode(401).
                    body("message", equalTo(Constants.FAILED_LOGIN_MESSAGE)).
                    body("authorization", nullValue());
    }

    @AfterClass
    public void deleteTestData(){
        delete(SPECIFICATION, validUser);
    }
}
