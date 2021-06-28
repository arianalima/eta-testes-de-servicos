import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static requests.UserEndpoint.delete;
import static requests.UserEndpoint.register;

public class PostUserTests extends TestBase{
    private static User validUser;
    private static User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Maria", "maria@email.com", "abc123", true);
        invalidUser = new User("Chico", "chico@email.com", "abc123", true);
    }

    @Test
    public void register_validUser_status201(){
        Response response = register(SPECIFICATION, validUser);
        response.
                then().
                    assertThat().
                    statusCode(201).
                    body("message", equalTo(Constants.SUCCESS_REGISTER_MESSAGE)).
                    body("_id", notNullValue());
    }

    @Test
    public void register_duplicatedUser_status400(){
        register(SPECIFICATION, invalidUser);
        Response response = register(SPECIFICATION, invalidUser);
        response.
                 then().
                     assertThat().
                     statusCode(400).
                     body("message", equalTo(Constants.DUPLICATED_EMAIL_MESSAGE)).
                     body("_id", nullValue());
    }

    @AfterClass
    public void deleteTestData(){
        delete(SPECIFICATION, validUser);
        delete(SPECIFICATION, invalidUser);
    }
}
