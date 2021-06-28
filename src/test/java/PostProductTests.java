import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requests.UserEndpoint;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static requests.ProductsEndpoint.register;
import static requests.UserEndpoint.delete;

public class PostProductTests extends TestBase{
    private static Product validProduct1;
    private static Product validProduct2;
    private static User validUser;
    private static User invalidUser1;
    private static User invalidUser2;


    @BeforeClass
    public void generateTestData(){
        validProduct1 = new Product("laptop", 5000, "i9 16GB 250GB SSD", 5);
        validProduct2 = new Product("chair", 300, "office chair", 25);
        validUser = new User("Ari", "ari@email.com", "senha", true);
        UserEndpoint.register(SPECIFICATION, validUser);
        UserEndpoint.login(SPECIFICATION, validUser);
        invalidUser1 = new User("Chico", "chico@email.com", "abc123", true);
        invalidUser2 = new User("Maria", "maria@email.com", "abc123", false);
        UserEndpoint.register(SPECIFICATION, invalidUser2);
        UserEndpoint.login(SPECIFICATION, invalidUser2);
    }

    @Test
    public void register_validProductValidUser_status201(){
        Response response = register(SPECIFICATION, validProduct1, validUser);
        response.
                then().
                    statusCode(201).
                    body("message", equalTo(Constants.SUCCESS_REGISTER_MESSAGE)).
                    body("_id", notNullValue());
    }

    @Test
    public void register_duplicatedProduct_status400(){
        register(SPECIFICATION, validProduct2, validUser);
        Response response = register(SPECIFICATION, validProduct2, validUser);
        response.
                then().
                statusCode(400).
                body("message", equalTo(Constants.DUPLICATED_PRODUCT_MESSAGE)).
                body("_id", nullValue());
    }

    @Test
    public void register_validProductInvalidUser_status401(){
        Response response = register(SPECIFICATION, validProduct2, invalidUser1);
        response.
                then().
                statusCode(401).
                body("message", equalTo(Constants.INVALID_TOKEN_MESSAGE)).
                body("_id", nullValue());
    }

    @Test
    public void register_validProductInvalidUser_status403(){
        Response response = register(SPECIFICATION, validProduct2, invalidUser2);
        response.
                then().
                statusCode(403).
                body("message", equalTo(Constants.UNAUTHORIZED_ROUTE_MESSAGE)).
                body("_id", nullValue());
    }

    @AfterClass
    public void deleteTestData(){
        delete(SPECIFICATION, validUser);
        delete(SPECIFICATION, invalidUser2);
    }
}
