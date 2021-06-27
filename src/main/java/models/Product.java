package models;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class Product {
    private String name;
    private int price;
    private String description;
    private int amount;
    private String id;

    public Product(String name, int price, String description, int amount) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Response register(RequestSpecification specification){
        String productStringJsonModel = generateProductJsonModel().toJSONString();
        Response response =
                given().
                        spec(specification).
                        header("Content-Type", "application/json").
                    and().
                        body(productStringJsonModel).
                when().
                        post("produtos");
        if (response.statusCode() == 201){
            setId(response.path("_id").toString());
        }
        return response;
    }

    private JSONObject generateProductJsonModel() {
        JSONObject productJsonModel = new JSONObject();
        productJsonModel.put("nome", this.name);
        productJsonModel.put("preco", this.price);
        productJsonModel.put("descricao", this.description);
        productJsonModel.put("quantidade", this.amount);
        return productJsonModel;
    }
}
