package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;

public class UserEndpoint {
    public static Response register(RequestSpecification specification, User user){
        String userStringJsonModel = user.generateUserJsonModel().toJSONString();
        Response response =
                given().
                        spec(specification).
                        header("Content-Type", "application/json").
                        and().
                        body(userStringJsonModel).
                        when().
                        post("usuarios");
        if (response.statusCode() == 201){
            user.setId(response.path("_id").toString());
        }
        return response;
    }

    public static Response login(RequestSpecification specification, User user){
        Response response =
                given().
                        spec(specification).
                        header("Content-Type", "application/json").
                        and().
                        body(user.getCredentials()).
                        when().
                        post("login");
        try{
            user.setAuthToken(response.path("authorization").toString());
        }catch (NullPointerException e) {
            user.setAuthToken(null);
        }
        return response;
    }

    public static Response delete(RequestSpecification specification, User user) {
        Response response =
                given().
                        spec(specification).
                        header("Content-Type", "application/json").
                        when().
                        delete("usuarios/" + user.getId());
        return response;
    }

    public static Response updateInfo(RequestSpecification specification, User user) {
        String userStringJsonModel = user.generateUserJsonModel().toJSONString();
        Response response =
                given().
                        spec(specification).
                        header("Content-Type", "application/json").
                        and().
                        body(userStringJsonModel).
                        when().
                        put("usuarios/" + user.getId());
        if (response.statusCode() == 201){
            user.setId(response.path("_id").toString());
        }
        return response;
    }
}
