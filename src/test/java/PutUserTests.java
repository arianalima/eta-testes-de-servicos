import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static requests.UserEndpoint.*;

public class PutUserTests extends TestBase{
    private static User validUser;
    private static User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Maria", "maria@email.com", "abc123", true);
        register(SPECIFICATION, validUser);
        invalidUser = new User("Chico", "chico@email.com", "abc123", true);
    }

    @Test
    public void updateInfo_validUser_status200(){
        validUser.setName("Updated Maria");
        Response response = updateInfo(SPECIFICATION, validUser);
        response.
                then().
                statusCode(200).
                body("message", equalTo(Constants.SUCCESS_UPDATE_MESSAGE)).
                body("_id", nullValue());
    }

    @Test
    public void updateInfo_invalidUser_status201(){
        Response response = updateInfo(SPECIFICATION, invalidUser);
        response.
                then().
                statusCode(201).
                body("message", equalTo(Constants.SUCCESS_REGISTER_MESSAGE)).
                body("_id", notNullValue());
    }

    @Test
    public void updateInfo_invalidUser_status400(){
        invalidUser.setEmail("maria@email.com");
        Response response = updateInfo(SPECIFICATION, invalidUser);
        response.
                then().
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
