package models;

import org.json.simple.JSONObject;


public class User {
    private String name;
    private String email;
    private String password;
    private String id;
    private boolean isAdmin;
    private String authToken;

    public User(String name, String email, String password, boolean isAdmin){
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.authToken = "";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public boolean isAdmin(){
        return isAdmin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public String getCredentials(){
        JSONObject userJsonModel = new JSONObject();
        userJsonModel.put("email", this.email);
        userJsonModel.put("password", this.password);
        return userJsonModel.toJSONString();
    }

    public JSONObject generateUserJsonModel() {
        JSONObject userJsonModel = new JSONObject();
        userJsonModel.put("nome", this.name);
        userJsonModel.put("email", this.email);
        userJsonModel.put("password", this.password);
        userJsonModel.put("administrador", String.valueOf(this.isAdmin));
        return userJsonModel;
    }

}
