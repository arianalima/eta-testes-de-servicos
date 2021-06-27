import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static requests.UserEndpoint.delete;
import static requests.UserEndpoint.register;

public class DeleteUserTests extends TestBase{
    private static User validUser;
    private static User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Ari", "ari@email.com", "senha", true);
        register(SPECIFICATION, validUser);
        invalidUser = new User("Dummy", "dummy@email.com", "senha", true);
        invalidUser.setId("dummy");
    }

    @Test
    public void delete_validUser_status200(){
        Response response = delete(SPECIFICATION, validUser);
        response.
                then().
                    assertThat().
                    statusCode(200).
                    body("message", equalTo(Constants.SUCCESS_DELETION_MESSAGE));
    }

    @Test
    public void delete_invalidUser_status200(){
        Response response = delete(SPECIFICATION, invalidUser);
        response.
                then().
                assertThat().
                statusCode(200).
                body("message", equalTo(Constants.NO_DELETION_MESSAGE));
    }

    @AfterClass
    public void deleteTestData(){
        delete(SPECIFICATION, validUser);
    }
}
