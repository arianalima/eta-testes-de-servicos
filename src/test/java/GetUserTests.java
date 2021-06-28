import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static requests.UserEndpoint.*;


public class GetUserTests extends TestBase{
    private static User validUser1;
    private static User validUser2;
    private static User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser1 = new User("Maria", "maria@email.com", "abc123", true);
        register(SPECIFICATION, validUser1);
        validUser2 = new User("Chico", "chico@email.com", "123abc", false);
        register(SPECIFICATION, validUser2);
        invalidUser = new User("Dummy", "chico@email.com", "123abc", false);
    }


    @DataProvider(name = "usersData")
    public Object[][] createTestData(){
        return new Object[][]{
                {"?nome=" + validUser1.getName(), 1},
                {"?password=" + validUser1.getPassword(), 1},
                {"?nome=" + invalidUser.getName(), 0},
        };
    }

    @Test
    public void getAll_allUsers_status200(){
        Response response = getAll(SPECIFICATION);
        response.
            then().
                    assertThat().
                    statusCode(200).
                    body("quantidade", equalTo(3));
    }

    @Test(dataProvider = "usersData")
    public void getUser_nameFulanoDaSilvaPasswordTest_status200(String query, int resultAmount){
        Response response = getAll(SPECIFICATION, query);
        response.
            then().
                    assertThat().
                    statusCode(200).
                    body("quantidade", equalTo(resultAmount));
    }

    @AfterClass
    public void cleanTestData(){
        delete(SPECIFICATION, validUser1);
        delete(SPECIFICATION, validUser2);
    }
}
