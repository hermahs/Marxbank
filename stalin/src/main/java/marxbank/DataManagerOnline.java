package marxbank;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import marxbank.API.LogInRequest;
import marxbank.API.SignUpRequest;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.util.AccountType;

public class DataManagerOnline {

    private static DataManagerOnline dataInstance = null;

    private Long userId = null;
    private String userToken = null;
    private Boolean loggedIN = false;

    private DataManagerOnline() {}

    public static DataManagerOnline manager() {
        if (dataInstance == null) {
            synchronized (DataManagerOnline.class) {
                if (dataInstance == null) {
                    dataInstance = new DataManagerOnline();
                }
            }
        }

        return dataInstance;
    }

    public String getUserToken() {
        return this.userToken;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Boolean checkIfOnline() {
        try {
            Socket s = new Socket(InetAddress.getLocalHost(), 8080);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void loginPost(LogInRequest request) throws Exception {
        // sjekke mot krypterte passord her etter eller annet sted
        String requestString = String.format("{\"username\": \"%s\", \"password\":\"%s\"}", request.getUsername(), request.getPassword());
        String result = UrlHandler.handlePost("/auth/login", requestString);
        if (result == null) throw new Exception("Yeet yeet");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(result);

        if (node.has("error")) throw new Exception("User does not exist");

        this.userToken = node.get("token").asText();
        this.userId = objectMapper.valueToTree(node.get("userResponse")).get("id").asLong();
        this.loggedIN = true;
    }

    public void signup(SignUpRequest request) throws Exception {
        // kryptere passord her et eller annet sted
        String requestString = String.format("{\"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}", request.getUsername(), request.getEmail(), request.getPassword());
        String result = UrlHandler.handlePost("/auth/signup", requestString);

        if (result == null) throw new Exception("Signup error");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(result);

        if (node.has("error")) throw new Exception("Signup Error");

        // maybe return something here??
    }

    public void logout() throws Exception {
        if (!this.loggedIN) throw new Exception("Cannot log out if you're not logged in");
        String requestString = "";
        String result = UrlHandler.handlePostWithAuth("/auth/logout", requestString, this.userToken);

        if (result == null) throw new Exception("logout error");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(result);

        if (!node.get("signedOut").asBoolean()) throw new Exception("logout error");

        this.userId = null;
        this.userToken = null;
        this.loggedIN = false;
    }

    public void getAccounts() throws Exception {
        if (!loggedIN) throw new Exception("Not logged in");
        if (this.userToken == null) throw new Exception("userToken is null?!");
        if (this.userId == null) throw new Exception("userId is null?!");

        String result = UrlHandler.handleGetWithAuth("/accounts/myAccounts", this.userToken);
        System.out.println(result);

    }

    public void getTransaction() {
        // gets transactions as json
        // Deserialize into list
        // return list
    }

    public void createAccount(AccountType type, String name) throws Exception {
        if (!loggedIN) throw new Exception("Not logged in");
        if (this.userToken == null) throw new Exception("userToken is null?!");
        if (this.userId == null) throw new Exception("userId is null?!");

        String requestString = String.format("{\"accountType\":\"%s\", \"name\":\"%s\"}", type.toString(), name);
        String response = UrlHandler.handlePostWithAuth("/accounts/createAccount", requestString, this.userToken);

        System.out.println(response);
    }

    public void createTransaction(Transaction transaction) {
        // serialize transaction
        // say transfer from this account to that account
        // hippity hoppety this is now my property
    }

    public static void main(String... args) throws Exception {
        LogInRequest yeet = new LogInRequest("yeet", "yeet");

        DataManagerOnline.manager().loginPost(yeet);
        //System.out.println(DataManagerOnline.manager().getUserToken());

        DataManagerOnline.manager().getAccounts();
        
        DataManagerOnline.manager().createAccount(AccountType.SAVING, "testAccount");

        DataManagerOnline.manager().getAccounts();

        DataManagerOnline.manager().logout();
        //System.out.println(DataManagerOnline.manager().getUserToken());


    }

}
