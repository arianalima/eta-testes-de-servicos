package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Product;
import models.User;

import static io.restassured.RestAssured.given;

public class ProductsEndpoint {
    public static Response register(RequestSpecification specification, Product product, User user){
        String productStringJsonModel = product.generateProductJsonModel().toJSONString();
        Response response =
                given().
                        spec(specification).
                        header("Content-Type", "application/json").
                        header("Authorization", user.getAuthToken()).
                    and().
                        body(productStringJsonModel).
                when().
                        post("produtos");
        if (response.statusCode() == 201){
            product.setId(response.path("_id").toString());
        }
        return response;
    }
}
